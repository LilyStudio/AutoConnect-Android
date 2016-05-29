package com.padeoe.njunet.connect.uihandler;

import android.os.Parcel;
import android.os.Parcelable;

import com.padeoe.njunet.connect.MainActivity;

/**
 * Created by padeoe on 2016/5/10.
 */
public class OnlineTimeHandle implements ConnectResultHandle {
    int second;
    public OnlineTimeHandle(int second){
        this.second=second;
    }

    @Override
    public void updateView(MainActivity activity) {
        GetOnlineTimeFailHandle.resetTimes();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(second/3600).append("小时").append(second%3600/60).append("分钟");
        activity.time.setText(stringBuilder.toString());
        activity.hideProgress();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.second);
    }

    protected OnlineTimeHandle(Parcel in) {
        this.second = in.readInt();
    }

    public static final Parcelable.Creator<OnlineTimeHandle> CREATOR = new Parcelable.Creator<OnlineTimeHandle>() {
        @Override
        public OnlineTimeHandle createFromParcel(Parcel source) {
            return new OnlineTimeHandle(source);
        }

        @Override
        public OnlineTimeHandle[] newArray(int size) {
            return new OnlineTimeHandle[size];
        }
    };
}
