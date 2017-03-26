package com.padeoe.njunet.connect.uihandler;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Parcel;

import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.controller.ConnectManager;

/**
 * Created by padeoe on 2016/6/3.
 */
public class ClientLimitHandle implements ConnectResultHandle {
    @Override
    public void updateView(MainActivity activity) {
        WifiManager wifiManager = (WifiManager) App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        activity.setNetInfo(App.getAppContext().getResources().getString(R.string.remote_online),wifiManager.getConnectionInfo().getSSID());
        activity.hideProgress();
        activity.updateViewStatus(activity.status, ConnectManager.getStatus());
        activity.showOnMainActivity(App.getAppContext().getResources().getString(R.string.force_login_button));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ClientLimitHandle() {
        ConnectManager.setStatus(ConnectManager.Status.REMOTE_ONLINE);
        StatusNotificationManager.showStatus(App.getAppContext().getResources().getString(R.string.remote_online));
    }

    protected ClientLimitHandle(Parcel in) {
    }

    public static final Creator<ClientLimitHandle> CREATOR = new Creator<ClientLimitHandle>() {
        @Override
        public ClientLimitHandle createFromParcel(Parcel source) {
            return new ClientLimitHandle(source);
        }

        @Override
        public ClientLimitHandle[] newArray(int size) {
            return new ClientLimitHandle[size];
        }
    };
}
