package com.padeoe.njunet.connect.monitor;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.controller.ConnectManager;
import com.padeoe.njunet.connect.uihandler.ConnectResultHandle;
import com.padeoe.njunet.connect.uihandler.WifiAvailableHandle;
import com.padeoe.njunet.connect.uihandler.WifiLostHandle;
import com.padeoe.njunet.util.MyObservable;
import com.padeoe.njunet.util.MyObserver;

/**
 * Created by padeoe on 2016/5/11.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class WifiNetWorkCallBack extends ConnectivityManager.NetworkCallback implements MyObserver<ConnectResultHandle> {
    public WifiNetWorkCallBack() {
        super();
    }

    @Override
    public void onAvailable(final Network network) {
        super.onAvailable(network);
        ConnectManager.setStatus(ConnectManager.Status.DETECTING);
        StatusNotificationManager.showStatus();
        //绑定网络，开始监测portal的连通性
        ConnectManager.bindNetWork(network);
        ConnectManager connectManager = new ConnectManager();
        connectManager.addObserver(this);
        connectManager.backgrConnect();

        //发送广播，可能别处的界面需要更新信息
        WifiManager wifiManager = (WifiManager) App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Intent intent = new Intent(ConnectManager.WIFI_AVAILABLE_ACTION);
        intent.putExtra("NETINFO", new WifiAvailableHandle(wifiManager.getConnectionInfo().getSSID()));
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
        broadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        ConnectManager.stopAllConnect();
        ConnectManager.setStatus(ConnectManager.Status.WIFI_LOST);
        StatusNotificationManager.showStatus(App.getAppContext().getResources().getString(R.string.no_wifi));

        //发送离线广播，可能别处的界面需要更新信息
        Intent intent = new Intent(ConnectManager.WIFI_LOST_ACTION);
        intent.putExtra("WIFI_LOST", new WifiLostHandle());
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
        broadcastManager.sendBroadcast(intent);
    }

    @Override
    public void update(MyObservable myObservable, ConnectResultHandle data) {
        //发送广播，可能别处的界面需要更新信息
        Intent intent = new Intent(ConnectManager.BACKGROUND_LOGIN_ACTION);
        intent.putExtra("LOGIN_RESULT", data);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
        broadcastManager.sendBroadcast(intent);
    }
}

