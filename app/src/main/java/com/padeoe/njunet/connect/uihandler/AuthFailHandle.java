package com.padeoe.njunet.connect.uihandler;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Parcel;

import com.padeoe.nicservice.njuwlan.object.portal.ReturnData;
import com.padeoe.njunet.App;
import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.controller.ConnectManager;

/**
 * Created by padeoe on 2016/5/19.
 */
public class AuthFailHandle implements ConnectResultHandle {
    ReturnData returnData;

    public AuthFailHandle(ReturnData returnData) {
        this.returnData = returnData;
        ConnectManager.setStatus(ConnectManager.Status.OFFLINE);
        StatusNotificationManager.showStatus("连接出错:" + returnData.getReply_message());
    }

    @Override
    public void updateView(MainActivity activity) {
        activity.hideProgress();
        WifiManager wifiManager = (WifiManager) App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        activity.setNetInfo("认证失败",wifiManager.getConnectionInfo().getSSID());
        //   activity.user_detail.setVisibility(View.VISIBLE);
        activity.updateViewStatus(activity.status, ConnectManager.getStatus());
        activity.showOnMainActivity(returnData.getReply_message());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.returnData, flags);
    }

    public AuthFailHandle() {
    }

    protected AuthFailHandle(Parcel in) {
        this.returnData = in.readParcelable(ReturnData.class.getClassLoader());
    }

    public static final Creator<AuthFailHandle> CREATOR = new Creator<AuthFailHandle>() {
        @Override
        public AuthFailHandle createFromParcel(Parcel source) {
            return new AuthFailHandle(source);
        }

        @Override
        public AuthFailHandle[] newArray(int size) {
            return new AuthFailHandle[size];
        }
    };
}
