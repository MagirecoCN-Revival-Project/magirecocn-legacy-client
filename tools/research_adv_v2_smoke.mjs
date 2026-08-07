import { chromium } from 'playwright';
import fs from 'node:fs';
import path from 'node:path';

const BASE = process.env.ADV_V2_URL || 'https://feature-story-playback-local.magiaexedralive2dviewer.pages.dev/';
const OUT = path.resolve(process.env.ADV_V2_SMOKE_OUT || 'adv-v2-smoke');
const TIMEOUT = 120_000;
const EXPECTED_RELEASE = 'adv-v2-native-story-layout-first-chapter-20260807';
fs.mkdirSync(OUT, { recursive: true });

const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

async function shot(page, name) {
  const file = path.join(OUT, `${name}.png`);
  await page.screenshot({ path: file, fullPage: true });
  console.log(`[shot] ${file}`);
}

async function stableText(page) {
  return page.evaluate(() => {
    const dialogue = document.querySelector('#adv-text')?.textContent ?? '';
    const narration = document.querySelector('#magireco-adv-narration-text')?.textContent ?? '';
    return `${dialogue}${narration}`;
  });
}

async function advanceUntil(page, needle, maxTurns = 80) {
  for (let i = 0; i < maxTurns; i++) {
    if ((await stableText(page)).includes(needle)) return;
    await page.keyboard.press('ArrowRight');
    await sleep(80);
    await page.keyboard.press('ArrowRight');
    await sleep(280);
  }
  throw new Error(`Did not reach text: ${needle}`);
}

async function canvasPixels(page, selector, logicalRect = null) {
  return page.evaluate(async (sel, rect) => {
    const source = document.querySelector(sel);
    if (!(source instanceof HTMLCanvasElement)) throw new Error(`canvas missing: ${sel}`);
    const image = new Image();
    image.src = source.toDataURL('image/png');
    await image.decode();
    const probe = document.createElement('canvas');
    probe.width = image.naturalWidth;
    probe.height = image.naturalHeight;
    const ctx = probe.getContext('2d', { willReadFrequently: true });
    if (!ctx) throw new Error('pixel context unavailable');
    ctx.drawImage(image, 0, 0);
    const sx = probe.width / 1280;
    const sy = probe.height / 720;
    const area = rect ?? { x: 0, y: 0, width: 1280, height: 720 };
    const data = ctx.getImageData(
      Math.round(area.x * sx), Math.round(area.y * sy),
      Math.max(1, Math.round(area.width * sx)), Math.max(1, Math.round(area.height * sy)),
    ).data;
    let alpha = 0; let bright = 0; let dark = 0; let coloured = 0;
    for (let i = 0; i < data.length; i += 4) {
      const r = data[i]; const g = data[i + 1]; const b = data[i + 2]; const a = data[i + 3];
      if (a > 80) alpha++;
      if (a > 150 && r + g + b > 650) bright++;
      if (a > 150 && r + g + b < 420) dark++;
      if (a > 150 && Math.max(r, g, b) - Math.min(r, g, b) > 45) coloured++;
    }
    return { alpha, bright, dark, coloured, width: probe.width, height: probe.height };
  }, selector, logicalRect);
}

