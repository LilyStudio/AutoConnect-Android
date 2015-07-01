package com.padeoe.autoconnect;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by padeoe on 2015/7/1.
 */
public class AutoConnectApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //以下代码是用来测试内存状况的，应该在发布时被删除
        int a=0;int b=0;int c=0;int d=0;
        // 启用崩溃错误统计
        //      AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
        //      AVOSCloud.setLastModifyEnabled(true);
        //      AVOSCloud.setDebugLogEnabled(true);
    }
}