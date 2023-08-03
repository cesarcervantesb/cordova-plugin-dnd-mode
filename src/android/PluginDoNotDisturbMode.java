package com.ccervantesb.cordova.dnd.mode;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class PluginDoNotDisturbMode extends CordovaPlugin {

    protected CordovaInterface cordovaInterface;
    protected CordovaWebView cordovaWebView;
    private NotificationManager notificationManager;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        this.cordovaInterface = cordova;
        this.cordovaWebView = webView;
        notificationManager = (NotificationManager) cordovaInterface.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        //requestPermission();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("toggleDNDMode")){
            this.toggleDNDMode(callbackContext);
            return true;
        }
        else if(action.equals("checkDNDMode")){
            this.checkDNDMode(callbackContext);
            return true;
        }
        else if(action.equals("requestPermission")){
            this.requestPermission();
            callbackContext.success();
            return true;
        }
        return false;
    }


    /**
     * toggle Do Not Disturb Mode. (enable | disable)
     */
    private void toggleDNDMode(CallbackContext callbackContext){
        if(notificationManager.isNotificationPolicyAccessGranted()) {
            int interruptionFilter = dndModeEnabled() ? NotificationManager.INTERRUPTION_FILTER_ALL : NotificationManager.INTERRUPTION_FILTER_NONE;
            notificationManager.setInterruptionFilter(interruptionFilter);
            // Callback success
            callbackContext.success(getInfo());
        }
        else{
            // Callback error
            callbackContext.error("Needs permission 'ACCESS_NOTIFICATION_POLICY' to be granted.");
        }
    }

    /**
     * Creates a JSONObject with the Do Not Disturb Mode information
     *
     * @return a JSONObject containing the Do Not Disturb Mode information
     */
    private JSONObject getInfo(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("enabled", dndModeEnabled());
            jsonObject.put("permissionGranted", notificationManager.isNotificationPolicyAccessGranted());

        } catch (JSONException e) {
            Log.e("PluginDNDMode", e.getMessage(), e);
        }
        return jsonObject;
    }


    /**
     * Get all info related to Do Not Disturb Mode
     */
    private void checkDNDMode(CallbackContext callbackContext){
        // Callback success
        callbackContext.success(getInfo());
    }

    /**
     * Check if permission 'ACCESS_NOTIFICATION_POLICY' is granted.
     * @return a boolean (true | false)
     */
    private boolean dndModeEnabled(){
        return notificationManager.getCurrentInterruptionFilter() == NotificationManager.INTERRUPTION_FILTER_NONE;
    }

    /**
     * Request permission 'ACCESS_NOTIFICATION_POLICY'
     */
    private void requestPermission(){
        // Check if the notification policy access has been granted for the app.
        if (!notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            cordovaInterface.getActivity().startActivity(intent);
        }
    }
}