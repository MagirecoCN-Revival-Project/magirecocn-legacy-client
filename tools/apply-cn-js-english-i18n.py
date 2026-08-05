#!/usr/bin/env python3
"""Compatibility entry point for the strict idempotent runtime UI patcher."""
from pathlib import Path
import runpy

TARGET = Path(__file__).with_name("apply-cn-js-english-i18n-idempotent.py")

if __name__ == "__main__":
    runpy.run_path(str(TARGET), run_name="__main__")
