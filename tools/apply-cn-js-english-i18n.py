#!/usr/bin/env python3
"""Apply reviewed English→Simplified Chinese UI corrections to cn_js_update.zip.

The patch is deliberately path-aware and literal-aware:
- JavaScript: only complete string literals are replaced; identifiers and substrings are untouched.
- HTML/EJS: only exact visible text fragments (`>text<`) and a small number of reviewed
  mixed fragments are replaced; attributes, class names and template expressions are untouched.
- Every reviewed replacement must be observed at least once, otherwise the build fails.
- Modified JavaScript is checked with `node --check` when Node.js is available.
"""
from __future__ import annotations

import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import shutil
import subprocess
import tempfile
import zipfile

# Exact JavaScript string-literal replacements valid across the runtime tree.
JS_GLOBAL = {
    "OK": "确定",
    "Close": "关闭",
    "Cancel": "取消",
    "Yes": "是",
    "No": "否",
    "COMING SOON": "敬请期待",
}

# Path-specific JavaScript string-literal replacements.
JS_BY_PATH: dict[str, dict[str, str]] = {
    "magica/js/view/config/ConfigTopView.js": {
        "Transfer Data": "数据转移",
        "Your password has been set.": "转移密码已设置。",
        "Password Set": "已设置密码",
        "A password must be 8-15 characters.": "密码须为 8～15 个字符。",
        "The passwords do not match.": "两次输入的密码不一致。",
        "Enter a password.": "请输入密码。",
        "Change Name": "更改名称",
        "Change": "更改",
        "Your Player Name has been updated.": "玩家名称已更新。",
        "To Title Page": "返回标题画面",
        "Enter your Player Name.": "请输入玩家名称。",
        "Are you sure you want to change your Player Name to 「\\x3cspan class\\x3d'popUserName'\\x3e\\x3c/span\\x3e」?\\x3cbr\\x3e\\x3cbr\\x3e\\x3cspan class\\x3d'c_red'\\x3e※Player Names can be changed only once a day.\\x3cbr\\x3e※After this, you will be returned to the Title Page":
            "确定要把玩家名称更改为「\\x3cspan class\\x3d'popUserName'\\x3e\\x3c/span\\x3e」吗？\\x3cbr\\x3e\\x3cbr\\x3e\\x3cspan class\\x3d'c_red'\\x3e※玩家名称每天只能修改一次。\\x3cbr\\x3e※更改后将返回标题画面",
        "Player ID": "玩家 ID",
        "Copied to your clipboard.": "已复制到剪贴板。",
        "Transfer ID": "转移 ID",
        "Cache Clear": "清除缓存",
        "Cache has been cleared.": "缓存已清除。",
        "Change Data Settings": "更改数据设置",
        "Download All Story Voice Data": "下载全部剧情语音数据",
        "Download all remaining Voices for the Main Story.\\x3cbr\\x3e\\x3cspan class\\x3d\"c_red\"\\x3e※We recommend using a stable Wi-Fi network.\\x3c/span\\x3e":
            "下载主线剧情尚未下载的全部语音。\\x3cbr\\x3e\\x3cspan class\\x3d\"c_red\"\\x3e※建议使用稳定的 Wi-Fi 网络。\\x3c/span\\x3e",
        "Downloading Data": "正在下载数据",
        "Data downloaded.": "数据下载完成。",
        "Video Data": "视频数据",
        "Video data downloaded.": "视频数据下载完成。",
        "Video data": "视频数据",
        "Download All": "全部下载",
        "Delete Data Settings": "删除数据设置",
        "Download All Data": "下载全部数据",
        "All data downloaded.": "全部数据下载完成。",
    },
    "magica/js/collection/StoryCollection.js": {
        "Kimochi Battle Special Edition: Alina Eve": "心魔战特别篇：阿莉娜·伊芙",
        "Story Confirmation": "剧情确认",
        "There is no story available.": "没有可播放的剧情。",
        "There is no story bookmark available.": "没有可用的剧情书签。",
        "There are no images available to display.": "没有可显示的图片。",
        "There is no story available here.": "此处没有可播放的剧情。",
        "Would you like to read this story?\\x3cbr\\x3e\\x3cspan class\\x3d'c_red'\\x3e※You can continuously read through all story episodes you've unlocked.\\x3c/span\\x3e":
            "要阅读这段剧情吗？\\x3cbr\\x3e\\x3cspan class\\x3d'c_red'\\x3e※可以连续阅读所有已解锁的剧情章节。\\x3c/span\\x3e",
        "Story Unlocked": "剧情已解锁",
        "The following story has been unlocked.": "已解锁以下剧情。",
        "Read Now": "立即阅读",
        "Read Later": "稍后阅读",
        "This will consume \\x3cspan class\\x3d'c_pink'\\x3e1\\x3c/span\\x3e \\x3cspan class\\x3d'c_pink'\\x3e":
            "将消耗 \\x3cspan class\\x3d'c_pink'\\x3e1\\x3c/span\\x3e 个 \\x3cspan class\\x3d'c_pink'\\x3e",
        "Unlock": "解锁",
        "You are missing \\x3cspan class\\x3d'c_pink'\\x3e1 \\x3c/span\\x3e\\x3cspan class\\x3d'c_pink'\\x3e":
            "还缺 \\x3cspan class\\x3d'c_pink'\\x3e1\\x3c/span\\x3e 个 \\x3cspan class\\x3d'c_pink'\\x3e",
        "To Shop": "前往商店",
        "Fragment of Reminiscence": "追忆碎片",
        "Story up to ": "剧情回顾：",
        "Present Day Kamihama": "现代神滨篇",
        "Prologue": "序章",
    },
    "magica/js/quest/QuestBattleSelect.js": {
        "Main Story": "主线剧情",
        "Another Story": "另一篇章",
        "Chara Story": "魔法少女剧情",
        "Costume Story": "服装剧情",
    },
    "magica/js/event/training/EventTrainingTop.js": {
        "BATTLE ◆ 初级": "战斗 ◆ 初级",
        "BATTLE ◆ 中级": "战斗 ◆ 中级",
        "BATTLE ◆ 上级": "战斗 ◆ 上级",
        "BATTLE ◆ 超级": "战斗 ◆ 超级",
    },
    "magica/js/quest/EventQuest.js": {
        "BATTLE ◆ 初级": "战斗 ◆ 初级",
        "BATTLE ◆ 中级": "战斗 ◆ 中级",
        "BATTLE ◆ 上级": "战斗 ◆ 上级",
        "BATTLE ◆ 超级": "战斗 ◆ 超级",
    },
}

