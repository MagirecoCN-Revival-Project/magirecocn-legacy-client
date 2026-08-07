import { chromium } from 'playwright';
import fs from 'node:fs';
import path from 'node:path';

const BASE = process.env.ADV_V2_URL || 'https://feature-story-playback-local.magiaexedralive2dviewer.pages.dev/';
const OUT = path.resolve(process.env.ADV_V2_SMOKE_OUT || 'adv-v2-smoke');
const TIMEOUT = 120_000;
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
  const selected = await page.evaluate(() => {
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
    if (!sectionOption) throw new Error(`101101-1 section not found: ${[...section.options].map(x => x.textContent).join(' | ')}`);
    section.value = sectionOption.value;
    load.click();
    return { story: storyOption.textContent, section: sectionOption.textContent };
  });
  console.log('[chapter]', selected);
  await page.waitForFunction(() => document.body.classList.contains('magireco-adv-mode'), null, { timeout: TIMEOUT });
  await page.waitForSelector('#magireco-adv-v2-active-canvas', { timeout: TIMEOUT });
  await page.waitForFunction(
    () => document.documentElement.dataset.magirecoAdvV2Ready === 'true',
    null,
    { timeout: TIMEOUT },
  );
}

function visibleNodeState() {
  const turn = [...document.querySelectorAll('.magireco-adv-cocos-effect')]
    .find((node) => node instanceof HTMLElement && node.dataset.armatureId === 'named-turn-effect');
  const emblem = [...document.querySelectorAll('.magireco-adv-item')]
    .find((node) => node instanceof HTMLImageElement && node.src.includes('6103_zenobia_emblem.png'));
  const visible = (node) => {
    if (!(node instanceof HTMLElement)) return null;
    const s = getComputedStyle(node);
    return {
      opacity: Number(s.opacity),
      visibility: s.visibility,
      replaced: node.dataset.magirecoAdvV2Replaced ?? node.dataset.magirecoAdvV2ItemReplaced ?? '',
    };
  };
  return { turn: visible(turn), emblem: visible(emblem) };
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
    const response = await fetch('/build-info.json', { cache: 'no-store' });
    return response.json();
  });
  console.log('[build-info]', JSON.stringify(build));
  if (build.release !== 'adv-v2-first-chapter-cocos-particle-compositor-20260807') {
    throw new Error(`Unexpected deployed release: ${build.release}`);
  }

  await openFirstSection(page);
  await advanceUntil(page, '为什么我……', 12);
  await shot(page, '01-dream-question');

  await advanceUntil(page, '再探查一下吧……', 50);
  await shot(page, '02-before-magic-detect');
  await page.keyboard.press('ArrowRight');
  await sleep(1050);
  await advanceUntil(page, '果然很近……', 8);
  await shot(page, '03-very-close');

  await page.keyboard.press('ArrowRight');
  await sleep(220);
  const named = await page.evaluate(() => document.documentElement.dataset.magirecoAdvV2NamedEffect ?? '');
  if (named !== 'true') throw new Error(`ef_adv_01 did not acquire v2 ownership: ${named}`);
  await shot(page, '04-ef-adv-01-detect-magic');

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
  const replaced = await page.evaluate(visibleNodeState);
  console.log('[witch-composite]', JSON.stringify(replaced));
  if (!replaced.turn || !replaced.emblem) throw new Error('witch legacy layers missing');
  if (replaced.turn.visibility !== 'hidden' && replaced.turn.opacity > 0) throw new Error('legacy turn effect still visible');
  if (replaced.emblem.visibility !== 'hidden' && replaced.emblem.opacity > 0) throw new Error('legacy emblem still visible');
  await shot(page, '05-ef-adv-06-witch-barrier-entry');

  await advanceUntil(page, '找到了，是魔女的结界', 6);
  await shot(page, '06-witch-barrier-dialogue');

  const fatal = consoleErrors.filter((entry) => /ADV v2|TypeError|ReferenceError|WebGL/i.test(entry));
  if (fatal.length) throw new Error(`Browser errors:\n${fatal.join('\n')}`);
  console.log('ADV v2 deployed first-chapter visual smoke passed');
} finally {
  await browser.close();
}
