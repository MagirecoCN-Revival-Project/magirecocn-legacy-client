#!/usr/bin/env python3
"""Naturalise structured Magia/skill descriptions from pinned Japanese data.

Only descriptions that can be translated without unresolved Japanese syntax are
replaced.  Existing Chinese is retained for unsupported records and listed in
the QA report.  The 23 JSON dictionaries are then re-embedded into jQuery so
the runtime injector and standalone JSON remain exactly consistent.
"""
from __future__ import annotations

import argparse
from collections import Counter
import copy
import hashlib
import json
from pathlib import Path, PurePosixPath
import re
import sys
import zipfile

JQUERY = "magica/js/libs/jquery-3.7.1.min.js"
TARGET_FILES = (
    "cardMagiaMap.json",
    "doppelCardMagiaMap.json",
    "pieceSkillMap.json",
    "emotionSkillMap.json",
)
KANA = re.compile(r"[\u3041-\u3096\u30a1-\u30fa\u30fd-\u30ff]")
UNRESOLVED = (
    "敵", "味全", "敵全", "敵単", "必ず", "確率で", "攻撃力", "防御力",
    "回復", "解除", "付与", "状態異常", "属性強化", "ダメージ", "ディスク",
    "スキル", "マギア", "ドッペル", "バリア", "デバフ", "バフ", "蘇生",
)
HIGH_CONFIDENCE_ARTIFACTS = (
    "Ajizen", "Aji全部", "Aji 全部", "aji 全部", "全口味", "全部口味",
    "所有口味", "各味", "味增", "Own", "From", "来自)", "自备)",
    "创造伤害", "敌方全体 至 伤害", "敌方单体 至 伤害", "毒药",
)

