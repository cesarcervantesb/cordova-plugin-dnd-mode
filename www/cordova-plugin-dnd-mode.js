var exec = require('cordova/exec');

module.exports = {
    toggleDNDMode: function(successCallback, errorCallback){
        exec(successCallback, errorCallback, 'PluginDoNotDisturbMode', 'toggleDNDMode', []);
    },
    checkDNDMode: function(successCallback){
        exec(successCallback, null, 'PluginDoNotDisturbMode', 'checkDNDMode', []);
    },
    requestPermission: function(){
        exec(null, null, 'PluginDoNotDisturbMode', 'requestPermission', []);
    }
}