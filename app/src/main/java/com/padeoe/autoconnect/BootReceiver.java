package com.padeoe.autoconnect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("DataFile", 0x00000);
            boolean isBanned = sharedPreferences.getBoolean("isBanned", false);
            String PostData = sharedPreferences.getString("PostData", null);
            if (!isBanned && PostData != null && PostData.length() > 0) {
                context.startService(new Intent(context, WiFiDetectService.class));
            }
        }
    }
}