# HTML/EJS exact visible text replacements. The patcher applies these only as >text<.
HTML_GLOBAL = {
    "OK": "确定",
    "Cancel": "取消",
    "Close": "关闭",
    "Yes": "是",
    "No": "否",
    "Rank": "排名",
    "Rank？？": "排名？？",
    "Tips": "提示",
    "STORY": "剧情",
    "NEXT": "下一步",
    "Reward": "奖励",
    "All": "全部",
}

HTML_BY_PATH: dict[str, dict[str, str]] = {
    "magica/template/formation/DeckFormation.html": {
        "Select a Magical Formation.": "请选择阵型。",
        "Import Team": "导入队伍",
        "Tap the Team you'd like to edit.": "点击要编辑的队伍。",
        "Tap the Team you'd like to import.": "点击要导入的队伍。",
        "Copy": "复制",
        "Start": "开始",
        "AUTO": "自动",
        "PLAY": "播放",
        "A preview of your current Team.": "当前队伍预览。",
        "Strategy Tips": "战术提示",
        "Switch Team": "切换队伍",
        "Rename Team": "重命名队伍",
        "Emojis cannot be used.": "无法使用表情符号。",
        "Dissolve Team": "解散队伍",
        "Are you sure you want to dissolve?": "确定要解散吗？",
        "Auto Team": "自动编成",
        "Choose what type of Team you'd like to auto-create.": "请选择要自动编成的队伍类型。",
        "Element": "属性",
        "Type of Team": "队伍类型",
        "Select what kind of Team you'd like.": "请选择队伍类型。",
    },
    "magica/template/gacha/GachaProbabilityPop.html": {
        "Pick up对象魔法少女": "概率提升对象魔法少女",
        "Pick up对象记忆结晶": "概率提升对象记忆结晶",
    },
    "magica/template/arena/ArenaReward.html": {
        "Layer Rewards": "镜层奖励",
        "◆ Endless Mirrors": "◆ 无尽镜界",
    },
    "magica/template/chara/CharaDetail.html": {
        "Hide Profile": "隐藏档案",
        "About": "简介",
        "Skills": "技能",
        "Memoria": "记忆结晶",
        "Settings": "设置",
        "Lvl.": "等级",
        "In:&nbsp;": "还需：&nbsp;",
        "Type": "类型",
        "Magic Lvl.": "觉醒等级",
        "Magia Lvl. ": "Magia 等级 ",
        "Episode Lvl.": "剧情等级",
        "Total": "总计",
        "Base": "基础",
        "Materials": "素材",
        "Enhance Magic": "强化魔法少女",
        "Awaken": "觉醒",
        "Enhance Magia": "强化 Magia",
        "Unlock Magic": "魔力解放",
        "Disks": "行动盘",
        "EX Skill": "EX 技能",
        "Connect": "连携",
        "Locked": "未解放",
        "Complete Doppel Quests to Unlock": "完成魔女化身任务后解放",
        "Home Page Outfit": "主页服装",
    },
    "magica/template/chara/CharaData.html": {
        "Lvl.": "等级",
        "Magic Lvl.": "觉醒等级",
        "Episode Lvl.": "剧情等级",
        "Memoria Slots": "记忆结晶槽位",
    },
    "magica/template/collection/StoryCollection.html": {
        "Main Story 【Arc 1】": "主线剧情【第一部】",
        "Main Story 【Arc 2】": "主线剧情【第二部】",
        "Another Story 【Arc 1】": "另一篇章【第一部】",
        "Another Story 【Arc 2】": "另一篇章【第二部】",
        "Magical Girl Stories": "魔法少女剧情",
        "Mirrors Story": "镜界剧情",
        "Event Stories": "活动剧情",
        "Battle Museum": "战斗博物馆",
        "Special": "特别篇",
        "Arc 1 OP Movie": "第一部 OP 动画",
        "Arc 1 ED Movie": "第一部 ED 动画",
        "Arc 1 ED (NA)": "第一部 ED（北美版）",
        "Arc 2 OP Movie": "第二部 OP 动画",
        "Arc 2 ED Movie": "第二部 ED 动画",
        "Main Story": "主线剧情",
        "Prologue": "序章",
        "CLEAR": "已通关",
        "Playback": "播放",
        "View Gallery": "查看图库",
        "The Story So Far": "剧情回顾",
        "NEXT": "下一页",
        "Quest": "任务",
        "LOCK": "未解锁",
        "Chapter 12": "第 12 章",
        "Requirements to Unlock Story": "剧情解锁条件",
    },
    "magica/template/user/MyProfilePopup.html": {
        "Change": "更改",
        "No Title Set": "未设置称号",
        "Rank": "玩家等级",
        "Player ID": "玩家 ID",
        "Copy": "复制",
        "Mirrors": "镜界",
        "◆Endless Mirrors": "◆无尽镜界",
        "Mirrors Pts": "镜界 Pt",
        "Wins": "胜场",
        "Settings": "设置",
        "Support": "支援",
        "◆Emblem Display Settings": "◆徽章显示设置",
        "ON": "开启",
        "OFF": "关闭",
        "Ranking": "排名",
        "Ranked Match": "排位赛",
    },
    "magica/template/config/ConfigTop.html": {
        "Game Settings": "游戏设置",
        "Notifications": "通知",
        "Manage Data": "数据管理",
        "Transfer Data": "数据转移",
        "Serial Code": "序列号",
        "Player Data": "玩家数据",
        "Player": "玩家",
        "Player Name": "玩家名称",
        "Change": "更改",
        "Player ID": "玩家 ID",
        "Copy": "复制",
        "Volume": "音量",
        "SE": "音效",
        "Voices": "语音",
        "Event": "活动",
        "ON": "开启",
        "AP Recovery": "AP 回复",
        "Voice Settings": "语音设置",
        "Play with Voices": "播放语音",
        "Play without Voices": "不播放语音",
        "Keep Story Voices After Viewing": "剧情播放后保留语音数据",
        "Download All Story Voice Data": "下载全部剧情语音数据",
        "Video Settings": "视频设置",
        "Play with High Quality Videos": "播放高画质视频",
        "Play with Low Quality Videos": "播放低画质视频",
        "Play without Videos": "不播放视频",
        "Keep Transformation Videos After Viewing": "变身动画播放后保留视频数据",
        "Video Data Download": "视频数据下载",
        "Download All Data": "下载全部数据",
        "Clear Cache": "清除缓存",
        "Transfer ID": "转移 ID",
        "Password": "密码",
        "Not Set": "未设置",
        "Set": "设置",
        "※Passwords must be 8-15 characters.": "※密码须为 8～15 个字符。",
        "※If you don't set a Transfer Password on this device, you can not transfer data to your new device.": "※若未在本设备上设置转移密码，将无法把数据转移到新设备。",
        "Set Password": "设置密码",
        "※Data can be transferred from the Title Page.": "※可在标题画面进行数据转移。",
        "Currently Available Promotions": "当前可用活动",
        "Availability：": "有效期：",
        "Delete Data": "删除数据",
        "※Once deleted, data CANNOT be recovered under any circumstances.": "※数据删除后在任何情况下都无法恢复。",
        "Delete Player Data": "删除玩家数据",
        "Confirm Password": "确认密码",
        "Your Transfer ID is needed when transfering accounts.": "转移账号时需要使用转移 ID。",
        "Please make a note of your ID and password.": "请记录并妥善保管 ID 与密码。",
        "Before": "更改前",
        "Check": "确认",
        "Play With Voices": "播放语音",
        "Play Without Voices": "不播放语音",
        "Play Without Videos": "不播放视频",
        "Play With High Quality Videos": "播放高画质视频",
        "Play With Low Quality Videos": "播放低画质视频",
        "After": "更改后",
        "Videos": "视频",
        "※We recommend using a stable Wi-Fi network.": "※建议使用稳定的 Wi-Fi 网络。",
        "Due to the amount of data, we recommend using a stable Wi-Fi network.": "数据量较大，建议使用稳定的 Wi-Fi 网络。",
        "※Player Names can be changed only once a day.": "※玩家名称每天只能修改一次。",
    },
}