EXACT_REPLACEMENTS = (
    ("敵全体に属性強化ダメージ", "对敌方全体造成属性强化伤害"),
    ("敵単体に属性強化ダメージ", "对敌方单体造成属性强化伤害"),
    ("敵全体にダメージ", "对敌方全体造成伤害"),
    ("敵単体にダメージ", "对敌方单体造成伤害"),
    ("敵全体へダメージ", "对敌方全体造成伤害"),
    ("敵単体へダメージ", "对敌方单体造成伤害"),
    ("全ディスク効果", "全指令盘效果"),
    ("Chargeディスクダメージ", "Charge指令盘伤害"),
    ("Blast ダメージ", "Blast伤害"),
    ("Accele MP", "Accele MP"),
    ("Charge後ダメージ", "Charge后伤害"),
    ("マギアダメージ", "Magia伤害"),
    ("ドッペルダメージ", "Doppel伤害"),
    ("火属性攻撃力", "火属性攻击力"),
    ("水属性攻撃力", "水属性攻击力"),
    ("木属性攻撃力", "木属性攻击力"),
    ("光属性攻撃力", "光属性攻击力"),
    ("闇属性攻撃力", "暗属性攻击力"),
    ("攻撃力", "攻击力"),
    ("防御力", "防御力"),
    ("与えるダメージ", "造成伤害"),
    ("ダメージカット無視", "无视伤害削减"),
    ("ダメージカット状態", "伤害削减状态"),
    ("ダメージ", "伤害"),
    ("状態異常耐性", "异常状态耐性"),
    ("状態異常解除", "解除异常状态"),
    ("状態強化解除", "解除状态强化"),
    ("デバフ効果を1回無効", "1次Debuff无效"),
    ("デバフ効果を2回無効", "2次Debuff无效"),
    ("デバフ効果を3回無効", "3次Debuff无效"),
    ("状態異常を1回無効", "1次异常状态无效"),
    ("状態異常を2回無効", "2次异常状态无效"),
    ("状態異常を3回無効", "3次异常状态无效"),
    ("デバフ解除", "解除Debuff"),
    ("バフ解除", "解除Buff"),
    ("デバフ反射", "Debuff反射"),
    ("HP自動回復", "HP自动回复"),
    ("MP自動回復", "MP自动回复"),
    ("HP回復", "HP回复"),
    ("MP回復", "MP回复"),
    ("MP獲得量", "MP获得量"),
    ("MPダメージ", "MP伤害"),
    ("被ダメージ時MPUP", "受击时MPUP"),
    ("Blast攻撃時MP獲得", "Blast攻击时获得MP"),
    ("Charge消費なし", "Charge不消耗"),
    ("消費MPなし", "MP消耗为0"),
    ("スキルクイック", "技能冷却加速"),
    ("必ず強化カウンター", "必定强化反击"),
    ("必ずカウンター", "必定反击"),
    ("必ず回避無効", "必定回避无效"),
    ("必ず回避", "必定回避"),
    ("必ず防御無視", "必定无视防御"),
    ("必ずダメージカット無視", "必定无视伤害削减"),
    ("必ず挑発", "必定挑衅"),
    ("必ず毒", "必定中毒"),
    ("必ずやけど", "必定烧伤"),
    ("必ず霧", "必定雾"),
    ("必ず暗闇", "必定黑暗"),
    ("必ず呪い", "必定诅咒"),
    ("必ず幻惑", "必定幻惑"),
    ("必ず魅了", "必定魅惑"),
    ("必ず拘束", "必定拘束"),
    ("必ずスタン", "必定眩晕"),
    ("必ずスキル不可", "必定技能封印"),
    ("必ずマギア不可", "必定Magia封印"),
    ("確率でクリティカル", "概率暴击"),
    ("クリティカル", "暴击"),
    ("確率で", "概率"),
    ("必ず", "必定"),
    ("強化毒", "强化中毒"),
    ("強化呪い", "强化诅咒"),
    ("毒", "中毒"),
    ("やけど", "烧伤"),
    ("霧", "雾"),
    ("暗闇", "黑暗"),
    ("呪い", "诅咒"),
    ("幻惑", "幻惑"),
    ("魅了", "魅惑"),
    ("拘束", "拘束"),
    ("スタン", "眩晕"),
    ("スキル不可", "技能封印"),
    ("マギア不可", "Magia封印"),
    ("挑発", "挑衅"),
    ("回避無効", "回避无效"),
    ("防御無視", "无视防御"),
    ("追撃", "追击"),
    ("カウンター", "反击"),
    ("バリア", "屏障"),
    ("を付与", ""),
    ("蘇生", "复活"),
    ("ヴァリアブル", "Variable"),
    ("さらに", "进一步"),
    ("属性強化", "属性强化"),
    ("ディスク", "指令盘"),
    ("味全", "我方全体"),
    ("敵全", "敌方全体"),
    ("敵単", "敌方单体"),
    ("自身", "自身"),
    ("自", "自身"),
)

SAFE_CN_REPLACEMENTS = (
    ("Ajizen", "我方全体"), ("Aji全部", "我方全体"),
    ("Aji 全部", "我方全体"), ("aji 全部", "我方全体"),
    ("全口味", "我方全体"), ("全部口味", "我方全体"),
    ("所有口味", "我方全体"), ("各味", "我方全体"),
    ("味增", "我方全体"), ("(Own/", "(自身/"),
    ("(From/", "(自身/"), ("(来自)", "(自身)"),
    ("(自备)", "(自身)"), ("创造伤害", "造成伤害"),
    ("毒药", "中毒"), ("爆炸 伤害", "Blast伤害"),
    ("爆炸伤害", "Blast伤害"), ("加速 MP", "Accele MP"),
    ("充电后", "Charge后"), ("所有磁盘效果", "全指令盘效果"),
    ("伤害 切割状态", "伤害削减状态"),
    ("伤害切割状态", "伤害削减状态"),
    ("忽略伤害 切割", "无视伤害削减"),
    ("免费消费", "MP消耗为0"), ("无费用消耗", "MP消耗为0"),
)


