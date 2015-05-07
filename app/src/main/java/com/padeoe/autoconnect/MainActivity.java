package com.padeoe.autoconnect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity {
    public static boolean allowed = true;
    EditText usernameEdit;
    EditText passwordEdit;
    SharedPreferences.Editor editor = null;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = MainActivity.this;
        //获取现有配置
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("DateFile", MODE_PRIVATE);
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
                    Toast.makeText(ctx, "已禁止开机自启", Toast.LENGTH_SHORT).show();
                    allowed = true;
                } else {
                    editor.putBoolean("isBanned", false);
                    editor.commit();
                    Toast.makeText(ctx, "已允许开机自启", Toast.LENGTH_SHORT).show();
                    allowed = false;
                }
            }
        });
        if (PostData != null) {
            this.startService(new Intent(this, WiFiDetectService.class));
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
        int id = item.getItemId();
        if (id == R.id.storePreference) {
            storeInfo();
            return true;
        }
        if (id == R.id.connect) {
            connectNow();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                final String PostData = "action=login&username=" + username + "&password=" + password;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String result = Authenticate.connectAndPost(PostData);
                            Gson gson = new Gson();
                            ReturnData ReturnData = gson.fromJson(result, ReturnData.class);
                            if (ReturnData != null) {
                                if (ReturnData.getUserinfo() != null) {
                                    final String userFullName = ReturnData.getUserinfo().getFullname();
                                    final String userName = ReturnData.getUserinfo().getUsername();
                                    ShowOnMainActivity(userFullName + userName + "\n" + ReturnData.getReply_message());
                                } else {//用户名密码错误
                                    final String reply_message = ReturnData.getReply_message();
                                    ShowOnMainActivity(reply_message);
                                }
                            } else {
                                Log.i("JSON", "gson解析返回null");
                                ShowOnMainActivity("登录失败gson解析返回null");
                            }
                        } catch (InterruptedException e) {
                            Log.getStackTraceString(e);
                            ShowOnMainActivity("登录失败");
                        }
                    }
                }.start();

            }
            //如果用户没有填写用户名密码
            else
                ShowOnMainActivity("没有连接WiFi");
        } else {
            Toast.makeText(ctx, "未填写", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ctx, "已保存", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(ctx, "未填写", Toast.LENGTH_SHORT).show();
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
     *
     * @return 返回值表示是否开启了无线网连接
     */
    public boolean isConnectedtoWiFi() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

}


