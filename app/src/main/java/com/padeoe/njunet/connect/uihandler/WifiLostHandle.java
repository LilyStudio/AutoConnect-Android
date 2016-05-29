package com.padeoe.njunet.connect.uihandler;

import android.os.Parcel;

import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.connect.controller.ConnectManager;

/**
 * Created by padeoe on 2016/5/18.
 */
public class WifiLostHandle implements ConnectResultHandle {

    @Override
    public void updateView(MainActivity activity) {
        activity.hideProgress();
        activity.setNetInfo(null);
      //  activity.user_detail.setVisibility(View.INVISIBLE);
        ConnectManager.setStatus(ConnectManager.Status.WIFI_LOST);
        activity.updateViewStatus(activity.status, ConnectManager.getStatus());
        activity.showOnMainActivity("未连接wifi");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public WifiLostHandle() {
    }

    protected WifiLostHandle(Parcel in) {
    }

    public static final Creator<WifiLostHandle> CREATOR = new Creator<WifiLostHandle>() {
        @Override
        public WifiLostHandle createFromParcel(Parcel source) {
            return new WifiLostHandle(source);
        }

        @Override
        public WifiLostHandle[] newArray(int size) {
            return new WifiLostHandle[size];
        }
    };
}