# Path-specific raw replacements for reviewed visible fragments that are split by
# nested spans, <br> tags or EJS expressions and therefore are not complete text nodes.
RAW_BY_PATH: dict[str, dict[str, str]] = {
    "magica/js/collection/StoryCollection.js": {
        r"\x3c/span\x3e\x3cbr\x3eAre you sure?": r"\x3c/span\x3e\x3cbr\x3e确定吗？",
        'c.title="Main Story【Arc "+b.chapter.partNo+\n"】　"+b.chapter.chapterNoForView':
            'c.title="主线剧情【第"+b.chapter.partNo+\n"部】　"+b.chapter.chapterNoForView',
        'g.storyTitle="Ep."+a.questBattle.sectionIndex;':
            'g.storyTitle="第"+a.questBattle.sectionIndex+"话";',
        'g.sectionNo="Ep."+a.section.genericIndex':
            'g.sectionNo="第"+a.section.genericIndex+"话"',
        'e.title="Ep."+n;': 'e.title="第"+n+"话";',
        'e.sectionNo=a.questBattle.parameterMap&&a.questBattle.parameterMap.FLOOR?"Stage"+a.questBattle.parameterMap.FLOOR:"Stage"+(d+1);':
            'e.sectionNo=a.questBattle.parameterMap&&a.questBattle.parameterMap.FLOOR?"第"+a.questBattle.parameterMap.FLOOR+"层":"第"+(d+1)+"层";',
    },
    "magica/js/view/memoria/PieceArchiveView.js": {
        '"所持枠"': '"持有栏位"',
        '"メモリア保管庫"': '"记忆结晶仓库"',
        '"選択したメモリアを"': '"将选中的记忆结晶从"',
        r'"から\x3cbr\x3e\x3cspan': r'"移动到\x3cbr\x3e\x3cspan',
        r'\x3c/span\x3eに移動します。\x3cbr\x3eよろしいですか？': r'\x3c/span\x3e。\x3cbr\x3e确定吗？',
        r'\x3c/span\x3eへ移動しました。': r'\x3c/span\x3e。',
        'decideBtnText:"OK"': 'decideBtnText:"确定"',
        'closeBtnText:"キャンセル"': 'closeBtnText:"取消"',
        'closeBtnText:"OK"': 'closeBtnText:"确定"',
        'console.log("保管庫に送る")': 'console.log("移入仓库")',
        'console.log("一覧に送る")': 'console.log("移回列表")',
    },
    "magica/template/formation/DeckFormation.html": {
        "※All equipped Memoria will be unequipped.": "※所有已装备的记忆结晶将被卸下。",
        "<span class=\"checkBox\"></span>Offensive": "<span class=\"checkBox\"></span>攻击型",
        "<span class=\"checkBox\"></span>Defensive": "<span class=\"checkBox\"></span>防御型",
        "<span class=\"checkBox\"></span>Balanced": "<span class=\"checkBox\"></span>均衡型",
        "Auto-create a Team that is effective against this Kimochi battle.":
            "自动编成对当前心魔战有效的队伍。",
    },
    "magica/template/chara/CharaData.html": {
        ">Magia Lvl. </p>": ">Magia 等级 </p>",
    },
    "magica/template/user/MyProfilePopup.html": {
        "◆Ranking": "◆排名",
        "◆Ranked Match": "◆排位赛",
        "By Selecting OFF, your Emblem will be hidden<br>and no longer be publicly displayed.":
            "选择“关闭”后，徽章将被隐藏<br>并且不再公开显示。",
        "The Emblem you have selected will now be publicly displayed. ":
            "当前选择的徽章将公开显示。 ",
    },
    "magica/template/loginBonus/loginBonusPopupTemp.html": {
        "Day<%= gameUser.loginBonusCount %></span>":
            "第<%= gameUser.loginBonusCount %>天</span>",
        "Day<%= model.day %></span>": "第<%= model.day %>天</span>",
        "Day1起经过7天后,将切换为下一种登录奖励":
            "从第1天开始，经过7天后将切换为下一种登录奖励",
    },
    "magica/template/purchase/PurchaseTemps.html": {
        "未成年の方は保護者の同意を得て下さい。": "未成年人请取得监护人同意。",
    },
    "magica/template/config/ConfigTop.html": {
        "Try doing this when images and voices are not being played/displayed.":
            "图片或语音无法正常播放／显示时，请尝试此操作。",
        "Set your Transfer Password here.": "请在此设置数据转移密码。",
        "Once you set a password, make sure not to forget your Transfer ID/Password.":
            "设置后请妥善保管转移 ID 和密码。",
        "Please enter the serial code.": "请输入序列号。",
        "※Serial codes are 12-digit alphanumeric codes, excluding hyphens.":
            "※序列号为 12 位英数字，不含连字符。",
        "※Please claim your special reward from the Present Box.":
            "※请前往礼物盒领取特典奖励。",
        "No promotions are currently available.": "当前没有可用活动。",
        "Completely erase your player data.": "彻底删除玩家数据。",
        "This will delete all downloaded ": "将删除所有已下载的",
        " will not be played after this change.": "更改后将不再播放。",
        "Downloading ": "正在下载",
        " Data<br>": "数据<br>",
        " will be played after this change.": "更改后将播放。",
        "Downloading all necessary images, voices, and videos.":
            "将下载所需的全部图片、语音和视频。",
        "Please enter your new Player Name.": "请输入新的玩家名称。",
        "You have chosen to no longer keep the following data on your device after use.":
            "你已选择使用后不再在设备中保留以下数据。",
        "※This data will be deleted when the app is closed and will be downloaded again when needed.":
            "※应用关闭时会删除这些数据，需要时将重新下载。",
        "You have chosen to keep the following data on your device after use.":
            "你已选择使用后继续在设备中保留以下数据。",
        "※This data will not be deleted when the app is closed.":
            "※应用关闭时不会删除这些数据。",
    },
}

