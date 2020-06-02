/*
 * Name: $RCSfile: NetworkUtility.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 3:57:18 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.suusoft.elistening.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;

import com.suusoft.elistening.AppController;

/**
 * NetworkUtility checks available network
 *
 * @author Lemon
 */
public class NetworkUtility {

    /**
     * Check network connection
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) AppController.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null) {
            return false;
        }
        if (!i.isConnected()) {
            return false;
        }
        if (!i.isAvailable()) {
            return false;
        }
        return true;
    }

    // turn on use network for location
    public void turnNetWorkLocationOn(Activity activity) {
        String provider = Settings.Secure.getString(
                activity.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { // if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("1"));
            activity.sendBroadcast(poke);
        }
        // String provider = Settings.Secure.getString(
        // activity.getContentResolver(),
        // Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        // Log.e("provider", "ok " + provider);
        // final Intent poke = new Intent();
        // poke.setClassName("com.android.settings",
        // "com.android.settings.widget.SettingsAppWidgetProvider");
        // poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
        // poke.setData(Uri.parse("3"));
        // activity.sendBroadcast(poke);
        // Settings.Secure.setLocationProviderEnabled(
        // activity.getContentResolver(),
        // LocationManager.NETWORK_PROVIDER, true);
    }

    // turn on GPS
    // public void turnGPSOn(Activity activity) {
    //
    // String provider = Settings.Secure.getString(
    // activity.getContentResolver(),
    // Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
    // if (!provider.contains("gps")) { // if gps is disabled
    // final Intent poke = new Intent();
    // poke.setClassName("com.android.settings",
    // "com.android.settings.widget.SettingsAppWidgetProvider");
    // poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
    // poke.setData(Uri.parse("3"));
    // activity.sendBroadcast(poke);
    // }
    //
    // }
}
