package com.padeoe.njunet.connect;

import android.Manifest;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.padeoe.njunet.App;

import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.controller.ConnectManager;
import com.padeoe.njunet.connect.controller.ConnectService;
import com.padeoe.njunet.connect.uihandler.ConnectResultHandle;
import com.padeoe.njunet.deploy.FirstSettingActivity;
import com.padeoe.njunet.settings.MySettingsActivity;
import com.padeoe.njunet.util.MyAnimation;
import com.padeoe.njunet.util.MyObservable;
import com.padeoe.njunet.util.MyObserver;
import com.padeoe.njunet.util.PrefFileManager;

public class MainActivity extends AppCompatActivity implements MyObserver<ConnectResultHandle>, PermissionExplainFragment.PermissionHandler {
    public ConnectManager.Status status = ConnectManager.Status.ONLINE;
    public View online;
    public View offline;
    public TextView netinfo;
    public TextView amount;
    public TextView time;
    public TextView location;
    public TextView ip;
    public ProgressBar progressBar;
    public AppBarLayout appBarLayout;
    BroadcastReceiver receiver;
    public TextView status_internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        //开启后台连接服务
        if (PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).getBoolean("auto_connect", true)) {
            startService(new Intent(this, ConnectService.class));
        }
        //开启状态栏通知
        if (App.getShowNotification()) {
            StatusNotificationManager.showStatus();
        }
        //注册应用内后台连接的广播，接收到广播后更新界面
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("自定义广播", "已经接收到广播" + intent.getAction());
                ConnectResultHandle infoHandle = null;
                switch (intent.getAction()) {
                    case ConnectManager.BACKGROUND_LOGIN_ACTION:
                        infoHandle = intent.getParcelableExtra("LOGIN_RESULT");
                        break;
                    case ConnectManager.WIFI_LOST_ACTION:
                        infoHandle = intent.getParcelableExtra("WIFI_LOST");
                        break;
                    case ConnectManager.UNKNOWNNET_ACTION:
                        infoHandle = intent.getParcelableExtra("NETWORK_INFO");
                        break;
                    case ConnectManager.AUTHFAIL_ACTION:
                        infoHandle = intent.getParcelableExtra("AUTH_ERROR");
                        break;
                    case ConnectManager.WIFI_AVAILABLE_ACTION:
                        infoHandle = intent.getParcelableExtra("NETINFO");
                        break;
                    default:
                        Log.e("unknown action", intent.getAction());
                }
                if (infoHandle != null) {
                    infoHandle.updateView(MainActivity.this);
                }
            }
        };
        if (PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).getBoolean("login_on_activity_start", true)) {
            initData();
        }





/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
       //     builder.addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN);
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
            NetworkRequest wifiNetworkRequest = builder.build();
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerNetworkCallback(wifiNetworkRequest, new WifiNetWorkCallBack());
            Log.d("已经开启了网络请求回调","已经开启了网络请求回调");
        }*/
