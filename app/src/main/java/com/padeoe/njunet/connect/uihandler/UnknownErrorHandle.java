package com.padeoe.njunet.connect.uihandler;

import android.os.Parcel;

import com.padeoe.njunet.connect.MainActivity;

/**
 * Created by padeoe on 2016/5/19.
 */
public class UnknownErrorHandle implements ConnectResultHandle {
    String error;

    public UnknownErrorHandle(String error) {
        this.error = error;
    }

    @Override
    public void updateView(MainActivity activity) {
        activity.hideProgress();
        activity.showOnMainActivity(error);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error);
    }

    public UnknownErrorHandle() {
    }

    protected UnknownErrorHandle(Parcel in) {
        this.error = in.readString();
    }

    public static final Creator<UnknownErrorHandle> CREATOR = new Creator<UnknownErrorHandle>() {
        @Override
        public UnknownErrorHandle createFromParcel(Parcel source) {
            return new UnknownErrorHandle(source);
        }

        @Override
        public UnknownErrorHandle[] newArray(int size) {
            return new UnknownErrorHandle[size];
        }
    };
}
