package com.padeoe.autoconnect;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

/**
 * Created by padeoe on 2015/9/6.
 */
public class  DownloadService extends Service {
    BroadcastReceiver receiver;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
         receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                File apk=new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOWNLOADS+"/AutoConnect.apk");
                Log.i("apk路径", apk.getPath());
                Uri path = Uri.fromFile(apk);
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.setDataAndType(path, "application/vnd.android.package-archive");
                startActivity(install);
                stopSelf();
            }
        };
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
