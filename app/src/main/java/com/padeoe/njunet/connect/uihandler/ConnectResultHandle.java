package com.padeoe.njunet.connect.uihandler;

import android.os.Parcelable;

import com.padeoe.njunet.connect.MainActivity;

/**
 * Created by padeoe on 2016/5/17.
 */
public interface ConnectResultHandle extends Parcelable{
    void updateView(MainActivity activity);
}
