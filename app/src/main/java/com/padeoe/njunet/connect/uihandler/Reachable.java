package com.padeoe.njunet.connect.uihandler;

import android.os.Parcel;
import android.os.Parcelable;

import com.padeoe.njunet.connect.MainActivity;

/**
 * Created by padeoe on 2016/5/18.
 */
public class Reachable implements ConnectResultHandle {
    int code;

    public int getCode() {
        return code;
    }

    public Reachable(int code) {
        this.code = code;
/*        if(this.isInternetAvailable()){
            Log.d("Reachable","网络测试可用");
        }
        else{
            Log.d("Reachable","网络测试不可用，即将继续尝试登陆");
            connectManager.backgrConnect();
        }*/
    }

    @Override
    public void updateView(MainActivity activity) {
        activity.showOnMainActivity("code="+code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
    }

    protected Reachable(Parcel in) {
        this.code = in.readInt();
    }

    public static final Parcelable.Creator<Reachable> CREATOR = new Parcelable.Creator<Reachable>() {
        @Override
        public Reachable createFromParcel(Parcel source) {
            return new Reachable(source);
        }

        @Override
        public Reachable[] newArray(int size) {
            return new Reachable[size];
        }
    };

    public boolean isInternetAvailable(){
        return code==204;
    }

    public boolean isCaptital(){return code!=204&&code>=200&&code<=399;}
}
