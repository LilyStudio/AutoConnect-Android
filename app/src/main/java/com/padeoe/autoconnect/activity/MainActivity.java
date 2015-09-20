package com.padeoe.autoconnect.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;


import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.padeoe.autoconnect.service.InstallService;
import com.padeoe.autoconnect.ui.ExplainPermissionFragment;
import com.padeoe.autoconnect.util.NetworkUtils;
import com.padeoe.autoconnect.R;
import com.padeoe.autoconnect.service.WiFiDetectService;
import com.padeoe.autoconnect.ui.AboutDialogFragment;
import com.padeoe.autoconnect.ui.CheckUpdateFragment;
import com.padeoe.autoconnect.ui.SettingDialogFragment;
import com.padeoe.nicservice.njuwlan.ConnectPNJU;
import com.padeoe.nicservice.njuwlan.ReturnData;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity implements CheckUpdateFragment.UpdateListener, ExplainPermissionFragment.ExplainPermissionListener {
    EditText usernameEdit;
    EditText passwordEdit;
    SharedPreferences.Editor editor = null;
    SharedPreferences sharedPreferences;
    CheckUpdateFragment checkUpdateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //添加LeanCloud用户统计分析，下面一行代码中的key仅用于测试，发布的apk中使用的不同
        AVOSCloud.initialize(this, "rfdbmj8hpdbo3dwx2unrqmvhfb2y8r6d3xrsaiwwoewr2bc4", "c6n60q7onyffn97vey1jywk3bje590xlntp8ddasdo0hnvcy");

        //获取现有配置
        sharedPreferences = App.context.getSharedPreferences("DataFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        //显示现有配置
        usernameEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);
        if (username != null) usernameEdit.setText(username);
        if (password != null) passwordEdit.setText(password);
        if (username != null & password != null) {
            this.startService(new Intent(this, WiFiDetectService.class));
        }

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[]{(String) getResources().getText(R.string.open_pnju)};

        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                switch ((int) id) {
                    case 0:
                        String url = "http://p.nju.edu.cn";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        break;
                }
            }

        });


        boolean isFirstInstall = sharedPreferences.getBoolean("isFirstInstall", true);
        //do something
        if (isFirstInstall) {
            editor.putBoolean("isFirstInstall", false);
            editor.commit();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    staticsButtonOnClicked(true);
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    staticsButtonOnClicked(false);
                }
            });
            // Set other dialog properties
            builder.setTitle((String) getResources().getText(R.string.statistics_title));
            builder.setMessage((String) getResources().getText(R.string.statistics_message));
            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    /**
     * {@link CheckUpdateFragment}请求更新的回调函数
     *
     * @param dialog
     */
    @Override
    public void updateClick(DialogFragment dialog) {
        Intent installIntent = new Intent(App.context, InstallService.class);
        installIntent.putExtra("apkName", "AutoConnect-" + checkUpdateFragment.newVersionName + ".apk");
        App.context.startService(installIntent);
        downloadNewVersionApp();
    }

    /**
     * {@link ExplainPermissionFragment}权限说明阅读结束的回调函数
     *
     * @param dialog
     */
    @Override
    public void explainOverClick(DialogFragment dialog) {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        new Thread() {
            @Override
            public void run() {
                switch (id) {
                    case R.id.storePreference:
                        storeInfo();
                        break;
                    case R.id.connect:
                        connectNow();
/*                            PackageManager p = getPackageManager();
                            ComponentName componentName = new ComponentName(App.context, com.padeoe.autoconnect.activity.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
                            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);*/
                        break;
                    case R.id.disconnect:
                        disconnectNow();
                        break;
                    case R.id.setting:
                        FragmentManager fm1 = MainActivity.this.getFragmentManager();
                        SettingDialogFragment settingDialogFragment = new SettingDialogFragment();
                        settingDialogFragment.show(fm1, "s");
                        break;
                    case R.id.about:
                        FragmentManager fm = MainActivity.this.getFragmentManager();
                        new AboutDialogFragment().show(fm, "s");
                        break;
                    case R.id.check_update:
                        checkUpdate();
                        break;
                    default:
                        break;
                }
            }

            ;
        }.start();
        return true;

    }

    /**
     * 立即注销
     */
    public void disconnectNow() {
        if (isConnectedtoWiFi()) {
            ShowOnMainActivity(NetworkUtils.getShowResult(ConnectPNJU.disconnect(200), false));
            if (sharedPreferences.getBoolean("allow_statistics", false)) {
                AVAnalytics.onEvent(App.context, "立即注销NJU-WLAN");
            }
        } else {
            ShowOnMainActivity((String) getResources().getText(R.string.no_wifi));
        }
    }

    /**
     * 立即登录
     */
    public void connectNow() {
        //获取用户名密码
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        //判断用户是否填写了用户名密码
        if (username.length() > 0 && password.length() > 0) {
            if (isConnectedtoWiFi()) {
                String connectResult = ConnectPNJU.connect(username, password, 200);
                ShowOnMainActivity(NetworkUtils.getShowResult(connectResult, true));
                ReturnData returnData = null;
                try {
                    returnData = NetworkUtils.getReturnDataObject(connectResult);
                } catch (Exception e) {

                }
                if (returnData != null && returnData.getReply_message().equals("登录成功!")) {
                    App.context.startService(new Intent(App.context, WiFiDetectService.class));
                    if (sharedPreferences.getString("username", null) == null) {
                        storeInfo();
                    }
                }
                if (sharedPreferences.getBoolean("allow_statistics", false)) {
                    AVAnalytics.onEvent(App.context, "立即登陆NJU-WLAtgN");
                }

            }
            //如果用户没有填写用户名密码
            else
                ShowOnMainActivity((String) getResources().getText(R.string.no_wifi));
        } else {
            ShowOnMainActivity((String) getResources().getText(R.string.blank_input));
        }

    }

    /**
     * 存储用户名密码
     */
    public void storeInfo() {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
        if (username.length() > 0 && password.length() > 0) {
            editor.putString("username", username);
            editor.putString("password", password);
            editor.commit();
            App.context.startService(new Intent(App.context, WiFiDetectService.class));
            Log.i("配置文件", "保存了用户名密码");
            Log.i("保存", " 保存后开启了服务");
            ShowOnMainActivity((String) getResources().getText(R.string.saved_success));
        } else
            ShowOnMainActivity((String) getResources().getText(R.string.blank_input));
    }

    /**
     * 在MainActivity显示内容为string的Toast
     *
     * @param string 要显示的字符
     */
    public void ShowOnMainActivity(String string) {
        final String string2 = string;
        try {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(App.context, string2, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e1) {
            Log.i("ShowOnMainActivity", "Toast显示失败");
        }
    }

    /**
     * @return 返回值表示是否开启了无线网连接
     */
    public boolean isConnectedtoWiFi() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }


    /**
     * {@link SettingDialogFragment}中Switch开关的事件响应
     *
     * @param view
     */
    public void autoRun(View view) {
        boolean isChecked = ((Switch) view).isChecked();
        switchOnClicked(isChecked);
    }

    /**
     * {@link SettingDialogFragment}中Switch开关的事件响应
     *
     * @param isChecked
     */
    public void switchOnClicked(boolean isChecked) {
        if (isChecked) {
            editor.putBoolean("isBanned", true);
            editor.commit();
            Toast.makeText(App.context, (String) getResources().getText(R.string.have_prohibited_boot_start), Toast.LENGTH_SHORT).show();
            AVAnalytics.onEvent(App.context, "允许开机自启用户-1");
        } else {
            editor.putBoolean("isBanned", false);
            editor.commit();
            Toast.makeText(App.context, (String) getResources().getText(R.string.have_allowed_boot_start), Toast.LENGTH_SHORT).show();
            AVAnalytics.onEvent(App.context, "允许开机自启用户+1");
        }
    }

    public void allowStatics(View view) {
        boolean isChecked = ((Switch) view).isChecked();
        staticsButtonOnClicked(!isChecked);
    }

    public void staticsButtonOnClicked(boolean allow) {
        editor.putBoolean("allow_statistics", allow);
        editor.commit();
        if (allow) {
            Toast.makeText(App.context, (String) getResources().getText(R.string.have_allowed_statistics), Toast.LENGTH_SHORT).show();
            AVObject Like = new AVObject("AllowData");
            Like.put("hello", "x");
            Like.saveInBackground();
        } else {
            Toast.makeText(App.context, (String) getResources().getText(R.string.have_prohibit_statistics), Toast.LENGTH_SHORT).show();
            AVObject Like = new AVObject("ProhibitsData");
            Like.put("hello", "x");
            Like.saveInBackground();
        }
    }

    /**
     * ListView事件响应：浏览器中打开github项目
     *
     * @param view
     */
    public void linkGithub(View view) {
        String githubURL = "https://github.com/padeoe/AutoConnect";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(githubURL));
        startActivity(intent);
    }

    /**
     * 下载
     */
    public void downloadNewVersionApp() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
                Log.i("权限", "解释权限");
                FragmentManager fm = MainActivity.this.getFragmentManager();
                new ExplainPermissionFragment().show(fm, "s");

            }
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant

        } else {
            downloadNow();
        }
    }

    /**
     * 获得权限后下载文件
     */
    private void downloadNow() {
        DownloadManager downloadManager = (DownloadManager) App.context.getSystemService(App.context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(checkUpdateFragment.url));
        String apkName = "AutoConnect-" + checkUpdateFragment.newVersionName + ".apk";
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkName);
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File newApk = new File(path, apkName);
        if (newApk.exists()) {
            newApk.delete();
        }
        long downloadId = downloadManager.enqueue(request);
    }

    /**
     * {@link CheckUpdateFragment}请求下载后在{@link MainActivity}中请求权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.i("请求权限", "requestCode" + requestCode);
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadNow();

                } else {

                }
                return;
            }
        }
    }

    /**
     * 检查更新
     */
    public void checkUpdate() {
        int loaclSdkVersion = Build.VERSION.SDK_INT;
        String apkminAPI = "14";
        if (Build.VERSION.SDK_INT >= 23) {
            apkminAPI = "23";
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                apkminAPI = "21";
            } else {
                apkminAPI = "14";
            }
        }
        AVQuery<AVObject> query = new AVQuery<>("NewestVersion");
        query.orderByDescending("updatedAt");
        query.whereEqualTo("minSDK", apkminAPI);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                    AVObject newestVersionObject = avObjects.get(0);
                    String url = newestVersionObject.getString("url");
                    String apkSize = newestVersionObject.getString("size");
                    String newVersionName = newestVersionObject.getString("versionName");
                    if (url != null && apkSize != null && newVersionName != null) {
                        String installedVersionName = CheckUpdateFragment.getInstalledVersion();
                        if (installedVersionName != null) {
                            if (!installedVersionName.equals(newVersionName)) {
                                checkUpdateFragment = new CheckUpdateFragment();
                                checkUpdateFragment.showDownloadDialog(url, newVersionName, installedVersionName, apkSize, MainActivity.this.getFragmentManager());
                            } else {
                                Log.i("检查更新", (String) App.context.getResources().getText(R.string.isNewestVersion) + installedVersionName);
                                Toast.makeText(App.context, (String) App.context.getResources().getText(R.string.isNewestVersion) + installedVersionName, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(App.context, App.context.getString(R.string.get_local_version_error), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(App.context, App.context.getString(R.string.check_update_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });
    }

};

