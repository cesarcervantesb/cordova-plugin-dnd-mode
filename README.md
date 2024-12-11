# cordova-plugin-dnd-mode

[![npm](https://img.shields.io/github/package-json/v/cesarcervantesb/cordova-plugin-dnd-mode
)](https://github.com/cesarcervantesb/cordova-plugin-dnd-mode/)
![Platform](https://img.shields.io/badge/platforms-Android-violet.svg)

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

- `toggleDNDMode` - toggle status of Do Not Disturb Mode. (enable | disable)

- `checkDNDMode` - Get status of Do Not disturb Mode.

- `enableDNDMode` - enable Do Not Disturb Mode.

- `disableDNDMode` - disable Do Not Disturb Mode.

Returns a JSONObject containing the Do Not Disturb Mode information:
  - `isActive` - Boolean
  - `permissionGranted` - Boolean

## Examples

- Check status of Do Not Disturb Mode.
```js
dndMode.checkDNDMode(function (dndMode) {
  // Success callback
  console.log("DND Mode isActive: " + dndMode.isActive);
  console.log("DND Mode permission granted: " + dndMode.permissionGranted);
}, function (error) {
  // Error callback
  console.log("Error: " + error.message);
});
```

- Enable Do Not Disturb Mode.
```js
dndMode.enableDNDMode(function (dndMode) {
  // Success callback
  // Do Not Disturb Mode is enabled
  console.log("DND Mode isActive: " + dndMode.isActive);
}, function (error) {
  // Error callback
  console.log("Error: " + error.message);
});
```

- Disable Do Not Disturb Mode.
```js
dndMode.disableDNDMode(function (dndMode) {
  // Success callback
  // Do Not Disturb Mode is disabled
  console.log("DND Mode isActive: " + dndMode.isActive);
}, function (error) {
  // Error callback
  console.log("Error: " + error.message);
});
```