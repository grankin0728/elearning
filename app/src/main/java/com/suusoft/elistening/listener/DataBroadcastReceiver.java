package com.suusoft.elistening.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.suusoft.elistening.service.DownloadService;


/**
 * Created by ASUS on 4/20/2017.
 */

public class DataBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadService.dl_continue = false;
    }
}
