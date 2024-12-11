package com.ccervantesb.dnd;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class PluginDoNotDisturbMode extends CordovaPlugin {

    private NotificationManager notificationManager;

    private static final int REQUEST_TOGGLE_DND_MODE = 1;
    private static final int REQUEST_CHECK_DND_MODE = 2;
    private static final int REQUEST_ENABLE_DND_MODE = 4;
    private static final int REQUEST_DISABLE_DND_MODE = 5;
    private static final String TOGGLE_DND_ACTION = "toggleDNDMode";
    private static final String CHECK_DND_ACTION = "checkDNDMode";
    private static final String ENABLE_DND_ACTION = "enableDNDMode";
    private static final String DISABLE_DND_ACTION = "disableDNDMode";

    private CallbackContext callbackContext;
    private boolean isActive;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        notificationManager = (NotificationManager) cordova.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        isActive = dndModeEnabled();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        boolean valid = true;
        switch (action) {
            case TOGGLE_DND_ACTION:
                this.toggleDNDMode();
                break;
            case CHECK_DND_ACTION:
                this.checkDNDMode();
                break;
            case ENABLE_DND_ACTION:
                this.enableDNDMode();
                break;
            case DISABLE_DND_ACTION:
                this.disableDNDMode();
                break;
            default:
                valid = false;
                break;
        }
        return valid;
    }

    /**
     * Disable Do Not Disturb Mode
     * */
    private void disableDNDMode() {
        if(checkPermission()) {
            int interruptionFilter = NotificationManager.INTERRUPTION_FILTER_ALL;
            notificationManager.setInterruptionFilter(interruptionFilter);
            setActive(false);
            checkDNDMode();
        }
        else {
            // Request permission
            this.requestPermission(REQUEST_DISABLE_DND_MODE);
        }
    }

    /**
     * Enable Do Not Disturb Mode
     * */
    private void enableDNDMode() {
        if(checkPermission()) {
            int interruptionFilter = NotificationManager.INTERRUPTION_FILTER_NONE;
            notificationManager.setInterruptionFilter(interruptionFilter);
            setActive(true);
            checkDNDMode();
        }
        else {
            // Request permission
            this.requestPermission(REQUEST_ENABLE_DND_MODE);
        }
    }

    /**
     * toggle Do Not Disturb Mode. (enable | disable)
     */
    private void toggleDNDMode(){
        if(checkPermission()) {
            if(dndModeEnabled()){
                disableDNDMode();
            }
            else {
                enableDNDMode();
            }
        }
        else{
            // Request permission
            this.requestPermission(REQUEST_TOGGLE_DND_MODE);
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
            jsonObject.put("isActive", this.getActive());
            jsonObject.put("permissionGranted", notificationManager.isNotificationPolicyAccessGranted());
        } catch (JSONException e) {
            Log.e("PluginDNDMode", e.getMessage(), e);
            this.handleError("Error creating JSON Object", "Error: " + e.getMessage());
        }
        return jsonObject;
    }


    /**
     * Get all info related to Do Not Disturb Mode
     */
    private void checkDNDMode(){
        // Check permission
        if(checkPermission()) {
            JSONObject info = getInfo();
            callbackContext.success(info);
        }
        else {
            // Request permission
            this.requestPermission(REQUEST_CHECK_DND_MODE);
        }
    }

    /**
     * Check if Do Not Disturb Mode is enable.
     * @return a boolean (true | false)
     */
    private boolean dndModeEnabled(){
        return notificationManager.getCurrentInterruptionFilter() == NotificationManager.INTERRUPTION_FILTER_NONE;
    }

    /**
     * Request permission 'ACCESS_NOTIFICATION_POLICY'
     */
    private void requestPermission(int requestCode){
        // Check if the notification policy access has been granted for the app.
        if (!checkPermission()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            cordova.startActivityForResult(this, intent, requestCode);
        }
        else {
            callbackContext.success(getInfo());
        }
    }

    /**
     * Check if permission 'ACCESS_NOTIFICATION_POLICY' is granted.
     * @return a boolean (true | false)
     */
    private boolean checkPermission() {
        return notificationManager.isNotificationPolicyAccessGranted();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode,intent);
        if (checkPermission()) {
            switch (requestCode) {
                case REQUEST_TOGGLE_DND_MODE:
                    this.toggleDNDMode();
                    break;
                case REQUEST_CHECK_DND_MODE:
                    this.checkDNDMode();
                    break;
                case REQUEST_ENABLE_DND_MODE:
                    this.enableDNDMode();
                    break;
                case REQUEST_DISABLE_DND_MODE:
                    this.disableDNDMode();
                    break;
            }
        }
        else {
            this.handleError("Permission denied", "Needs permission 'ACCESS_NOTIFICATION_POLICY' to be granted.");
        }
    }

    private void setActive(boolean isActive) { this.isActive = isActive; }
    public boolean getActive() { return this.isActive; }

    private void handleError(String title, String message) {
        JSONObject error = new JSONObject();
        try {
            error.put("title", title);
            error.put("message", message);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, error);
            this.callbackContext.sendPluginResult(result);
        } catch (JSONException e) {
            Log.e("PluginMockLocation", "Error creating JSON Object", e);
        }
    }
}