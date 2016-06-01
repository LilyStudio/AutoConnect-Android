package com.padeoe.njunet.connect.uihandler;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Parcel;

import com.padeoe.nicservice.njuwlan.object.portal.ReturnData;
import com.padeoe.nicservice.njuwlan.object.portal.UserInfo;
import com.padeoe.njunet.App;
import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.controller.ConnectManager;
import com.padeoe.njunet.connect.controller.UpdateInfo;
import com.padeoe.njunet.util.MyAnimation;
import com.padeoe.utils.ConvertionUtils;

/**
 * Created by padeoe on 2016/5/10.
 */
public class ReturnDataHandle implements ConnectResultHandle {
    ReturnData returnData;

    public ReturnDataHandle(ReturnData returnData) {
        this.returnData = returnData;
        handleNotification();
    }

    public void handleNotification() {
        ConnectManager.setStatus(ConnectManager.Status.ONLINE);
        StatusNotificationManager.showStatus();
    }

    @Override
    public void updateView(MainActivity mainActivity) {
        if (returnData != null) {
            UserInfo userinfo = returnData.getUserInfo();
            if (userinfo != null) {
                //    mainActivity.username.setText(userinfo.getUsername() + "(" + userinfo.getFullname() + ")");
                WifiManager wifiManager = (WifiManager) App.getAppContext().getSystemService(Context.WIFI_SERVICE);
                mainActivity.setNetInfo(wifiManager.getConnectionInfo().getSSID());
                // mainActivity.user_detail.setVisibility(View.VISIBLE);
                mainActivity.amount.setText(Double.valueOf(userinfo.getBalance()) / 100 + "元");
                mainActivity.location.setText(userinfo.getArea_name());
                mainActivity.ip.setText(ConvertionUtils.getIP(userinfo.getUseripv4()));
                MyAnimation.fadeInTextView(mainActivity.amount);
                MyAnimation.fadeInTextView(mainActivity.location);
                MyAnimation.fadeInTextView(mainActivity.ip);
                UpdateInfo updateInfo = new UpdateInfo();
                updateInfo.addObserver(mainActivity);
                updateInfo.updateOnlineTime();
            } else {
                ConnectManager.setStatus(ConnectManager.Status.OFFLINE);
            }
            mainActivity.updateViewStatus(mainActivity.status, ConnectManager.getStatus());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.returnData, flags);
    }

    protected ReturnDataHandle(Parcel in) {
        this.returnData = in.readParcelable(ReturnData.class.getClassLoader());
    }

    public static final Creator<ReturnDataHandle> CREATOR = new Creator<ReturnDataHandle>() {
        @Override
        public ReturnDataHandle createFromParcel(Parcel source) {
            return new ReturnDataHandle(source);
        }

        @Override
        public ReturnDataHandle[] newArray(int size) {
            return new ReturnDataHandle[size];
        }
    };
}