def translate_description(source: str) -> tuple[str | None, str | None]:
    text = source.replace("\u3000", " ").strip()
    text = re.sub(r"ランダム\s*(\d+)回\s*属性強化ダメージ", r"随机\1次属性强化伤害", text)
    text = re.sub(r"ランダム\s*(\d+)回\s*ダメージ", r"随机\1次伤害", text)
    text = text.replace("縦方向にダメージ", "纵向伤害")
    text = text.replace("横方向にダメージ", "横向伤害")
    for old, new in EXACT_REPLACEMENTS:
        text = text.replace(old, new)
    text = re.sub(r"屏障\((\d+)\)(?:を)?", r"赋予屏障（\1）", text)
    text = re.sub(r"\s*&\s*", "＆", text)
    text = re.sub(r"\s+", " ", text).strip()
    text = text.replace(" (", "（").replace(")", "）")
    text = text.replace("/ ", "/").replace(" /", "/")
    text = text.replace("敌方全体に", "敌方全体").replace("敌方单体に", "敌方单体")
    unresolved = [token for token in UNRESOLVED if token in text]
    if KANA.search(text) or unresolved:
        reason = "kana" if KANA.search(text) else "unresolved:" + ",".join(unresolved[:5])
        return None, reason
    return text, None


def safe_cn(text: str) -> str:
    result = text
    for old, new in SAFE_CN_REPLACEMENTS:
        result = result.replace(old, new)
    result = re.sub(r"敌方全体\s*至\s*伤害", "对敌方全体造成伤害", result)
    result = re.sub(r"敌方单体\s*至\s*伤害", "对敌方单体造成伤害", result)
    result = re.sub(r"伤害(\[[^\]]+\])\s*至\s*敌方全体", r"对敌方全体造成伤害\1", result)
    result = re.sub(r"伤害(\[[^\]]+\])\s*至\s*敌方单体", r"对敌方单体造成伤害\1", result)
    result = re.sub(r"\s+([＆，。；：）])", r"\1", result)
    result = re.sub(r"（\s+", "（", result)
    return result


