package com.padeoe.njunet.connect.uihandler;

import android.os.Parcel;

import com.padeoe.nicservice.njuwlan.object.portal.ReturnData;
import com.padeoe.njunet.connect.MainActivity;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.controller.ConnectManager;

/**
 * Created by padeoe on 2016/5/16.
 * 该类表示后台自动登录成功后的处理,负责更新通知，发送广播更新主界面。该类的{@linkplain #updateView(MainActivity)}无法独立完成对界面的更新，而是生成其他的{@link ConnectResultHandle}对象
 */
public class BackgrReturnDataHandle implements ConnectResultHandle {
    String result;

    public BackgrReturnDataHandle(String result) {
        this.result = result;
        handleNotification();
    }

    public void handleNotification() {
        ConnectManager.setStatus(ConnectManager.Status.ONLINE);
        StatusNotificationManager.showStatus();
    }

    /**
     * MainActivity通过通知的形式接收到本类的实例，并将自己传进本实例的本方法进行更新界面
     * @param activity
     */
    @Override
    public void updateView(MainActivity activity) {
        getRealInfoHandle().updateView(activity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.result);
    }

    public BackgrReturnDataHandle() {
    }

    protected BackgrReturnDataHandle(Parcel in) {
        this.result = in.readString();
    }

    public static final Creator<BackgrReturnDataHandle> CREATOR = new Creator<BackgrReturnDataHandle>() {
        @Override
        public BackgrReturnDataHandle createFromParcel(Parcel source) {
            return new BackgrReturnDataHandle(source);
        }

        @Override
        public BackgrReturnDataHandle[] newArray(int size) {
            return new BackgrReturnDataHandle[size];
        }
    };

    /**
     * 用于获得具体状态的handle对象。
     *
     * @return
     */
    private ConnectResultHandle getRealInfoHandle() {
        ReturnData returnData = ReturnData.getFromJson(result);
        return returnData == null ? new UnknownErrorHandle(result) : new ReturnDataHandle(returnData);
    }


/*    @Override
    public void handleNetworkMonitor(MyObserver<ConnectResultHandle> myObserver) {
        ConnectFailHandle.resetTimes();
        if(ReturnData.getFromJson(result)==null){
            new UnknownErrorHandle().handleNetworkMonitor(myObserver);
        }
        else{
            //更改全局状态
            ConnectManager.setStatus(ConnectManager.Status.ONLINE);
            StatusNotificationManager.showStatus("南大校园网");
            //发送广播，通知其他界面更新信息
            Intent intent = new Intent(ConnectManager.BACKGROUND_LOGIN_ACTION);
            intent.putExtra("LOGIN_RESULT", this);
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
            broadcastManager.sendBroadcast(intent);
        }

    }*/
}
