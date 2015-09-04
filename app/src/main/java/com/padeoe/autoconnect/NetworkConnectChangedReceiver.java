package com.padeoe.autoconnect;

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
                boolean isConnected2 = (state == NetworkInfo.State.CONNECTED);
                if (isConnected2) {
                    Log.i("NetworkConnectChanged", "aaaa");
                    WifiManager mWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = mWifi.getConnectionInfo();
                    if(WiFiDetectService.targetSSID==null){
                        if(Build.VERSION.SDK_INT>=17&&wifiInfo.getSSID().startsWith("\"")&&wifiInfo.getSSID().endsWith("\"")){
                            sharedPreferences.edit().putString("target_SSID","\"NJU-WLAN\"");
                            WiFiDetectService.targetSSID="\"NJU-WLAN\"";
                        }
                        else{
                            sharedPreferences.edit().putString("target_SSID","NJU-WLAN");
                            WiFiDetectService.targetSSID="NJU-WLAN";
                        }
                    }

                    if (wifiInfo.getSSID().equals(WiFiDetectService.targetSSID) ||wifiInfo.getSSID().equals("\"NJU-FAST\"") || wifiInfo.getSSID().equals("NJU-FAST")) {
                        Log.i("NetworkConnectChanged", "bbbb");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        String PostData = sharedPreferences.getString("PostData", null);
                                        if (PostData != null) {
                                            for (int i = 0; i < 5; i++) {
                                                if (Authenticate.connectAndPost(PostData,App.LOGINURL) != null) {
                                                    if(WiFiDetectService.allowStatistics){
                                                        AVAnalytics.onEvent(context, "后台自动登陆NJU-WLAN成功");
                                                    }
                                                    break;
                                                } else
                                                    Thread.sleep(100);
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
