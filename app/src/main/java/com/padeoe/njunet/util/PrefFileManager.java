package com.padeoe.njunet.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.padeoe.njunet.App;

/**
 * Created by padeoe on 2016/4/21.
 */
public class PrefFileManager {
    public static SharedPreferences getAccountPref(){
        Log.i("正在读文件","正在读文件");
        return App.context.getSharedPreferences("DataFile", 0);
    }
    public static SharedPreferences getWiFiPref(){
        Log.i("正在读文件WiFiSSID","正在读文件WiFiSSID");
        return App.context.getSharedPreferences("WiFiSSID", 0);
    }
}
