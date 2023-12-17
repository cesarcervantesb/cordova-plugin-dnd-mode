# cordova-plugin-dnd-mode

[![npm](https://img.shields.io/github/package-json/v/cesarcervantesb/cordova-plugin-dnd-mode
)](https://github.com/cesarcervantesb/cordova-plugin-dnd-mode/)
![Platform](https://img.shields.io/badge/platforms-Android-violet.svg)
[![donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.me/cesarcervantesb)

This plugin allows check status and enable/disable the Do Not Disturb Mode.

## Installation

Install plugin from npm:

```
npm i cordova-plugin-dnd-mode
```

Or install the latest master version from GitHub:

```
cordova plugin add https://github.com/cesarcervantesb/cordova-plugin-dnd-mode
```

## Supported Platforms

- Android

## Usage

The plugin creates the object cordova.plugins.dndMode and is accessible after the deviceready event has been fired.

```js
document.addEventListener('deviceready', function () {
    // cordova.plugins.dndMode is now available
}, false);
```

## Available methods

- `requestPermission` - Request permission 'ACCESS_NOTIFICATION_POLICY' for Android platform.
```js
dndMode.requestPermission()
```

- `toggleDNDMode` - toggle Do Not Disturb Mode. (enable | disable)
```js
dndMode.toggleDNDMode()
```

- `checkDNDMode` - Get all info related to Do Not Disturb Mode.
```js
dndMode.checkDNDMode()
```
return a JSONObject containing the Do Not Disturb Mode information:
  - `enabled` - Boolean
  - `permissionGranted` - Boolean

