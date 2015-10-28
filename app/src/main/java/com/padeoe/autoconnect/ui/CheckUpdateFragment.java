package com.padeoe.autoconnect.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.padeoe.autoconnect.activity.App;
import com.padeoe.autoconnect.activity.MainActivity;
import com.padeoe.autoconnect.R;

/**
 * Created by padeoe on 2015/9/4.
 */
public class CheckUpdateFragment extends DialogFragment {
    public String url;
    public String newVersionName;
    public String installedVersionName;
    public String apkSize;
    public String log;
    UpdateListener mListener;

    @Override
    public void onStart() {
        super.onStart();
    }

    public interface UpdateListener {
        public void updateClick(DialogFragment dialog);
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
                        mListener.updateClick(CheckUpdateFragment.this);
                    }
                })
                .setNegativeButton(R.string.update_soon, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setTitle((String) getResources().getText(R.string.find_new_version) + newVersionName+"("+apkSize+")")
                .setMessage(log);
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

    public void setLog(String log) {
        this.log = log;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public void setNewVersionName(String newVersionName){
        this.newVersionName=newVersionName;
    }
    public void setInstalledVersionName(String installedVersionName){
        this.installedVersionName=installedVersionName;
    }
    public void setApkSize(String apkSize){
        this.apkSize=apkSize;
    }
    public void showDownloadDialog(FragmentManager fm) {
        this.show(fm, "showNewVersion");
        Log.i("downloadApk", "即将下载" + url);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (UpdateListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement UpdateListener");
        }
    }

    /**
     * 判断版本号是否更新
     *
     * @param versionName1
     * @param versionName2
     * @return versionName1新于versionName2则返回true
     */
    public static boolean isNewer(String versionName1, String versionName2) {
        String[] version1 = versionName1.split("\\.|-");
        String[] version2 = versionName2.split("\\.|-");
        for (int i = 0; i < Math.min(version1.length, version2.length); i++) {
            if (getCodeNumber(version1[i]) > getCodeNumber(version2[i])) {
                return true;
            } else {
                if (getCodeNumber(version1[i]) < getCodeNumber(version2[i])) {
                    return false;
                }
            }
        }
        if (version1.length == version2.length) {
            return false;
        } else {
            if (version1.length > version2.length) {
                if (getCodeNumber(version1[Math.min(version1.length, version2.length)]) > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (getCodeNumber(version2[Math.min(version1.length, version2.length)]) < 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    /**
     * 获取版本号分段标识码
     *
     * @param codeString
     * @return
     */
    private static double getCodeNumber(String codeString) {
        int n = codeString.charAt(0);
        if (n >= 48 && n <= 57) {
            return n - 48;
        } else {
            switch (codeString) {
                case "beta":
                    return -0.1;
                case "debug":
                    return -0.2;
                case "dev":
                    return -0.3;
                default:
                    return -1;
            }
        }
    }
}




