package com.padeoe.autoconnect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MainActivity extends Activity{
    public static boolean allowed = true;
    EditText usernameEdit;
    EditText passwordEdit;
    SharedPreferences.Editor editor = null;
    public static Context ctx;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //添加LeanCloud用户统计分析，下面一行代码中的key仅用于测试，发布的apk中使用的不同
        AVOSCloud.initialize(this, "rfdbmj8hpdbo3dwx2unrqmvhfb2y8r6d3xrsaiwwoewr2bc4", "c6n60q7onyffn97vey1jywk3bje590xlntp8ddasdo0hnvcy");
        //  AVOSCloud.initialize(this, "pq3sqjul4anoev3fhxc99736s72jl6w0euuovi0tzfy35src", "i9mnvkzb53btg8nk22bmthraxwsfq71jdbatas5tueaggznj");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        //获取toolbar对象，设置为ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        this.setSupportActionBar(toolbar);*/
        //  this.getActionBar().bac
        ctx = MainActivity.this;
        sharedPreferences = ctx.getSharedPreferences("DateFile", MODE_PRIVATE);
        //获取现有配置
        Display display = getWindowManager().getDefaultDisplay(); //Activity#getWindowManager()
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.i("width",String.valueOf(width));
        Log.i("height",String.valueOf(height));

        editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        String PostData = sharedPreferences.getString("PostData", null);
        boolean isAllowed = sharedPreferences.getBoolean("isBanned", true);
        boolean test = sharedPreferences.getBoolean("test", false);
        System.out.println(test);
        //显示现有配置
        usernameEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);
        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setChecked(isAllowed);
        if (username != null) usernameEdit.setText(username);
        if (password != null) passwordEdit.setText(password);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    editor.putBoolean("isBanned", true);
                    editor.commit();
                    Toast.makeText(ctx, (String) getResources().getText(R.string.have_prohibited_boot_start), Toast.LENGTH_SHORT).show();
                    AVAnalytics.onEvent(ctx, "允许开机自启用户-1");
                    allowed = true;
                } else {
                    editor.putBoolean("isBanned", false);
                    editor.commit();
                    Toast.makeText(ctx, (String) getResources().getText(R.string.have_allowed_boot_start), Toast.LENGTH_SHORT).show();
                    AVAnalytics.onEvent(ctx, "允许开机自启用户+1");
                    allowed = false;
                }
            }
        });
        if (PostData != null) {
            this.startService(new Intent(this, WiFiDetectService.class));
        }

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[]{"在浏览器中打开p.nju.edu.cn", "github项目链接"};

        final ArrayList<String> list = new ArrayList<String>();
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
                    case 1:
                        String url2 = "https://github.com/padeoe/AutoConnect";
                        Intent i2 = new Intent(Intent.ACTION_VIEW);
                        i2.setData(Uri.parse(url2));
                        startActivity(i2);
                        break;

                }
            }

        });

//


        boolean isFirstInstall = sharedPreferences.getBoolean("isFirstInstall", true);
        //do something
        if (isFirstInstall) {
            editor.putBoolean("isFirstInstall", false);
            editor.commit();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    editor.putBoolean("allow_statistics", true);
                    editor.commit();
                    AVAnalytics.onEvent(ctx, "启用数据统计");
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    editor.putBoolean("allow_statistics", false);
                    editor.commit();
                    AVAnalytics.onEvent(ctx, "禁用数据统计");
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
                try {
                    switch (id) {
                        case R.id.storePreference:
                            storeInfo();
                            break;
                        case R.id.connect:
                            connectNow();
                            break;
                        case R.id.disconnect:
                            disconnectNow();
                            break;
                        case R.id.setting:
                            break;
                        case R.id.about:
/*                            FragmentManager fm = Activity.getFragmentManager();
                            new MyDialogFragment().show(fm,"s");*/
                            break;
                        default:

                    }
                } catch (Exception e) {
                    System.out.println(e);
                    Log.getStackTraceString(e);
                    ShowOnMainActivity((String) getResources().getText(R.string.connect_fail));
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
            ShowOnMainActivity(Authenticate.disconnect());
            if (sharedPreferences.getBoolean("allow_statistics", false)) {
                AVAnalytics.onEvent(ctx, "立即注销NJU-WLAN");
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
                //    final String PostData = "";
                final String PostData = "action=login&username=" + username + "&password=" + password;
                ShowOnMainActivity(Authenticate.connect(PostData));
                if (sharedPreferences.getBoolean("allow_statistics", false)) {
                    AVAnalytics.onEvent(ctx, "立即登陆NJU-WLAN");
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
        editor.putBoolean("test", true);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
        if (username.length() > 0 && password.length() > 0) {
            String PostData = "action=login&username=" + username + "&password=" + password;
            editor.putString("PostData", PostData);
            editor.commit();
            ctx.startService(new Intent(ctx, WiFiDetectService.class));
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
                    Toast.makeText(ctx, string2, Toast.LENGTH_SHORT).show();
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
}