# Reviewed residual phrases that must be absent from runtime files after patching.
FORBIDDEN_RUNTIME = {
    "Select a Magical Formation.", "Import Team", "Tap the Team you'd like to edit.",
    "Game Settings", "Manage Data", "Transfer Data", "Voice Settings",
    "Play with Voices", "Play without Voices", "Video Settings", "Clear Cache",
    "Your password has been set.", "Your Player Name has been updated.",
    "Story Confirmation", "There is no story available.", "Story Unlocked",
    "Layer Rewards", "Hide Profile", "Home Page Outfit", "No Title Set",
    "Pick up对象魔法少女", "Pick up对象记忆结晶",
    "Are you sure you want to dissolve?", "Select what kind of Team you'd like.",
    ">Tips<", ">STORY<", ">NEXT<", ">Reward<", ">Rank<", "Rank？？", ">Day<",
    "メモリア保管庫", "選択したメモリアを", "よろしいですか？", "キャンセル",
    "未成年の方は保護者の同意を得て下さい。",
}


def _escape_unescaped_delimiter(value: str, delimiter: str) -> str:
    """Escape only delimiter characters that are not already escaped in JS source text."""
    if delimiter not in value:
        return value
    out: list[str] = []
    slash_run = 0
    for ch in value:
        if ch == "\\":
            out.append(ch)
            slash_run += 1
            continue
        if ch == delimiter and slash_run % 2 == 0:
            out.append("\\")
        out.append(ch)
        slash_run = 0
    return "".join(out)


