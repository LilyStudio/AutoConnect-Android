package com.padeoe.njunet.connect.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.padeoe.njunet.App;
import com.padeoe.njunet.connect.StatusNotificationManager;

import java.util.List;

/**
 * Created by padeoe on 2016/5/21.
 */
public class ScanResultReceiver extends BroadcastReceiver {
    WifiManager wifiManager= (WifiManager)App.getAppContext().getSystemService(Context.WIFI_SERVICE);
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("已接收到扫描结果");
        List<ScanResult> scanResults= wifiManager.getScanResults();
        String myRequestWiFi=null;

        if(Build.VERSION.SDK_INT >= 17){
                for(ScanResult s:scanResults){
                    if(App.isInPortalWiFiSet("\""+s.SSID+"\"")){
                        myRequestWiFi="\""+s.SSID+"\"";
                        break;
                    }
                }

            if(myRequestWiFi==null){
                System.out.println("需要进一步寻找");
                    for(ScanResult s:scanResults){
                        if(App.isInPortalWiFiSet("\""+s.SSID+"\"")){
                            myRequestWiFi="\""+s.SSID+"\"";
                            break;
                        }
                    }

            }
        }
        else{
            for(ScanResult s:scanResults){
                if(App.isInPortalWiFiSet(s.SSID)){
                    myRequestWiFi=s.SSID;
                    break;
                }
            }

            if(myRequestWiFi==null){
                System.out.println("需要进一步寻找");
                for(ScanResult s:scanResults){
                    if(App.isInPortalWiFiSet(s.SSID)){
                        myRequestWiFi=s.SSID;
                        break;
                    }
                }

            }
        }
        if(myRequestWiFi==null){
            Log.e("未扫描到可用校园网","扫描连接失败");
            StatusNotificationManager.showStatus("未找到校园网Wifi");
        }
        else{
            Log.e("扫描成功",myRequestWiFi);
            StatusNotificationManager.showStatus("正在连接"+myRequestWiFi);
            if(!App.isInPortalWiFiSet(wifiManager.getConnectionInfo().getSSID())&&!App.isInSuspiciousWiFiSSIDSet(wifiManager.getConnectionInfo().getSSID())){
                List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                for( WifiConfiguration i : list ) {
                    if(i.SSID != null && i.SSID.equals(myRequestWiFi)) {
                        wifiManager.enableNetwork(i.networkId, true);
                        wifiManager.reassociate();
                        break;
                    }
                }
            }
        }
        App.getAppContext().unregisterReceiver(this);
    }
}
