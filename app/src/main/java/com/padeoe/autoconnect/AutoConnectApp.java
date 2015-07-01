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
        //添加LeanCloud用户统计分析，下面一行代码中的key仅用于测试，发布的apk中使用的不同
        AVOSCloud.initialize(this, "rfdbmj8hpdbo3dwx2unrqmvhfb2y8r6d3xrsaiwwoewr2bc4","c6n60q7onyffn97vey1jywk3bje590xlntp8ddasdo0hnvcy");
        // 启用崩溃错误统计
  //      AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
  //      AVOSCloud.setLastModifyEnabled(true);
  //      AVOSCloud.setDebugLogEnabled(true);
    }
}