def replace_js_literals(text: str, mapping: dict[str, str]) -> tuple[str, dict[str, int]]:
    """Replace complete JS string literals while preserving every unmatched byte verbatim.

    This is intentionally not a JavaScript pretty-printer. It only finds a quoted span,
    compares its *raw source contents* with reviewed keys, and splices a replacement when
    there is an exact match. Unmatched literals—including escaped quotes in minified code—
    are copied from the original source without reconstruction.
    """
    counts = {k: 0 for k in mapping}
    if not mapping:
        return text, counts

    out: list[str] = []
    last = 0
    i = 0
    n = len(text)
    while i < n:
        q = text[i]
        if q not in "'\"`":
            i += 1
            continue
        start = i
        i += 1
        while i < n:
            c = text[i]
            if c == "\\" and i + 1 < n:
                i += 2
                continue
            if c == q:
                raw = text[start + 1:i]
                replacement = mapping.get(raw)
                if replacement is not None:
                    out.append(text[last:start + 1])
                    out.append(_escape_unescaped_delimiter(replacement, q))
                    out.append(q)
                    counts[raw] += 1
                    last = i + 1
                i += 1
                break
            i += 1
        else:
            break
    out.append(text[last:])
    return "".join(out), counts


def replace_html_visible(text: str, mapping: dict[str, str]) -> tuple[str, dict[str, int]]:
    counts = {k: 0 for k in mapping}
    for old, new in mapping.items():
        # Replace an exact visible text prefix between tags, preserving surrounding whitespace.
        # This also covers template nodes such as >Rank<%= model.rank %> and indented > OK <.
        pat = re.compile(r">(?P<lead>\s*)" + re.escape(old) + r"(?P<trail>\s*)(?=<)")
        def repl(m: re.Match[str]) -> str:
            return ">" + m.group("lead") + new + m.group("trail")
        text, n = pat.subn(repl, text)
        counts[old] += n
    return text, counts