async function openFirstSection(page) {
  await page.waitForSelector('#toggleAdvPanelBtn', { timeout: TIMEOUT });
  await page.click('#toggleAdvPanelBtn');
  await page.waitForFunction(() => {
    const search = document.querySelector('#magi-reader-search');
    return search instanceof HTMLInputElement
      && !search.disabled
      && document.querySelectorAll('#magi-reader-story option').length > 0;
  }, null, { timeout: TIMEOUT });
  await page.evaluate(() => {
    const search = document.querySelector('#magi-reader-search');
    search.value = '1011-01';
    search.dispatchEvent(new Event('input', { bubbles: true }));
  });
  await sleep(150);
  await page.evaluate(() => {
    const story = document.querySelector('#magi-reader-story');
    const section = document.querySelector('#magi-reader-section');
    const load = document.querySelector('#magi-reader-load');
    if (!(story instanceof HTMLSelectElement)
      || !(section instanceof HTMLSelectElement)
      || !(load instanceof HTMLButtonElement)) throw new Error('picker incomplete');
    const storyOption = [...story.options].find((entry) => entry.textContent?.includes('1011-01'));
    if (!storyOption) throw new Error('1011-01 story not found');
    story.value = storyOption.value;
    story.dispatchEvent(new Event('change', { bubbles: true }));
    const sectionOption = [...section.options].find((entry) => entry.textContent?.includes('101101-1'));
    if (!sectionOption) throw new Error('101101-1 section not found');
    section.value = sectionOption.value;
    load.click();
  });
  await page.waitForFunction(() => document.body.classList.contains('magireco-adv-mode'), null, { timeout: TIMEOUT });
  await page.waitForSelector('#magireco-adv-v2-active-canvas', { timeout: TIMEOUT });
  await page.waitForFunction(
    () => document.documentElement.dataset.magirecoAdvV2Ready === 'true',
    null,
    { timeout: TIMEOUT },
  );
}

async function assertCanvasStack(page) {
  const stack = await page.evaluate(() => {
    const under = document.getElementById('magireco-adv-v2-effect-underlay');
    const live = document.getElementById('live2dCanvas');
    const stage = document.getElementById('magireco-adv-stage-ui');
    const active = document.getElementById('magireco-adv-v2-active-canvas');
    if (!(under instanceof HTMLElement) || !(live instanceof HTMLElement)
      || !(stage instanceof HTMLElement) || !(active instanceof HTMLElement)) {
      throw new Error('ADV canvas stack incomplete');
    }
    return {
      underZ: Number(getComputedStyle(under).zIndex || 0),
      liveZ: Number(getComputedStyle(live).zIndex || 0),
      stageZ: Number(getComputedStyle(stage).zIndex || 0),
    };
  });
  console.log('[canvas-stack]', stack);
  if (!(stack.underZ < stack.liveZ && stack.liveZ < stack.stageZ)) {
    throw new Error(`Native cross-Live2D stack is reversed: ${JSON.stringify(stack)}`);
  }
}

function legacyState() {
  const turn = [...document.querySelectorAll('.magireco-adv-cocos-effect')]
    .find((node) => node instanceof HTMLElement && node.dataset.armatureId === 'named-turn-effect');
  const emblem = [...document.querySelectorAll('.magireco-adv-item')]
    .find((node) => node instanceof HTMLImageElement && node.src.includes('6103_zenobia_emblem.png'));
  const state = (node) => {
    if (!(node instanceof HTMLElement)) return null;
    const style = getComputedStyle(node);
    return {
      opacity: Number(style.opacity),
      visibility: style.visibility,
      replaced: node.dataset.magirecoAdvV2Replaced ?? node.dataset.magirecoAdvV2ItemReplaced ?? '',
    };
  };
  return { turn: state(turn), emblem: state(emblem) };
}

const browser = await chromium.launch({
  headless: true,
  args: ['--enable-webgl', '--ignore-gpu-blocklist', '--use-angle=swiftshader', '--autoplay-policy=no-user-gesture-required'],
});

