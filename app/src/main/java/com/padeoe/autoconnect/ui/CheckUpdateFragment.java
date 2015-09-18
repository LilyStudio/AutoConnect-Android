package com.padeoe.autoconnect.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.padeoe.autoconnect.activity.App;
import com.padeoe.autoconnect.activity.MainActivity;
import com.padeoe.autoconnect.R;
import com.padeoe.autoconnect.service.DownloadService;

/**
 * Created by padeoe on 2015/9/4.
 */
public class CheckUpdateFragment extends DialogFragment{
    public String url;
    public String newVersionName;
    public String apkSize;
    MainActivity mainActivity;
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
                        mainActivity.downloadNewVersionApp();
                    }
                })
                .setNegativeButton(R.string.update_soon, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setTitle((String) getResources().getText(R.string.find_new_version) + newVersionName);
        TextView filesize = (TextView) view.findViewById(R.id.filesize);
        filesize.setText(getResources().getString(R.string.apk_size) + apkSize);
        return builder.create();
    }


    /**
     * 获取已安装的版本号
     *
     * @return
     */
    public static String getInstalledVersion() {
        try {
            return App.context.getPackageManager()
                    .getPackageInfo(App.context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void showDownloadDialog(String url,String newVersionName,String apkSize,FragmentManager fm,MainActivity mainActivity) {
        this.url=url;this.newVersionName=newVersionName;this.apkSize=apkSize;
        this.mainActivity=mainActivity;
        this.show(fm, "showNewVersion");
        Log.i("downloadApk", "即将下载" + url);
    }
}




