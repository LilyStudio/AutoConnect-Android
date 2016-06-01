package com.padeoe.njunet.connect.monitor;

/**
 * Created by padeoe on 2016/6/1.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.padeoe.njunet.connect.controller.ConnectService;
import com.padeoe.njunet.util.PrefFileManager;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = PrefFileManager.getAccountPref();
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            if (sharedPreferences.getBoolean("auto_connect", true) &&
                    sharedPreferences.getString("username", null) != null &&
                    sharedPreferences.getString("password", null) != null) {
                context.startService(new Intent(context, ConnectService.class));
            }
        }
    }
}
