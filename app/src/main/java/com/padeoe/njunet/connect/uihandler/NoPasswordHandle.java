package com.padeoe.njunet.connect.uihandler;

import android.content.Intent;
import android.os.Parcel;

import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.deploy.FirstSettingActivity;

/**
 * Created by padeoe on 2016/5/30.
 */
public class NoPasswordHandle implements ConnectResultHandle {
    @Override
    public void updateView(MainActivity activity) {
        Intent intent = new Intent();
        intent.setAction("com.padeoe.njunet.EDIT_ACCOUNT");
        intent.setClass(activity, FirstSettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public NoPasswordHandle() {
    }

    protected NoPasswordHandle(Parcel in) {
    }

    public static final Creator<NoPasswordHandle> CREATOR = new Creator<NoPasswordHandle>() {
        @Override
        public NoPasswordHandle createFromParcel(Parcel source) {
            return new NoPasswordHandle(source);
        }

        @Override
        public NoPasswordHandle[] newArray(int size) {
            return new NoPasswordHandle[size];
        }
    };
}
