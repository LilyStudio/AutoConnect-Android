package com.padeoe.autoconnect.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;
import com.padeoe.autoconnect.service.WiFiDetectService;
import com.padeoe.autoconnect.util.NetworkUtils;
import com.padeoe.nicservice.njuwlan.ConnectPNJU;

/**
 * Created by padeoe on 4/20/15.
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    public void onReceive(final Context context, Intent intent) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("DataFile", 0);
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
            if (networkInfo != null) {
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = (state == NetworkInfo.State.CONNECTED);
                if (isConnected) {
                    WifiManager mWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = mWifi.getConnectionInfo();
                    if (WiFiDetectService.targetSSID == null) {
                        if (Build.VERSION.SDK_INT >= 17 && wifiInfo.getSSID().startsWith("\"") && wifiInfo.getSSID().endsWith("\"")) {
                            sharedPreferences.edit().putString("target_SSID", "\"NJU-WLAN\"");
                            WiFiDetectService.targetSSID = "\"NJU-WLAN\"";
                            sharedPreferences.edit().apply();
                        } else {
                            sharedPreferences.edit().putString("target_SSID", "NJU-WLAN");
                            WiFiDetectService.targetSSID = "NJU-WLAN";
                            sharedPreferences.edit().apply();
                        }
                    }
                    if (wifiInfo.getSSID().equals(WiFiDetectService.targetSSID) || wifiInfo.getSSID().equals("\"NJU-FAST\"") || wifiInfo.getSSID().equals("NJU-FAST")) {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    if (WiFiDetectService.username != null & WiFiDetectService.password != null) {
                                        for (int i = 0; i < 5; i++) {
                                            if (NetworkUtils.isLoginSuccess(ConnectPNJU.connect(WiFiDetectService.username, WiFiDetectService.password, 200))) {
                                                if (WiFiDetectService.allowStatistics) {
                                                    AVAnalytics.onEvent(context, "后台自动登陆NJU-WLAN成功");
                                                }
                                                break;
                                            } else {
                                                AVAnalytics.onEvent(context, "后台自动登陆NJU-WLAN失败");
                                                ;
                                                Thread.sleep(200);
                                            }
                                        }
                                    } else
                                        Log.i("Error", "未设置用户名密码");
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        }.start();
                    } else {
                        Log.i("RESULT", "SSID不是目标，SSID是"+wifiInfo.getSSID()+"目标是"+WiFiDetectService.targetSSID);
                    }
                }
            }
        }
    }
}
