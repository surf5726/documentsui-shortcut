# Release Checklist

## GitHub

1. Check the app builds:

   ```powershell
   .\gradlew.bat assembleRelease
   ```

2. Commit source files only. Do not commit:

   - `local.properties`
   - `.gradle/`
   - `build/`
   - `app/build/`
   - `keystore/`
   - generated APK or `.idsig` files

3. Tag the release:

   ```powershell
   git tag v1.0.0
   git push origin main --tags
   ```

4. Create a GitHub Release from `v1.0.0`.

## F-Droid Notes

F-Droid builds APKs from source. For the official repository, submit metadata to
`fdroiddata` or open a Request For Packaging. Use the Git tag as the build
reference.

Useful package information:

- Application ID: `com.documentsui.shortcut`
- Version name: `1.0`
- Version code: `1`
- License: `Apache-2.0`
- Build command: `./gradlew assembleRelease`
