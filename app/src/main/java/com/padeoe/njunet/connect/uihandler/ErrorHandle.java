package com.padeoe.njunet.connect.uihandler;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Parcel;

import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.connect.controller.ConnectManager;

/**
 * Created by padeoe on 2016/5/10.
 */
public class ErrorHandle implements ConnectResultHandle {
    String error;

    public ErrorHandle(String error) {
        this.error = error;
        ConnectManager.setStatus(ConnectManager.Status.OFFLINE);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void updateView(MainActivity mainActivity) {
        mainActivity.hideProgress();
        mainActivity.showOnMainActivity(error);
        WifiManager wifiManager = (WifiManager) App.getAppContext().getSystemService(Context.WIFI_SERVICE);
        mainActivity.setNetInfo(App.getAppContext().getResources().getString(R.string.connect_error),wifiManager.getConnectionInfo().getSSID());
        mainActivity.updateViewStatus(mainActivity.status,ConnectManager.getStatus());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error);
    }

    protected ErrorHandle(Parcel in) {
        this.error = in.readString();
    }

    public static final Creator<ErrorHandle> CREATOR = new Creator<ErrorHandle>() {
        @Override
        public ErrorHandle createFromParcel(Parcel source) {
            return new ErrorHandle(source);
        }

        @Override
        public ErrorHandle[] newArray(int size) {
            return new ErrorHandle[size];
        }
    };
}
