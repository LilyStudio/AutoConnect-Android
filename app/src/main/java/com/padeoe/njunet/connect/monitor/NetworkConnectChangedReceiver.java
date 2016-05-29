package com.padeoe.njunet.connect.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.padeoe.nicservice.njuwlan.service.LoginService;
import com.padeoe.njunet.App;
import com.padeoe.njunet.connect.controller.ConnectManager;
import com.padeoe.njunet.connect.controller.ConnectService;
import com.padeoe.njunet.connect.uihandler.ErrorHandle;
import com.padeoe.njunet.connect.uihandler.WifiLostHandle;

/**
 * Created by padeoe on 4/20/15.
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver{
    public static boolean isTargetWifi;
    public void onReceive(final Context context, Intent intent) {
        NetworkInfo networkInfo=intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(networkInfo!=null){
            NetworkInfo.DetailedState state=networkInfo.getDetailedState();
            if(state==NetworkInfo.DetailedState.DISCONNECTED){
                if(ConnectManager.getStatus()==ConnectManager.Status.ONLINE){
                    ConnectManager.setStatus(ConnectManager.Status.OFFLINE);
                    System.out.println("下线");
                    //发送离线广播，可能别处的界面需要更新信息
                    Intent wifiLostIntent = new Intent(ConnectManager.WIFI_LOST_ACTION);
                    wifiLostIntent.putExtra("WIFI_LOST", new WifiLostHandle());
                    LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
                    broadcastManager.sendBroadcast(wifiLostIntent);
                }
            }
            else {
                if(state==NetworkInfo.DetailedState.CONNECTED){
                       WifiInfo wifiInfo=intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                    if(wifiInfo!=null){
                        //验证是否是目标wifi
                        if (ConnectService.isTargetWifi(wifiInfo.getSSID())) {
                            Log.d("SSID", wifiInfo.getSSID());
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    Log.d("onAvailable", "即将连接");
                                    System.out.println(LoginService.getInstance().connect(ConnectService.getPassword(), ConnectService.getUsername()));
                                    //连接相关的事情交给connectmanager代理，myObserver会作为订阅者获得结果
                                    ConnectManager connectManager=new ConnectManager();
                                    connectManager.backgrConnect();
                                }
                            }.start();
                        }
                        else{
                            Log.e("不是目标wifi","不是目标");
                            //更改全局状态
                            ConnectManager.setStatus(ConnectManager.Status.WIFI_LOST);
                            //发送广播，通知其他界面更新信息
                            Intent notTartgetIntent = new Intent(ConnectManager.NOT_TARGET_ACTION);
                            //下面2行有bug
                            notTartgetIntent.putExtra("WIFI_SSID", new ErrorHandle(wifiInfo.getSSID()));
                            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
                            broadcastManager.sendBroadcast(notTartgetIntent);
                        }
                    }
                }
            }
        }
    }

/*    @Override
    public void update(MyObservable myObservable, ConnectResultHandle data) {
        data.handleNetworkMonitor(this);
*//*        //更改全局状态
        ConnectManager.setStatus(data.isBackgrdLoginSuccess()?ConnectManager.Status.ONLINE:ConnectManager.Status.OFFLINE);
        //发送广播，通知其他界面更新信息
        Intent intent = new Intent(ConnectManager.BACKGROUND_LOGIN_ACTION);
        intent.putExtra("LOGIN_RESULT", data);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
        broadcastManager.sendBroadcast(intent);*//*
    }*/
}
