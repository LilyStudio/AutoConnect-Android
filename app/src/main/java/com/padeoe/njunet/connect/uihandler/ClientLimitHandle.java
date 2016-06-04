package com.padeoe.njunet.connect.uihandler;

import android.os.Parcel;

import com.padeoe.njunet.connect.MainActivity;

/**
 * Created by padeoe on 2016/6/3.
 */
public class ClientLimitHandle implements ConnectResultHandle {
    @Override
    public void updateView(MainActivity activity) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ClientLimitHandle() {
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
