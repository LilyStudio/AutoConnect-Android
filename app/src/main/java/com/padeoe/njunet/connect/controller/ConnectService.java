package com.padeoe.njunet.connect.controller;

/**
 * Created by padeoe on 2016/4/21.
 */

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.padeoe.njunet.App;
import com.padeoe.njunet.connect.monitor.NetworkConnectChangedReceiver;
import com.padeoe.njunet.connect.monitor.WifiNetWorkCallBack;
import com.padeoe.njunet.util.PrefFileManager;

/**
 * 用于负责后台网络连接的服务，存储了网络连接相关的数据，包括用户名密码，目标SSID
 * 对于不同API等级，使用了不同的接口实现了网络状态监测
 */
public class ConnectService extends Service {
    private static String username;
    private static String password;
    private static String targetSSID;
    Object wifiNetWorkCallBack;
    public static final String STOP_SERVICE_ACTION = "com.padeoe.njunet.STOP_SERVICE_ACTION";
    public static final String SCAN_AND_CONNECT_ACTION = "com.padeoe.njunet.SCAN_AND_CONNECT_ACTION";

    NetworkConnectChangedReceiver networkConnectChangedReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the com.padeoe.service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && (intent.getAction()) != null && intent.getAction().equals(SCAN_AND_CONNECT_ACTION)) {
            Log.d("wifi扫描", "即将开启");
            WiFiScanner wiFiScanner = new WiFiScanner();
            wiFiScanner.startScan();
        }
        if (intent != null && (intent.getAction()) != null && intent.getAction().equals(STOP_SERVICE_ACTION)) {
            this.stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        Log.d("ConnectService", "即将开启连接service");
        super.onCreate();
        SharedPreferences sharedPreferences = PrefFileManager.getAccountPref();
        username = sharedPreferences.getString("username", null);
        password = sharedPreferences.getString("password", null);
        targetSSID = sharedPreferences.getString("target_SSID", null);
        startMonitor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        endMonitor();
    }

    /**
     * 获得校园网连接的用户名，若当前为空，则会从配置文件中查找
     *
     * @return
     */
    public static String getUsername() {
        return username != null ? username : (username = PrefFileManager.getAccountPref().getString("username", null));
    }

    /**
     * 设置校园网连接的用户名，并写入配置文件
     *
     * @param username
     */
    public static void setUsername(String username) {
        SharedPreferences.Editor editor = PrefFileManager.getAccountPref().edit();
        ConnectService.username = username;
        editor.putString("username", username).apply();
    }

    /**
     * 获得校园网连接的密码，若当前为空，则会从配置文件中查找
     *
     * @return
     */
    public static String getPassword() {
        return password != null ? password : (password = PrefFileManager.getAccountPref().getString("password", null));
    }

    /**
     * 设置校园网连接的密码，并写入配置文件
     *
     * @param password
     */
    public static void setPassword(String password) {
        SharedPreferences.Editor editor = PrefFileManager.getAccountPref().edit();
        ConnectService.password = password;
        editor.putString("password", password).apply();
    }

    /**
     * 获得识别校园网连接使用的SSID名称，若当前为空，则会从配置文件中查找
     */
    public static String getTargetSSID() {
        return targetSSID != null ? targetSSID : (targetSSID = PrefFileManager.getAccountPref().getString("target_SSID", null));
    }

    /**
     * 设置校园网连接的SSID，并写入配置文件
     *
     * @param targetSSID
     */
    public static void setTargetSSID(String targetSSID) {
        SharedPreferences.Editor editor = PrefFileManager.getAccountPref().edit();
        ConnectService.targetSSID = targetSSID;
        editor.putString("target_SSID", targetSSID).apply();
    }

    public static boolean isTargetWifi(String SSID) {
        //如果目标wifi名称不存在就首先确定目标wifi名称
        if (getTargetSSID() == null)
            ConnectService.setTargetSSID((Build.VERSION.SDK_INT >= 17 && SSID.startsWith("\"") && SSID.endsWith("\"")) ? "\"NJU-WLAN\"" : "NJU-WLAN");
        return SSID.equals(getTargetSSID()) || SSID.equals("NJU-FAST") || SSID.equals("\"NJU-FAST\"");
    }

    private void startMonitor() {
        //共有两个功能
        //1.后台连接p.nju.edu.cn
        //2.检测portal网络并提示登录（仅Android N及以上具有该功能）

        //API<21  Android 4.0-4.4使用动态注册广播接收器的形式实现功能
        //API=21 Android 5.0使用public void requestNetwork (NetworkRequest request, ConnectivityManager.NetworkCallback networkCallback)实现功能1
        //API=22 Android 6.0使用public void requestNetwork (NetworkRequest request, PendingIntent operation)实现功能1
        //API=23 Android N使用 public void registerNetworkCallback (NetworkRequest request, PendingIntent operation)实现功能1和功能2

        if (Build.VERSION.SDK_INT <= 20) {
            registerWifiReceiver();
        }
        //开启后台自动连接项目
        if (Build.VERSION.SDK_INT >= 21) {
            registerNetworkCallback();
        }

        /*
        if (Build.VERSION.SDK_INT == 22) {
            Log.i("API22", "API22网络监测");
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
            builder.addCapability(NetworkCapabilities.NET_CAPABILITY_CAPTIVE_PORTAL);
            NetworkRequest wifiNetworkRequest = builder.build();
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(this, 233, new Intent(getApplicationContext(), ConnectService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            connectivityManager.requestNetwork(wifiNetworkRequest, pendingIntent);
        }


        //API23测试可用
        if (Build.VERSION.SDK_INT >= 23) {
            Log.i("API23", "API23网络监测");
            NetworkRequest.Builder newbuilder = new NetworkRequest.Builder();
            newbuilder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
            newbuilder.addCapability(NetworkCapabilities.NET_CAPABILITY_CAPTIVE_PORTAL);
            NetworkRequest portalNetworkRequest = newbuilder.build();
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(this, 233, new Intent(getApplicationContext(), ConnectService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            connectivityManager.registerNetworkCallback(portalNetworkRequest, pendingIntent);
        }*/
    }

    private void endMonitor() {
        releaseWifiChangedReceiver();
        releaseNetworkCallback();
    }

    private void registerWifiReceiver() {
        Log.i("API14", "API14网络监测");
        networkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        //    filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(networkConnectChangedReceiver, filter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void registerNetworkCallback() {
        Log.i("API21", "API21网络监测");
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        NetworkRequest wifiNetworkRequest = builder.build();
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiNetWorkCallBack = new WifiNetWorkCallBack();

        connectivityManager.requestNetwork(wifiNetworkRequest, (WifiNetWorkCallBack) wifiNetWorkCallBack);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void releaseNetworkCallback() {
        if (wifiNetWorkCallBack != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.unregisterNetworkCallback((WifiNetWorkCallBack) wifiNetWorkCallBack);
        }
    }

    private void releaseWifiChangedReceiver() {
        if (networkConnectChangedReceiver != null) {
            unregisterReceiver(networkConnectChangedReceiver);
        }
    }

    private static boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) App.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRunning() {
        return isServiceRunning(ConnectService.class);
    }
}


