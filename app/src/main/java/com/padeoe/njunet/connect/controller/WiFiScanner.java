package com.padeoe.njunet.connect.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v4.content.LocalBroadcastManager;

import com.padeoe.njunet.App;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.monitor.ScanResultReceiver;
import com.padeoe.njunet.connect.uihandler.ConnectResultHandle;
import com.padeoe.njunet.util.MyObservable;
import com.padeoe.njunet.util.MyObserver;

/**
 * Created by padeoe on 2016/5/20.
 */
public class WiFiScanner implements MyObserver<ConnectResultHandle> {
    static BroadcastReceiver receiver;

    public void startScan() {
        //如果wifi未连接，才进行扫描
        if (ConnectManager.getStatus() == ConnectManager.Status.WIFI_LOST) {
            StatusNotificationManager.showStatus("正在扫描WLAN");
            //首先确保wifi开关已打开
            WifiManager wifiManager = (WifiManager) App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            App.getAppContext().registerReceiver(receiver = new ScanResultReceiver(), new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            if (wifiManager.isWifiEnabled() || wifiManager.setWifiEnabled(true)) {
                wifiManager.startScan();
            }
        }
        //如果已连接，不进行扫描，做网络检测刷新或登录。
        else {
            System.out.println("无需扫描wifi，即将检查网络");
            ConnectManager.setStatus(ConnectManager.Status.DETECTING);
            StatusNotificationManager.showStatus();
            ConnectManager connectManager = new ConnectManager();
            connectManager.addObserver(this);
            connectManager.login();
        }
    }


    @Override
    public void update(MyObservable myObservable, ConnectResultHandle data) {
        //发送广播，可能别处的界面需要更新信息
        System.out.println("发送登陆结果广播");
        Intent intent = new Intent(ConnectManager.BACKGROUND_LOGIN_ACTION);
        intent.putExtra("LOGIN_RESULT", data);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
        broadcastManager.sendBroadcast(intent);
    }
}
