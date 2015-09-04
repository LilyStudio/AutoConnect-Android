package com.padeoe.autoconnect;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;

public class WiFiDetectService extends Service {
    public static boolean allowStatistics;
    public static String postData;
    public static String targetSSID;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        final SharedPreferences sharedPreferences = App.context.getSharedPreferences("DataFile", 0);
        postData = sharedPreferences.getString("PostData", null);
        allowStatistics=sharedPreferences.getBoolean("allow_statistics", false);
        targetSSID=sharedPreferences.getString("target_SSID", null);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(new NetworkConnectChangedReceiver(), filter);
    }
}

