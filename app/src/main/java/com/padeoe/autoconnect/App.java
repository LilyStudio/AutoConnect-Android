package com.padeoe.autoconnect;

import android.app.Application;
import android.content.Context;

/**
 * Created by padeoe on 2015/7/2.
 */
public class App extends Application {
    public static final String LOGINURL="http://219.219.114.15/portal_io/login";
    public static final String LOGOUTURL="http://219.219.114.15/portal_io/logout";
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
}
