package com.padeoe.njunet.connect.uihandler;

import android.os.Parcel;

import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.connect.controller.ConnectManager;
import com.padeoe.njunet.util.MyAnimation;

/**
 * Created by padeoe on 2016/5/19.
 */
public class UnknownNetHandle implements ConnectResultHandle {
    String SSID;

    public UnknownNetHandle(String SSID) {
        this.SSID = SSID;
    }

    @Override
    public void updateView(MainActivity activity) {
        activity.hideProgress();
        //  activity.user_detail.setVisibility(View.VISIBLE);
        activity.setNetInfo(App.isInPortalWiFiSet(SSID) || App.isInSuspiciousWiFiSSIDSet(SSID) ?
                App.getAppContext().getResources().getString(R.string.NJUWLAN) : App.getAppContext().getResources().getString(R.string.Unknown_WLAN), SSID);
        activity.updateViewStatus(activity.status, ConnectManager.getStatus());
        MyAnimation.fadeInTextView(activity.status_internet);
        activity.showOnMainActivity(activity.getResources().getString(R.string.connect_fail));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.SSID);
    }

    protected UnknownNetHandle(Parcel in) {
        this.SSID = in.readString();
    }

    public static final Creator<UnknownNetHandle> CREATOR = new Creator<UnknownNetHandle>() {
        @Override
        public UnknownNetHandle createFromParcel(Parcel source) {
            return new UnknownNetHandle(source);
        }

        @Override
        public UnknownNetHandle[] newArray(int size) {
            return new UnknownNetHandle[size];
        }
    };
}
