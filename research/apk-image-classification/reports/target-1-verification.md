# Target 1 verification record

Generated: 2026-08-04 (Asia/Singapore)

## Baseline input verification

Command:

```powershell
Get-FileHash -Algorithm SHA256 'D:\magia\MyProducts\MagiaRe\Magia_CN_Project\dist\MagiaCN-r128-downloader-fix1.apk'
```

Literal result:

```text
BD3AAE6D80F87044A9C6780AC0226746AA53D568CF81A6203C7F08D60C7B67F5
exit_status=0
```

## Catalog verification

Command:

```powershell
python research\apk-image-classification\scripts\verify_apk_catalog.py research\apk-image-classification\manifests --audit-root 'D:\magia\MyProducts\MagiaRe\Magia_CN_Project\research\apk-image-audit-2026-08-02'
```

Literal result:

```json
{
  "physical": {
    "rows": 424,
    "missing": 0,
    "hash_mismatch": 0,
    "size_mismatch": 0,
    "decode_failures": 0
  },
  "logical_frames": {
    "rows": 1008,
    "missing": 0,
    "hash_mismatch": 0,
    "size_mismatch": 0,
    "decode_failures": 0
  },
  "csv_json_count_equal": true,
  "summary_count_equal": true,
  "layers_separate": true,
  "ok": true
}
```

```text
exit_status=0
```

The verifier reopened all 1,432 exported files and checked their paths,
encoded SHA-256 values, dimensions and decodability.