def sha256(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda: f.read(1 << 20), b""):
            h.update(chunk)
    return h.hexdigest()


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("input_zip", type=Path)
    ap.add_argument("output_zip", type=Path)
    ap.add_argument("--report", type=Path)
    ns = ap.parse_args()

    report: dict[str, object] = {
        "input": str(ns.input_zip),
        "input_sha256": sha256(ns.input_zip),
        "modified_files": [],
        "replacement_counts": {},
        "node_checked": [],
        "json_parsed": [],
        "html_structure_compared": [],
        "forbidden_residuals": [],
    }

    with tempfile.TemporaryDirectory(prefix="cn-js-en-i18n-") as td:
        root = Path(td) / "root"
        root.mkdir()
        with zipfile.ZipFile(ns.input_zip) as zf:
            input_infos = {info.filename: info for info in zf.infolist() if not info.is_dir()}
            input_names = list(input_infos)
            input_structure: dict[str, tuple[int, ...]] = {}
            for name in input_names:
                if name.endswith((".html", ".ejs")):
                    source = zf.read(name).decode("utf-8")
                    input_structure[name] = (
                        source.count("<%"), source.count("%>"),
                        source.count("<script"), source.count("</script>"),
                        source.count("<style"), source.count("</style>"),
                    )
            zf.extractall(root)

        modified: list[Path] = []
        total_counts: dict[str, int] = {}
        for p in sorted(root.rglob("*")):
            if not p.is_file() or p.suffix.lower() not in {".js", ".html", ".ejs"}:
                continue
            rel = p.relative_to(root).as_posix()
            before = p.read_text("utf-8")
            after = before
            file_counts: dict[str, int] = {}
            if p.suffix.lower() == ".js":
                # The jQuery file contains the generated 23-dictionary runtime payload.
                # Do not run generic UI substitutions through the minified library/injector;
                # its data source remains the JSON dictionaries and is validated separately.
                mapping = {} if rel == "magica/js/libs/jquery-3.7.1.min.js" else dict(JS_GLOBAL)
                mapping.update(JS_BY_PATH.get(rel, {}))
                after, file_counts = replace_js_literals(after, mapping)
            else:
                mapping = dict(HTML_GLOBAL)
                mapping.update(HTML_BY_PATH.get(rel, {}))
                after, file_counts = replace_html_visible(after, mapping)
            raw_counts: dict[str, int] = {}
            for old_raw, new_raw in sorted(
                    RAW_BY_PATH.get(rel, {}).items(),
                    key=lambda item: len(item[0]), reverse=True):
                n = after.count(old_raw)
                if n:
                    after = after.replace(old_raw, new_raw)
                raw_counts[old_raw] = n
            for k, v in file_counts.items():
                if v:
                    total_counts[f"{rel}\t{k}"] = v
            for k, v in raw_counts.items():
                if v:
                    total_counts[f"{rel}\tRAW:{k}"] = v
            if after != before:
                p.write_text(after, "utf-8", newline="")
                modified.append(p)

        # Every path-specific reviewed item must match at least once.
        missing: list[str] = []
        for rel, mapping in JS_BY_PATH.items():
            for old in mapping:
                if total_counts.get(f"{rel}\t{old}", 0) == 0:
                    missing.append(f"{rel}: JS literal not found: {old}")
        for rel, mapping in HTML_BY_PATH.items():
            for old in mapping:
                if total_counts.get(f"{rel}\t{old}", 0) == 0:
                    missing.append(f"{rel}: HTML visible text not found: {old}")
        for rel, mapping in RAW_BY_PATH.items():
            for old in mapping:
                if total_counts.get(f"{rel}\tRAW:{old}", 0) == 0:
                    missing.append(f"{rel}: raw reviewed fragment not found: {old}")
        if missing:
            raise SystemExit("reviewed replacements missing:\n" + "\n".join(missing))

        # Parse every data dictionary and syntax-check every runtime JavaScript file,
        # not only modified files. This validates the final deployable tree as a whole.
        for p in sorted(root.rglob("*.json")):
            try:
                json.loads(p.read_text("utf-8"))
            except Exception as exc:
                raise SystemExit(f"JSON parse failed: {p}: {exc}") from exc
            report["json_parsed"].append(p.relative_to(root).as_posix())

        node = shutil.which("node")
        if node:
            for p in sorted(root.rglob("*.js")):
                cp = subprocess.run([node, "--check", str(p)], text=True,
                                    stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
                if cp.returncode != 0:
                    raise SystemExit(f"node --check failed: {p}\n{cp.stdout}")
                report["node_checked"].append(p.relative_to(root).as_posix())

        # Text replacements must not alter EJS/script/style boundary counts.
        for rel, before_sig in input_structure.items():
            source = (root / rel).read_text("utf-8")
            after_sig = (
                source.count("<%"), source.count("%>"),
                source.count("<script"), source.count("</script>"),
                source.count("<style"), source.count("</style>"),
            )
            if before_sig != after_sig:
                raise SystemExit(
                    f"HTML/EJS structure sentinel changed: {rel}: "
                    f"{before_sig} -> {after_sig}")
            report["html_structure_compared"].append(rel)

        residuals: list[dict[str, str]] = []
        for p in root.rglob("*"):
            if not p.is_file() or p.suffix.lower() not in {".js", ".html", ".ejs"}:
                continue
            rel = p.relative_to(root).as_posix()
            if "/test/" in f"/{rel}/" or rel.endswith("/LawPopup.html"):
                continue
            s = p.read_text("utf-8", errors="ignore")
            for phrase in FORBIDDEN_RUNTIME:
                if phrase in s:
                    residuals.append({"path": rel, "text": phrase})
        report["forbidden_residuals"] = residuals
        if residuals:
            raise SystemExit("known runtime English residuals remain:\n" +
                             "\n".join(f"{x['path']}: {x['text']}" for x in residuals))

        ns.output_zip.parent.mkdir(parents=True, exist_ok=True)
        # Reuse each input entry's metadata and original order, so identical source bytes
        # produce a byte-for-byte deterministic overlay ZIP across repeated builds.
        with zipfile.ZipFile(ns.output_zip, "w") as zf:
            for rel in input_names:
                original = input_infos[rel]
                info = zipfile.ZipInfo(rel, date_time=original.date_time)
                info.compress_type = zipfile.ZIP_DEFLATED
                info.create_system = original.create_system
                info.external_attr = original.external_attr
                info.internal_attr = original.internal_attr
                info.flag_bits = original.flag_bits
                info.extra = original.extra
                info.comment = original.comment
                zf.writestr(info, (root / rel).read_bytes(),
                            compress_type=zipfile.ZIP_DEFLATED, compresslevel=9)
        with zipfile.ZipFile(ns.output_zip) as zf:
            output_names = [info.filename for info in zf.infolist() if not info.is_dir()]
        if output_names != input_names:
            raise SystemExit("ZIP entry paths/order differ from input overlay")

        report["output"] = str(ns.output_zip)
        report["output_sha256"] = sha256(ns.output_zip)
        report["modified_files"] = [p.relative_to(root).as_posix() for p in modified]
        report["replacement_counts"] = total_counts
        report["zip_entries"] = sum(1 for p in root.rglob("*") if p.is_file())

    report_path = ns.report or ns.output_zip.with_suffix(ns.output_zip.suffix + ".qa.json")
    report_path.write_text(json.dumps(report, ensure_ascii=False, indent=2), "utf-8")
    print(json.dumps({
        "output": str(ns.output_zip),
        "sha256": report["output_sha256"],
        "modified_files": len(report["modified_files"]),
        "replacements": sum(report["replacement_counts"].values()),
        "node_checked": len(report["node_checked"]),
        "json_parsed": len(report["json_parsed"]),
        "html_structure_compared": len(report["html_structure_compared"]),
        "zip_entries": report["zip_entries"],
        "report": str(report_path),
    }, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
