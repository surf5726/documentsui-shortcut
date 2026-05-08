# DocumentsUI Shortcut

[![License: Apache-2.0](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](LICENSE)

DocumentsUI Shortcut is a tiny Android launcher that opens the system Files / DocumentsUI app.

It is only a shortcut. It does not request storage permission, read files, access the network, run a background service, or implement a file manager of its own.

## Features

- Opens Android's built-in Files / DocumentsUI app.
- Prefers DocumentsUI browser mode instead of file picker mode.
- No network permission, storage permission, or background service.
- Application ID: `com.documentsui.shortcut`

## Android/data and Android/obb

Android 11 and later restrict ordinary apps from accessing `Android/data` and `Android/obb` through the system file picker. This app only launches the system DocumentsUI app; whether those directories are visible depends on Android and the device vendor's DocumentsUI implementation.

## Build

```powershell
.\gradlew.bat assembleRelease
```

The unsigned release APK is generated at:

```text
app\build\outputs\apk\release\app-release-unsigned.apk
```

Debug build:

```powershell
.\gradlew.bat assembleDebug
```

## Release

Use your own signing key for GitHub releases or F-Droid builds. Do not commit `keystore/`, `local.properties`, `build/`, generated APKs, or signing artifacts.

```powershell
git tag v1.0.0
git push origin main --tags
```

## Icon

The icon is derived from the AOSP DocumentsUI `android-16.0.0_r4` `ic_app_icon` resources, with an added shortcut arrow badge. See [NOTICE](NOTICE).

## License

This project is licensed under the [Apache License 2.0](LICENSE).
