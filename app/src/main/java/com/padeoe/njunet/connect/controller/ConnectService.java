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
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.padeoe.njunet.App;
import com.padeoe.njunet.connect.monitor.NetworkConnectChangedReceiver;
import com.padeoe.njunet.connect.monitor.WifiNetWorkCallBack;
import com.padeoe.njunet.util.PrefFileManager;

/**
 * 用于负责后台网络连接的服务，存储了网络连接相关的数据，包括用户名密码
 * 对于不同API等级，使用了不同的接口实现了网络状态监测
 */
public class ConnectService extends Service {
    private static String username;
    private static String password;
    private static Boolean checkBeforeLogin = null;
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
        if (PreferenceManager.getDefaultSharedPreferences(App.getAppContext()).getBoolean("auto_connect", true)) {
            startMonitor();
        }

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
        PrefFileManager.getAccountPref().edit().putString("username", ConnectService.username = username).apply();
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
        PrefFileManager.getAccountPref().edit().putString("password", ConnectService.password = password).apply();
    }


    private void startMonitor() {
        if (/*Build.VERSION.SDK_INT <= 20*/true) {
            registerWifiReceiver();
        }
        /**
         * 本打算对API21采用注册网络回调{@link android.net.ConnectivityManager.NetworkCallback#registerNetworkCallback(NetworkRequest, ConnectivityManager.NetworkCallback)}的方法进行监听，
         * 因为这样能方便的检查到网络的详细变化包括{@link android.net.ConnectivityManager.NetworkCallback#onCapabilitiesChanged(Network, NetworkCapabilities)}等。然而首次打开或者后台被杀时，service重启再次打开回调时
         * 会导致{@link android.net.ConnectivityManager.NetworkCallback#onAvailable(Network)}方法可能被调用(wifi连接的情况下是一定会)(该方法中会调用登陆函数)，所以用户会发现，本来点击下线已退出，然而杀掉后台后，又重新登录了。
         *
         */
        if (/*Build.VERSION.SDK_INT >= 21*/false) {
            registerNetworkCallback();
        }
    }

    private void endMonitor() {
        releaseWifiChangedReceiver();
        releaseNetworkCallback();
    }

    private void registerWifiReceiver() {
        networkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(networkConnectChangedReceiver, filter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void registerNetworkCallback() {
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
        ActivityManager manager = (ActivityManager) App.getAppContext().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
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

    public static boolean isCheckBeforeLogin() {
        return checkBeforeLogin != null ? checkBeforeLogin : (checkBeforeLogin = PreferenceManager.getDefaultSharedPreferences(App.getAppContext()).getBoolean("checkBeforeLogin", true));
    }

    public static void setCheckBeforeLogin(boolean checkBeforeLogin) {
        PrefFileManager.getAccountPref().edit().putBoolean("checkBeforeLogin", ConnectService.checkBeforeLogin = checkBeforeLogin).apply();
    }
}