/*
        Intent intent = getIntent();
        if (ConnectivityManager.ACTION_CAPTIVE_PORTAL_SIGN_IN.equals(intent.getAction())) {
            Log.d("captivePortal","存在captivePortal的intent");
            CaptivePortal captivePortal=intent.getParcelableExtra(ConnectivityManager.EXTRA_CAPTIVE_PORTAL);
            Network network=intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK);
            if(captivePortal!=null){
                Log.e("captivePortal不为空","captivePortal不为空");
            }else {
                Log.e("captivePortal是空的","captivePortal为空");
            }
            if(network!=null){
                Log.d("网络的具体信息","网络的具体信息存在");
                ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
             //   connectivityManager.bindProcessToNetwork(network);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        ConnectManager connectManager = new ConnectManager();
                        connectManager.addObserver(MainActivity.this);
                        connectManager.connectNow();
                    }
                }.start();
            }
            else{
                Log.e("网络的具体信息不存在","网络的具体信息不存在");
            }
        }*/

    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                ConnectManager connectManager = new ConnectManager();
                connectManager.addObserver(MainActivity.this);
                Log.d("login request", "triggered by button in MainActivity");
                connectManager.backgrConnect();
            }
        });

        online = findViewById(R.id.status_iamge);
        offline = findViewById(R.id.status_image_noconnection);
        netinfo = (TextView) findViewById(R.id.netinfo);
        amount = (TextView) findViewById(R.id.amount);
        time = (TextView) findViewById(R.id.time);
        location = (TextView) findViewById(R.id.location);
        ip = (TextView) findViewById(R.id.ip);
        status_internet = (TextView) findViewById(R.id.status_internet);
        WifiManager wifiManager = (WifiManager) App.getAppContext().getSystemService(Context.WIFI_SERVICE);
        setNetInfo(wifiManager.getConnectionInfo().getSSID());
    }

    public void setNetInfo(String ssid) {
        netinfo.setText(getNetInfo(ssid));
    }

    ;

    private static SpannableString getNetInfo(String ssid) {
        if (ssid == null) {
            return new SpannableString("未连接");
        } else {
            StringBuilder builder = new StringBuilder();
            String type = App.isInPortalWiFiSet(ssid) || App.isInSuspiciousWiFiSSIDSet(ssid) ? "校园网" : "未知网络";
            builder.append(type).append("\n").append(ssid);
            String status_internet_str = builder.toString();
            SpannableString spannableString = new SpannableString(status_internet_str);
            spannableString.setSpan(new StyleSpan(Typeface.ITALIC), status_internet_str.indexOf(ssid), status_internet_str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(0.7f), status_internet_str.indexOf(ssid), status_internet_str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
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
        //BackgrdInfoHandleaction bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.disconnect) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                appBarLayout.setElevation(0f);
            }
            progressBar.setVisibility(View.VISIBLE);
            ConnectManager connectManager = new ConnectManager();
            connectManager.addObserver(MainActivity.this);
            connectManager.disconnectNow();
            return true;
        }
        if (id == R.id.settings) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MySettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 在MainActivity显示内容为string的Toast
     *
     * @param string 要显示的字符
     */
    public void showOnMainActivity(String string) {
        final String string2 = string;
        try {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(App.context, string2, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("showOnMainActivity", "Toast显示失败");
        }
    }

    public void updateViewStatus(ConnectManager.Status oldStatus, ConnectManager.Status newStatus) {
        System.out.println(oldStatus + "=>" + newStatus);
        status_internet.setText(ConnectManager.getInternetStatus());
        MyAnimation.fadeInTextView(status_internet);
        if (getStatusView(oldStatus) != getStatusView(newStatus)) {
            System.out.println("当前状态不一致");
            MyAnimation.flip(getStatusView(oldStatus), getStatusView(newStatus), 300);
            if (getStatusView(newStatus) == offline) {
                MyAnimation.updateBackground(appBarLayout, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.disconnect), 1000);
            } else {
                MyAnimation.updateBackground(appBarLayout, getResources().getColor(R.color.disconnect), getResources().getColor(R.color.colorPrimary), 1000);
            }
            this.status = newStatus;
        }
    }

    public View getStatusView(ConnectManager.Status status) {
        switch (status) {
            case ONLINE:
                return online;
            case OFFLINE:
                return offline;
            case WIFI_LOST:
                return offline;
            case DETECTING:
                return offline;
        }
        return null;
    }


    public void initData() {
        showProgress();
        ConnectManager connectManager = new ConnectManager();
        connectManager.addObserver(this);
        connectManager.backgrConnect();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectManager.BACKGROUND_LOGIN_ACTION);
        filter.addAction(ConnectManager.WIFI_LOST_ACTION);
        filter.addAction(ConnectManager.UNKNOWNNET_ACTION);
        filter.addAction(ConnectManager.WIFI_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver), filter);
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void update(MyObservable myObservable, final ConnectResultHandle data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "更新");
                data.updateView(MainActivity.this);
            }
        });
    }

    public void showProgress() {
        Log.e("开始显示progress", "progress");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setElevation(0f);
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        Log.e("开始隐藏progress", "progress");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setElevation(5f);
        }
        progressBar.setVisibility(View.GONE);
    }

    public void checkPermission() {
        if (/*ContextCompat.checkSelfPermission(App.getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED*/false) {
            System.out.println("权限已经获得");
        } else {
            PermissionExplainFragment permissionExplainFragment = new PermissionExplainFragment();
            FragmentManager fragmentManager = this.getFragmentManager();
            permissionExplainFragment.show(fragmentManager, "请求定位权限");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showOnMainActivity("权限已经获得");
        } else {
            showOnMainActivity("权限没有获得");
        }
    }

    @Override
    public void onPermissionExplained() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }
}
