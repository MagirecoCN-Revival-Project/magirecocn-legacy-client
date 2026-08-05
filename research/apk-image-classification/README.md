# APK image classification research

This directory keeps the reproducible image-audit tooling and Git-sized
evidence for `MagiaCN-r128-downloader-fix1.apk`.  Full exported raster payloads
are distributed as checksummed GitHub Release assets rather than ordinary Git
objects.

## Source fixture

- APK SHA-256: `BD3AAE6D80F87044A9C6780AC0226746AA53D568CF81A6203C7F08D60C7B67F5`
- Direct raster payloads: **347** (PNG 339, JPEG 8)
- plist-embedded physical payload instances: **77** (PNG 47, TIFF 30)
- Physical payload instances, total: **424** (**370** unique encoded SHA-256)
- Restored logical atlas frames: **1,008** (**967** unique encoded SHA-256)

Physical payloads and logical frames are separate layers.  A frame is a crop or
restored canvas derived from a physical atlas texture; it is not another APK
payload.

## Reproduce the APK catalog

```powershell
python scripts/extract_apk_images.py APK AUDIT_ROOT
python scripts/extract_plist_images.py APK AUDIT_ROOT
python scripts/build_apk_catalog.py AUDIT_ROOT --apk APK --output manifests
python scripts/verify_apk_catalog.py manifests --audit-root AUDIT_ROOT
```

The manifests include source location, exported path, dimensions, image format,
encoded SHA-256, normalized RGBA pixel SHA-256 and deterministic 64-bit pHash.
The verification command reopens every one of the 1,432 exported files and
checks its path, encoded hash, dimensions and decodability.

Subsequent reports in this directory add English UI review, legacy CN resource
discovery, replacement matching, visual comparisons and Release asset checks.