try {
  const context = await browser.newContext({ viewport: { width: 698, height: 1536 } });
  const page = await context.newPage();
  page.setDefaultTimeout(TIMEOUT);
  const consoleErrors = [];
  page.on('console', (message) => {
    console.log(`[browser:${message.type()}] ${message.text()}`);
    if (message.type() === 'error') consoleErrors.push(message.text());
  });
  page.on('pageerror', (error) => consoleErrors.push(error.message));

  const url = new URL(BASE);
  url.searchParams.set('advRenderer', 'pixi-v2');
  await page.goto(url.href, { waitUntil: 'domcontentloaded', timeout: TIMEOUT });
  const build = await page.evaluate(async () => {
    const response = await fetch(`/build-info.json?smoke=${Date.now()}`, { cache: 'no-store' });
    return response.json();
  });
  console.log('[build-info]', JSON.stringify(build));
  if (build.release !== EXPECTED_RELEASE) {
    throw new Error(`Unexpected deployed release: ${build.release}; expected ${EXPECTED_RELEASE}`);
  }

  await openFirstSection(page);
  await assertCanvasStack(page);
  await advanceUntil(page, '为什么我……', 12);
  await shot(page, '01-dream-question');

  await advanceUntil(page, '再探查一下吧……', 50);
  await shot(page, '02-before-magic-detect');
  await page.keyboard.press('ArrowRight');
  await sleep(1050);
  await advanceUntil(page, '果然很近……', 8);
  await shot(page, '03-very-close');

  await page.keyboard.press('ArrowRight');
  await page.waitForFunction(() => document.documentElement.dataset.magirecoAdvV2NamedEffect === 'true', null, { timeout: TIMEOUT });
  await sleep(220);
  const charFrontPixels = await canvasPixels(page, '#magireco-adv-v2-active-canvas', {
    x: 120, y: 80, width: 1040, height: 500,
  });
  console.log('[ef_adv_01-character-front]', charFrontPixels);
  if (charFrontPixels.alpha < 500) throw new Error(`ef_adv_01 missing from character-front surface: ${JSON.stringify(charFrontPixels)}`);
  await shot(page, '04-ef-adv-01-detect-magic-native-front');

  await advanceUntil(page, '（必须赶紧去确认！）', 12);
  await page.keyboard.press('ArrowRight');
  await sleep(90);
  await page.keyboard.press('ArrowRight');
  await page.waitForFunction(() => {
    const turn = [...document.querySelectorAll('.magireco-adv-cocos-effect')]
      .find((node) => node instanceof HTMLElement && node.dataset.armatureId === 'named-turn-effect');
    const emblem = [...document.querySelectorAll('.magireco-adv-item')]
      .find((node) => node instanceof HTMLImageElement && node.src.includes('6103_zenobia_emblem.png'));
    return turn instanceof HTMLElement
      && turn.dataset.magirecoAdvV2Replaced === 'true'
      && emblem instanceof HTMLElement
      && emblem.dataset.magirecoAdvV2ItemReplaced === 'true';
  }, null, { timeout: TIMEOUT });
  await sleep(800);
  const underPixels = await canvasPixels(page, '#magireco-adv-v2-effect-underlay', {
    x: 40, y: 20, width: 1200, height: 650,
  });
  console.log('[ef_adv_06+emblem-under-cubism]', underPixels);
  if (underPixels.alpha < 1000) throw new Error(`Witch barrier underlay missing: ${JSON.stringify(underPixels)}`);
  const replaced = await page.evaluate(legacyState);
  console.log('[witch-legacy-replaced]', JSON.stringify(replaced));
  if (!replaced.turn || !replaced.emblem) throw new Error('witch legacy layers missing');
  if (replaced.turn.visibility !== 'hidden' && replaced.turn.opacity > 0) throw new Error('legacy turn effect still visible');
  if (replaced.emblem.visibility !== 'hidden' && replaced.emblem.opacity > 0) throw new Error('legacy emblem still visible');
  await shot(page, '05-ef-adv-06-witch-barrier-native-under');

  await advanceUntil(page, '找到了，是魔女的结界', 6);
  await shot(page, '06-witch-barrier-dialogue');

  const fatal = consoleErrors.filter((entry) => /ADV v2|TypeError|ReferenceError|WebGL/i.test(entry));
  if (fatal.length) throw new Error(`Browser errors:\n${fatal.join('\n')}`);
  console.log('ADV v2 deployed first-chapter native-layer smoke passed');
} finally {
  await browser.close();
}
