var exec = require('cordova/exec');

module.exports = {
    toggleDNDMode: function(successCallback, errorCallback){
        exec(successCallback, errorCallback, 'PluginDoNotDisturbMode', 'toggleDNDMode', []);
    },
    checkDNDMode: function(successCallback, errorCallback){
        exec(successCallback, errorCallback, 'PluginDoNotDisturbMode', 'checkDNDMode', []);
    },
    enableDNDMode: function(successCallback, errorCallback){
        exec(successCallback, errorCallback, 'PluginDoNotDisturbMode', 'enableDNDMode', []);
    },
    disableDNDMode: function(successCallback, errorCallback){
        exec(successCallback, errorCallback, 'PluginDoNotDisturbMode', 'disableDNDMode', []);
    }
}