def clone_info(info: zipfile.ZipInfo) -> zipfile.ZipInfo:
    copied = copy.copy(info)
    copied.CRC = 0
    copied.file_size = 0
    copied.compress_size = 0
    return copied


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("input_zip", type=Path)
    parser.add_argument("output_zip", type=Path)
    parser.add_argument("--jp-source-dir", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    parser.add_argument("--minimum-source-translations", type=int, default=50)
    args = parser.parse_args()
    try:
        source_maps = {}
        for filename in TARGET_FILES:
            path = args.jp_source_dir / filename
            source_maps[filename] = json.loads(path.read_text("utf-8-sig"))

        with zipfile.ZipFile(args.input_zip) as source_zip:
            infos = source_zip.infolist()
            payload = {info.filename: source_zip.read(info.filename) for info in infos if not info.is_dir()}

        runtime_json = {}
        for name, raw in payload.items():
            if name.startswith("magica/js/libs/") and name.endswith(".json"):
                runtime_json[PurePosixPath(name).name] = json.loads(raw.decode("utf-8-sig"))
        if len(runtime_json) != 23:
            raise RuntimeError(f"expected 23 runtime dictionaries, found {len(runtime_json)}")

        translated = 0
        safe_changes = 0
        skipped = Counter()
        changed_by_file = Counter()
        examples = []
        for filename in TARGET_FILES:
            current = runtime_json.get(filename)
            jp = source_maps[filename]
            if not isinstance(current, dict) or not isinstance(jp, dict):
                raise RuntimeError(f"dictionary shape mismatch: {filename}")
            for record_id, record in current.items():
                if not isinstance(record, dict):
                    continue
                for field in ("name", "shortDescription"):
                    value = record.get(field)
                    if isinstance(value, str):
                        cleaned = safe_cn(value)
                        if cleaned != value:
                            record[field] = cleaned
                            safe_changes += 1
                            changed_by_file[filename] += 1
                jp_record = jp.get(str(record_id))
                if not isinstance(jp_record, dict):
                    skipped["missing-source-id"] += 1
                    continue
                source_description = jp_record.get("shortDescription")
                if not isinstance(source_description, str) or not source_description:
                    skipped["missing-source-description"] += 1
                    continue
                natural, reason = translate_description(source_description)
                if natural is None:
                    skipped[reason or "unsupported"] += 1
                    continue
                old = record.get("shortDescription")
                if isinstance(old, str) and old != natural:
                    record["shortDescription"] = natural
                    translated += 1
                    changed_by_file[filename] += 1
                    if len(examples) < 40:
                        examples.append({
                            "file": filename, "id": str(record_id),
                            "before": old, "sourceJa": source_description, "after": natural,
                        })

        if translated < args.minimum_source_translations:
            raise RuntimeError(
                f"source-driven translation coverage too low: {translated} < "
                f"{args.minimum_source_translations}"
            )

        # Write the 23 standalone dictionaries back first.
        for filename, obj in runtime_json.items():
            path = "magica/js/libs/" + filename
            payload[path] = (json.dumps(
                obj, ensure_ascii=False, indent=2, separators=(",", ": ")
            ) + "\n").encode("utf-8")

        # Rebuild the exact jQuery cn dictionary from final JSON values.
        jquery = payload[JQUERY].decode("utf-8-sig")
        marker = "var cn = "
        at = jquery.find(marker)
        if at < 0:
            raise RuntimeError("jQuery cn dictionary marker not found")
        json_start = at + len(marker)
        _, consumed = json.JSONDecoder().raw_decode(jquery[json_start:])
        json_end = json_start + consumed
        ordered = {}
        for key in json.loads(jquery[json_start:json_end]).keys():
            filename = key + ".json"
            if filename not in runtime_json:
                raise RuntimeError(f"jQuery key has no JSON file: {key}")
            ordered[key] = runtime_json[filename]
        if len(ordered) != 23:
            raise RuntimeError(f"expected 23 jQuery dictionaries, found {len(ordered)}")
        embedded = json.dumps(ordered, ensure_ascii=False, separators=(",", ":"))
        jquery = jquery[:json_start] + embedded + jquery[json_end:]
        payload[JQUERY] = jquery.encode("utf-8")

        residuals = Counter()
        for filename in TARGET_FILES:
            for record in runtime_json[filename].values():
                if not isinstance(record, dict):
                    continue
                for field in ("name", "shortDescription"):
                    value = record.get(field)
                    if not isinstance(value, str):
                        continue
                    for token in HIGH_CONFIDENCE_ARTIFACTS:
                        if token in value:
                            residuals[token] += 1
        if residuals:
            raise RuntimeError(f"high-confidence machine artifacts remain: {dict(residuals)}")

        args.output_zip.parent.mkdir(parents=True, exist_ok=True)
        with zipfile.ZipFile(args.output_zip, "w") as output:
            for info in infos:
                if info.is_dir():
                    output.writestr(clone_info(info), b"")
                    continue
                output.writestr(clone_info(info), payload[info.filename])

        report = {
            "schema": 1,
            "input": str(args.input_zip),
            "output": str(args.output_zip),
            "outputSha256": hashlib.sha256(args.output_zip.read_bytes()).hexdigest(),
            "sourceDrivenDescriptionReplacements": translated,
            "safeChineseNormalisations": safe_changes,
            "changedByFile": dict(changed_by_file),
            "skippedByReason": dict(skipped),
            "highConfidenceArtifactsRemaining": dict(residuals),
            "jqueryDictionaryRebuilt": True,
            "standaloneJsonCount": len(runtime_json),
            "examples": examples,
        }
        rendered = json.dumps(report, ensure_ascii=False, indent=2) + "\n"
        if args.report:
            args.report.parent.mkdir(parents=True, exist_ok=True)
            args.report.write_text(rendered, "utf-8")
        print(rendered, end="")
        return 0
    except (OSError, ValueError, RuntimeError, zipfile.BadZipFile) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
