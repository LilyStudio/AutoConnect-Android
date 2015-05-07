package com.padeoe.autoconnect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by padeoe on 4/20/15.
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    int i=0;
    public void onReceive(Context context, Intent intent) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("DateFile", 0);
        if(WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())){
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
            if(networkInfo!=null){
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected2 =(state==NetworkInfo.State.CONNECTED);
                if(isConnected2){
                    Log.i("NetworkConnectChanged","网络连接已经建立");
                    WifiManager  mWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = mWifi.getConnectionInfo();
                    Log.d("wifiInfo:", wifiInfo.getSSID());
                    if(wifiInfo.getSSID().equals("\"NJU-FAST\"")||wifiInfo.getSSID().equals("\"NJU-WLAN\"")){

                        if(i==1){
                            new Thread(){
                                @Override
                                public void run(){
                                    try {
                                        String PostData=sharedPreferences.getString("PostData",null);
                                        if(PostData!=null){
                                            for(int i=0;i<20;i++){
                                                if(Authenticate.connectAndPost(PostData)!=null)
                                                    break;
                                                else
                                                    Thread.sleep(200);
                                            }
                                        }
                                        else
                                            Log.i("Error","未设置用户名密码");
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }.start();
                            i=0;
                        }
                        else
                            i++;
                    }
                    else{
                        Log.i("RESULT","SSID不是目标" );
                    }
                }
            }
        }
    }
}
