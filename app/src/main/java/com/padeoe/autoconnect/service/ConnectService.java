package com.padeoe.autoconnect.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.IBinder;

import com.padeoe.autoconnect.activity.App;
import com.padeoe.autoconnect.receiver.NetworkConnectChangedReceiver;

public class ConnectService extends Service {
    private static boolean allowStatistics=false;
    private static String username;
    private static String password;
    private static String targetSSID;
    static final SharedPreferences sharedPreferences = App.context.getSharedPreferences("DataFile", 0);
    static SharedPreferences.Editor editor = sharedPreferences.edit();
    NetworkConnectChangedReceiver networkConnectChangedReceiver=new NetworkConnectChangedReceiver();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        System.out.println("onCreate"+"正在读文件");
        // TODO Auto-generated method stub
        super.onCreate();
        username = sharedPreferences.getString("username", null);
        password = sharedPreferences.getString("password", null);
        allowStatistics = sharedPreferences.getBoolean("allow_statistics", false);
        targetSSID=sharedPreferences.getString("target_SSID", null);

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(networkConnectChangedReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroyed");
        unregisterReceiver(networkConnectChangedReceiver);
    }

    public static boolean isAllowStatistics() {
        return allowStatistics;
    }

    public static void setAllowStatistics(boolean allowStatistics) {
        ConnectService.allowStatistics = allowStatistics;
        editor.putBoolean("allow_statistics",allowStatistics);
        editor.apply();
    }

    public static String getUsername() {
        if(username==null){
            System.out.println("正在读文件");
            username=sharedPreferences.getString("username",null);
        }
        return username;
    }

    public static void setUsername(String username) {
        ConnectService.username = username;
        editor.putString("username",username);
        editor.apply();
    }

    public static String getPassword() {
        if(password==null){
            System.out.println("正在读文件");
            password=sharedPreferences.getString("password",null);
        }
        return password;
    }

    public static void setPassword(String password) {
        ConnectService.password = password;
        editor.putString("password", password);
        editor.apply();
    }

    public static String getTargetSSID() {
        if(targetSSID==null){
            System.out.println("正在读文件");
            targetSSID=sharedPreferences.getString("target_SSID",null);
        }
        return targetSSID;
    }

    public static void setTargetSSID(String targetSSID) {
        ConnectService.targetSSID = targetSSID;
        editor.putString("target_SSID", targetSSID);
        editor.apply();
    }
}

