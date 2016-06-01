package com.padeoe.njunet.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.padeoe.njunet.App;

/**
 * Created by padeoe on 2016/4/21.
 */
public class PrefFileManager {
    public static SharedPreferences getAccountPref() {
        return App.context.getSharedPreferences("DataFile", 0);
    }

    public static SharedPreferences getWiFiPref() {
        return App.context.getSharedPreferences("WiFiSSID", 0);
    }
}
