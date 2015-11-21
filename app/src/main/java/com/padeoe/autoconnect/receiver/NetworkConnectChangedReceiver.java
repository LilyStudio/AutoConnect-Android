package com.padeoe.autoconnect.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;
import com.padeoe.autoconnect.service.ConnectService;
import com.padeoe.nicservice.njuwlan.service.LoginService;

/**
 * Created by padeoe on 4/20/15.
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    int i = 0;

    public void onReceive(final Context context, Intent intent) {
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
                    Log.d("wifiInfo:", wifiInfo.getSSID());
                    if (wifiInfo.getSSID().equals("\"NJU-FAST\"") || wifiInfo.getSSID().equals("\"NJU-WLAN\"")) {
                        Log.i("后台登陆", "是目标ssid");
                        if (i == 1) {
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        if (ConnectService.getUsername() != null & ConnectService.getPassword() != null) {
                                            Thread.sleep(200);
                                            for (int i = 0; i < 5; i++) {
                                                Log.i("后台登陆","第"+i+"次尝试");
                                                if (LoginService.isLoginSuccess(LoginService.getInstance().connect(ConnectService.getUsername(), ConnectService.getPassword()))) {
                                                    Log.i("后台登陆","后台登陆成功");
                                                    if (ConnectService.isAllowStatistics()) {
                                                        AVAnalytics.onEvent(context, "后台自动登陆NJU-WLAN成功");
                                                    }
                                                    break;
                                                } else {
                                                    Log.e("后台登陆","后台登陆失败");
                                                    Thread.sleep(200);
                                                }
                                            }
                                        } else
                                            Log.e("Error", "未设置用户名密码");
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }.start();
                            i = 0;
                        } else
                            i++;
                    } else {
                        Log.i("RESULT", "SSID不是目标，SSID是"+wifiInfo.getSSID());
                    }
                }
            }
        }
    }
}
