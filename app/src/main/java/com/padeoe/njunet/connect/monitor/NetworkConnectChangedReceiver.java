package com.padeoe.njunet.connect.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.padeoe.nicservice.njuwlan.service.LoginService;
import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.controller.ConnectManager;
import com.padeoe.njunet.connect.controller.ConnectService;
import com.padeoe.njunet.connect.uihandler.ConnectResultHandle;
import com.padeoe.njunet.connect.uihandler.ErrorHandle;
import com.padeoe.njunet.connect.uihandler.WifiAvailableHandle;
import com.padeoe.njunet.connect.uihandler.WifiLostHandle;
import com.padeoe.njunet.util.MyObservable;
import com.padeoe.njunet.util.MyObserver;

/**
 * Created by padeoe on 4/20/15.
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver implements MyObserver<ConnectResultHandle> {
    public void onReceive(final Context context, Intent intent) {
        NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (networkInfo != null) {
            NetworkInfo.DetailedState state = networkInfo.getDetailedState();
            if (state == NetworkInfo.DetailedState.DISCONNECTED && ConnectManager.getStatus() != ConnectManager.Status.WIFI_LOST) {
                ConnectManager.stopAllConnect();
                ConnectManager.setStatus(ConnectManager.Status.WIFI_LOST);
                StatusNotificationManager.showStatus(App.getAppContext().getResources().getString(R.string.no_wifi));

                //发送离线广播，可能别处的界面需要更新信息
                Intent wifiLostIntent = new Intent(ConnectManager.WIFI_LOST_ACTION);
                wifiLostIntent.putExtra("WIFI_LOST", new WifiLostHandle());
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
                broadcastManager.sendBroadcast(wifiLostIntent);
            } else {
                if (state == NetworkInfo.DetailedState.CONNECTED && ConnectManager.getStatus() == ConnectManager.Status.WIFI_LOST) {
                    ConnectManager.setStatus(ConnectManager.Status.DETECTING);
                    StatusNotificationManager.showStatus();
                    //绑定网络，开始监测portal的连通性
                    ConnectManager.bindNetWork();
                    ConnectManager connectManager = new ConnectManager();
                    connectManager.addObserver(this);
                    Log.d("登陆请求", "收到wifi连接广播");
                    connectManager.backgrConnect();

                    //发送广播，可能别处的界面需要更新信息
                    WifiManager wifiManager = (WifiManager) App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    Intent wifiConnectedIntent = new Intent(ConnectManager.WIFI_AVAILABLE_ACTION);
                    wifiConnectedIntent.putExtra("NETINFO", new WifiAvailableHandle(wifiManager.getConnectionInfo().getSSID()));
                    LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
                    broadcastManager.sendBroadcast(wifiConnectedIntent);
                }
            }
        }
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
