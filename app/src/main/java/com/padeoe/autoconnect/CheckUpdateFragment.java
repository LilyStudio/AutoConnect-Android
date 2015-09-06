package com.padeoe.autoconnect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;

import java.io.File;

/**
 * Created by padeoe on 2015/9/4.
 */
public class CheckUpdateFragment extends DialogFragment {
    CheckUpdateFragment checkUpdateFragment;
    String url;
    String newVersionName;
    String installedVersionName;
    String apkSize;
    FragmentManager fm;
    DownloadManager downloadManager;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_dialog, null);
        builder.setView(view)
                .setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        App.context.startService(new Intent(App.context, DownloadService.class));
                        downloadNewVersionApp();
                    }
                })
                .setNegativeButton(R.string.update_soon, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
        .setTitle((String)getResources().getText(R.string.find_new_version)+newVersionName);
        TextView filesize=(TextView)view.findViewById(R.id.filesize);
        filesize.setText(getResources().getString(R.string.apk_size)+apkSize);
        return builder.create();
    }


    /**
     * 获取已安装的版本号
     *
     * @return
     */
    private static String getInstalledVersion() {
        try {
            return App.context.getPackageManager()
                    .getPackageInfo(App.context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 下载
     */
    public void downloadNewVersionApp() {
        downloadManager = (DownloadManager) App.context.getSystemService(App.context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "AutoConnect.apk");
        long downloadId = downloadManager.enqueue(request);




    }

    public void checkUpdate(FragmentManager fm) {
        this.fm = fm;
        Log.i("检查更新", "即将开始检查更新");
        AVQuery<AVObject> query = new AVQuery<AVObject>("NewestVersion");
        query.getInBackground("55e9a7c960b2617119a7fb51", new GetCallback<AVObject>() {
            public void done(AVObject newestVersion, AVException e) {
                if (e == null) {
                    installedVersionName = getInstalledVersion();
                    if (installedVersionName != null) {
                        if (true) {
                            //   if(!installedVersion.equals(newestVersion.getString("versionName"))){
                            url = newestVersion.getString("url");
                            newVersionName = newestVersion.getString("versionName");
                            apkSize= newestVersion.getString("size");
                            showDownloadDialog();
                        } else {
                            Log.i("检查更新", (String) App.context.getResources().getText(R.string.isNewestVersion) + installedVersionName);
                            Toast.makeText(App.context, (String) App.context.getResources().getText(R.string.isNewestVersion) + installedVersionName, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.i("获取本地程序版本号","程序版本号获取失败");
                    }
                } else {
                    Log.i("检查更新", e.getMessage());
                    Toast.makeText(App.context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showDownloadDialog() {
        this.show(fm, "showNewVersion");
        Log.i("downloadApk", "即将下载" + url);
    }
}




