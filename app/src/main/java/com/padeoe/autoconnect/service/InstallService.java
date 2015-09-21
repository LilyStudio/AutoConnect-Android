package com.padeoe.autoconnect.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.padeoe.autoconnect.activity.App;

import java.io.File;

/**
 * Created by padeoe on 2015/9/6.
 */
public class InstallService extends Service {
    BroadcastReceiver downloadCompleteReceiver;
    BroadcastReceiver downloadPaunseReceiver;
    String apkName;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            Log.d("InstallService", "can't get apkName");
            Toast.makeText(App.context, "程序出错", Toast.LENGTH_SHORT).show();
            stopSelf();
        } else {
            apkName = (String) extras.get("apkName");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downloadCompleteReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                File apk = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + apkName);
                Log.i("apk路径", apk.getPath());
                Uri path = Uri.fromFile(apk);
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.setDataAndType(path, "application/vnd.android.package-archive");
                startActivity(install);
                stopSelf();
            }
        };
        downloadPaunseReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //终止下载
                stopSelf();
            }
        };
        IntentFilter filterDownloadStall = new IntentFilter();
        filterDownloadStall.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadCompleteReceiver, new IntentFilter(filterDownloadStall));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadCompleteReceiver);
    }
}
