package com.padeoe.njunet.connect.uihandler;

import android.os.Parcel;
import android.util.Log;

import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.connect.controller.UpdateInfo;
import com.padeoe.njunet.util.MyObservable;

/**
 * Created by padeoe on 2016/5/10.
 */
public class GetOnlineTimeFailHandle extends MyObservable<ConnectResultHandle> implements ConnectResultHandle {
    private static int times;
    private static int maxTry = 10;

    public GetOnlineTimeFailHandle() {
        times++;
    }

    @Override
    public void updateView(MainActivity activity) {
        if (times < maxTry) {
            Log.d("尝试", "即将第" + times + "次获取时长");
            UpdateInfo updateInfo = new UpdateInfo();
            updateInfo.addObserver(activity);
            updateInfo.updateOnlineTime();
            times++;
        } else {
            activity.hideProgress();
            resetTimes();
            notifyObservers(new ErrorHandle("获取时长失败"));
            Log.e("获取时长失败", "失败");
        }
    }

    public static void resetTimes() {
        times = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.maxTry);
    }

    protected GetOnlineTimeFailHandle(Parcel in) {
        this.maxTry = in.readInt();
    }

    public static final Creator<GetOnlineTimeFailHandle> CREATOR = new Creator<GetOnlineTimeFailHandle>() {
        @Override
        public GetOnlineTimeFailHandle createFromParcel(Parcel source) {
            return new GetOnlineTimeFailHandle(source);
        }

        @Override
        public GetOnlineTimeFailHandle[] newArray(int size) {
            return new GetOnlineTimeFailHandle[size];
        }
    };

    public static int getMaxTry() {
        return maxTry;
    }

    public static void setMaxTry(int maxTry) {
        GetOnlineTimeFailHandle.maxTry = maxTry;
    }
}
