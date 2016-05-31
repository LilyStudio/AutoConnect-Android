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
public class WifiNetWorkCallBack extends ConnectivityManager.NetworkCallback implements MyObserver<ConnectResultHandle>{
    public WifiNetWorkCallBack() {
        super();
    }

    @Override
    public void onAvailable(final Network network) {
        super.onAvailable(network);
        ConnectManager.setStatus(ConnectManager.Status.DETECTING);
        StatusNotificationManager.showStatus();
        System.out.println("即将检查网络");
        //绑定网络，开始监测portal的连通性
        ConnectManager.bindNetWork(network);
        ConnectManager connectManager=new ConnectManager();
        connectManager.addObserver(this);
        Log.d("登陆请求","收到wifi连接广播");
        connectManager.backgrConnect();

        //发送广播，可能别处的界面需要更新信息
        WifiManager wifiManager = (WifiManager) App.getAppContext().getSystemService(Context.WIFI_SERVICE);
        Intent intent = new Intent(ConnectManager.WIFI_AVAILABLE_ACTION);
        intent.putExtra("NETINFO", new WifiAvailableHandle(wifiManager.getConnectionInfo().getSSID()));
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
        broadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        Log.d("onCapabilitiesChanged", "onCapabilitiesChanged");
    }

    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
        Log.d("onLinkPropertiesChanged", "onLinkPropertiesChanged");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        ConnectManager.stopAllConnect();
        ConnectManager.setStatus(ConnectManager.Status.WIFI_LOST);
        StatusNotificationManager.showStatus("未连接网络233");
        Log.d("onLost", "onLost");
        //发送离线广播，可能别处的界面需要更新信息
        Intent intent = new Intent(ConnectManager.WIFI_LOST_ACTION);
        intent.putExtra("WIFI_LOST", new WifiLostHandle());
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
        broadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
        Log.d("onLosing", "onLosing");
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

