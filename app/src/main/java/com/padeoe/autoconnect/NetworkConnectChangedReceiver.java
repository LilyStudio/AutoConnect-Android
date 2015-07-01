package com.padeoe.autoconnect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;

/**
 * Created by padeoe on 4/20/15.
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    int i=0;
    public void onReceive(final Context context, Intent intent) {
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
            if (networkInfo != null) {
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected2 = (state == NetworkInfo.State.CONNECTED);
                if (isConnected2) {
                    Log.i("NetworkConnectChanged", "网络连接已经建立");
                    WifiManager mWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = mWifi.getConnectionInfo();
                    Log.d("wifiInfo:", wifiInfo.getSSID());
                    if (wifiInfo.getSSID().equals("\"NJU-FAST\"") || wifiInfo.getSSID().equals("\"NJU-WLAN\"")) {
                        Log.i("SSID","发现时NJU-WLAN");
                        if (i == 1) {
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        if (WiFiDetectService.postData != null) {
                                            for (int i = 0; i < 5; i++) {
                                                if (Authenticate.connectAndPost(WiFiDetectService.postData,App.LOGINURL) != null) {
                                                    if(WiFiDetectService.allowStatisc){
                                                        AVAnalytics.onEvent(context, "后台自动登陆NJU-WLAN成功");
                                                    }
                                                    break;
                                                } else{
                                                    Thread.sleep(100);
                                                }
                                            }
                                        } else
                                            Log.i("Error", "未设置用户名密码");
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }.start();
                            i = 0;
                        } else
                            i++;
                    } else {
                        Log.i("RESULT", "SSID不是目标");
                    }
                }
            }
        }
    }
}
