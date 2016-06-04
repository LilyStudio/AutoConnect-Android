package com.padeoe.njunet.connect.uihandler;

import android.os.Parcel;

import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.MainActivity;

/**
 * Created by padeoe on 2016/5/23.
 */
public class WifiAvailableHandle implements ConnectResultHandle {
    String ssid;

    @Override
    public void updateView(MainActivity activity) {
        activity.setNetInfo(App.isInPortalWiFiSet(ssid) || App.isInSuspiciousWiFiSSIDSet(ssid) ?
                App.getAppContext().getResources().getString(R.string.NJUWLAN) : App.getAppContext().getResources().getString(R.string.Unknown_WLAN), ssid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ssid);
    }

    public WifiAvailableHandle(String ssid) {
        this.ssid = ssid;
    }

    protected WifiAvailableHandle(Parcel in) {
        this.ssid = in.readString();
    }

    public static final Creator<WifiAvailableHandle> CREATOR = new Creator<WifiAvailableHandle>() {
        @Override
        public WifiAvailableHandle createFromParcel(Parcel source) {
            return new WifiAvailableHandle(source);
        }

        @Override
        public WifiAvailableHandle[] newArray(int size) {
            return new WifiAvailableHandle[size];
        }
    };
}
