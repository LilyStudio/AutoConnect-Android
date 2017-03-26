package com.padeoe.njunet.connect.uihandler;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcel;
import android.util.Log;

import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.controller.ConnectManager;
import com.padeoe.njunet.util.MyObservable;
import com.padeoe.njunet.util.MyObserver;

/**
 * Created by padeoe on 2016/5/16.
 */
public class ConnectFailHandle extends MyObservable<ConnectResultHandle> implements ConnectResultHandle, MyObserver<Reachable> {
    ConnectManager connectManager;
    int currentTimes;

    /**
     * @param connectManager 连接管理器，此处获取是为了调用它的连接函数继续循环尝试
     * @param currentTimes   当前尝试的次数
     */
    public ConnectFailHandle(ConnectManager connectManager, int currentTimes) {
        this.connectManager = connectManager;
        this.currentTimes = currentTimes;
    }

    /**
     * 失败结果不会立即通知调用者，而是当多次尝试重连都失败后通知,
     *
     * @return 表明是否应该通知调用者
     */
    public void handle() {
        //奇数次进行外网连通性测试，偶数次进行校园网连通性测试，直到达到maxTry的次数或者任一网络联通性测试通过
        if (currentTimes % 2 == 1) {
            currentTimes++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConnectManager connectManager2 = new ConnectManager();
            connectManager2.addObserver(this);
            connectManager2.networkTest();
        } else {
            if (currentTimes < App.getMaxTry()) {
                currentTimes++;
                connectManager.login(this);
            } else {
                StatusNotificationManager.showStatus();
                Log.e("ConnectFailHandle", "达到了最大尝试次数,判定为未知网路");
                connectManager.notifyObservers(new ErrorHandle(App.getAppContext().getResources().getString(R.string.connect_fail)));
            }
        }
    }

    @Override
    public void updateView(MainActivity activity) {
        Log.e("ConnectFailHandle", "内部错误，ConnectFailHandle不应该被直接使用");
    }


    @Override
    public void update(MyObservable myObservable, Reachable data) {
        Log.d("测试结果", "已接收到测试结果" + data.getCode());
        WifiManager mWifi = (WifiManager) App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        String ssid = wifiInfo.getSSID();

        if (data.isInternetAvailable()) {
            ConnectManager.setStatus(ConnectManager.Status.ONLINE);
            StatusNotificationManager.showStatus();
            connectManager.notifyObservers(new UnknownNetHandle(ssid));
        } else {
            if (data.isCaptital()) {
                ConnectManager.setStatus(ConnectManager.Status.OFFLINE);
                StatusNotificationManager.showStatus(App.isInPortalWiFiSet(ssid) || App.isInSuspiciousWiFiSSIDSet(ssid) ? App.getAppContext().getString(R.string.NJUWLAN) : App.getAppContext().getResources().getString(R.string.need_auth));
                connectManager.notifyObservers(new UnknownNetHandle(ssid));
            } else {
                ConnectManager.setStatus(ConnectManager.Status.OFFLINE);
                connectManager.login(this);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.currentTimes);
    }

    protected ConnectFailHandle(Parcel in) {
        this.currentTimes = in.readInt();
    }

    public static final Creator<ConnectFailHandle> CREATOR = new Creator<ConnectFailHandle>() {
        @Override
        public ConnectFailHandle createFromParcel(Parcel source) {
            return new ConnectFailHandle(source);
        }

        @Override
        public ConnectFailHandle[] newArray(int size) {
            return new ConnectFailHandle[size];
        }
    };
}
