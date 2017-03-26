package com.padeoe.njunet.connect.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.padeoe.nicservice.njuwlan.object.bras.list.OnlineBras;
import com.padeoe.nicservice.njuwlan.object.portal.BasicInfo;
import com.padeoe.nicservice.njuwlan.object.portal.ReturnData;
import com.padeoe.nicservice.njuwlan.object.portal.row.BasicInfoRow;
import com.padeoe.nicservice.njuwlan.service.LoginService;
import com.padeoe.nicservice.njuwlan.service.OfflineQueryService;
import com.padeoe.nicservice.njuwlan.service.OnlineQueryService;
import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;
import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.uihandler.AuthFailHandle;
import com.padeoe.njunet.connect.uihandler.BackgrReturnDataHandle;
import com.padeoe.njunet.connect.uihandler.ClientLimitHandle;
import com.padeoe.njunet.connect.uihandler.ConnectFailHandle;
import com.padeoe.njunet.connect.uihandler.ConnectResultHandle;
import com.padeoe.njunet.connect.uihandler.ErrorHandle;
import com.padeoe.njunet.connect.uihandler.NoPasswordHandle;
import com.padeoe.njunet.connect.uihandler.OfflineHandle;
import com.padeoe.njunet.connect.uihandler.OnlineTimeHandle;
import com.padeoe.njunet.connect.uihandler.Reachable;
import com.padeoe.njunet.connect.uihandler.ReturnDataHandle;
import com.padeoe.njunet.connect.uihandler.UnknownNetHandle;
import com.padeoe.njunet.connect.uihandler.WifiLostHandle;
import com.padeoe.njunet.util.MyObservable;
import com.padeoe.njunet.util.NetworkTest;
import com.padeoe.utils.LoginException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by padeoe on 2016/4/26.
 */
public class ConnectManager extends MyObservable<ConnectResultHandle> {
    public static final String BACKGROUND_LOGIN_ACTION = "com.padeoe.njunet.BACKGROUND_LOGIN_ACTION";
    public static final String WIFI_LOST_ACTION = "com.padeoe.njunet.WIFI_LOST_ACTION";
    public static final String UNKNOWNNET_ACTION = "com.padeoe.njunet.UNKNOWNNET_ACTION";
    public static final String AUTHFAIL_ACTION = "com.padeoe.njunet.AUTHFAIL_ACTION";
    public static final String WIFI_AVAILABLE_ACTION = "com.padeoe.njunet.WIFI_AVAILABLE_ACTION";
    public static List<Thread> threadList = Collections.synchronizedList(new ArrayList<Thread>());

    /*用于控制上一个连接没有结束时不再发起新的连接*/
    static AtomicBoolean isConnecting = new AtomicBoolean(false);

    public static Status getStatus() {
        return status;
    }

    public static void setStatus(Status status) {
        ConnectManager.status = status;
    }

    public enum Status {ONLINE, OFFLINE, WIFI_LOST, DETECTING,REMOTE_ONLINE}

    private static Status status = Status.OFFLINE;

    public void disconnectNow() {
        Intent stopServiceIntent = new Intent(App.getAppContext(), ConnectService.class);
        stopServiceIntent.setAction(ConnectService.STOP_SERVICE_ACTION);
        App.getAppContext().startService(stopServiceIntent);
        if (isConnecting.compareAndSet(false, true)) {
            Thread thread;
            threadList.add(thread = new Thread() {
                @Override
                public void run() {
                    // bindNetWork(null);
                    String result;
                    try {
                        result = LoginService.getInstance().disconnect();
                        isConnecting.set(false);
                        ReturnData returnData = ReturnData.getFromJson(result);
                        setChanged();
                        notifyObservers(returnData == null ? new ErrorHandle(result) : new OfflineHandle(returnData));
                        threadList.remove(this);
                    } catch (IOException e) {
                        isConnecting.set(false);
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
        }
    }

    public void backgrConnect() {
        if (ConnectService.isCheckBeforeLogin()) {
            checkOnline();
        } else {
            login();
        }
    }

    //检查是否有其他设备在线，当前只处理学生标准及时服务
    public void checkOnline() {
        if (isConnecting.compareAndSet(false, true)) {
            Thread thread;
            threadList.add(thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    if (bindNetWork()) {
                        System.out.println("即将检查在线设备");
                        try {
                            //首先查询当前设备是否已登录，如果已登录，则放弃登陆操作并刷新数据。如果未登录，查询其他设备是否登陆
                            OnlineQueryService onlineQueryService = new OnlineQueryService();
                            String result = onlineQueryService.getCurrentUserInfo();
                            //如果已登录，则放弃登陆操作并刷新数据
                            if (OnlineQueryService.isPortalOnline(result)) {
                                isConnecting.set(false);
                                setChanged();
                                notifyObservers(new BackgrReturnDataHandle(result));
                                return;
                            } else {
                                //当前设备没有登录，则查询其他设备是否登陆
                                OfflineQueryService offlineQueryService = null;
                                offlineQueryService = new OfflineQueryService(ConnectService.getUsername(), ConnectService.getPassword());
                                OnlineBras onlineBras = OnlineBras.getFromJson(offlineQueryService.getOnline(1, 20));
                                isConnecting.set(false);
                                if (onlineBras != null) {
                                    if (onlineBras.getOnlineRowBrases()==null) {
                                        System.out.println("当前没有设备在线");
                                        login();
                                    } else {
                                        System.out.println("当前已有其他设备在线");
                                        setChanged();
                                        notifyObservers(new ClientLimitHandle());
                                    }
                                }
                                else{
                                    System.out.println("查询失败");
                                    setChanged();
                                    notifyObservers(new ErrorHandle(App.getAppContext().getResources().getString(R.string.connect_fail)));
                                }
                            }
                        } catch (LoginException e) {
                            e.printStackTrace();
                            setChanged();
                            Log.e("bras登陆错误", e.getMessage());
                            notifyObservers(new ErrorHandle(e.getMessage()));
                        } catch (IOException io) {
                            isConnecting.set(false);
                            io.printStackTrace();
                            setChanged();
                            notifyObservers(new ErrorHandle(io.getMessage()));
                        }


                    } else {
                        isConnecting.set(false);
                        onWiFiLost();
                    }
                    threadList.remove(this);
                }
            });
            thread.start();
        }
    }

    public void login() {
        login(null);
    }

    public void login(final ConnectFailHandle connectFailHandle) {

        if (isConnecting.compareAndSet(false, true)) {
            Thread thread;
            threadList.add(thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    if (bindNetWork()) {
                        if (ConnectService.getUsername() == null || ConnectService.getPassword() == null) {
                            isConnecting.set(false);
                            setChanged();
                            notifyObservers(new NoPasswordHandle());
                        } else {
                            String result = null;
                            try {
                                result = LoginService.getInstance().oldConnect(ConnectService.getUsername(), ConnectService.getPassword());
                                isConnecting.set(false);
                                System.out.println("后台登陆结束" + result);
                                setChanged();
                                saveCurrentSSID();
                                if (LoginService.isLoginSuccess(result)) {
                                    notifyObservers(new BackgrReturnDataHandle(result));
                                } else {
                                    ReturnData returnData = ReturnData.getFromJson(result);
                                    notifyObservers(returnData == null ? new ErrorHandle(App.getAppContext().getResources().getString(R.string.connect_fail)) : new AuthFailHandle(returnData));
                                }
                            } catch (IOException e) {
                                isConnecting.set(false);
                                System.out.println("后台登陆结束" + result);
                                setChanged();
                                e.printStackTrace();
                                removeCurrentSSID();
                                if (connectFailHandle == null) {
                                    ConnectFailHandle newConnectFailHandle = new ConnectFailHandle(ConnectManager.this, 1);
                                    newConnectFailHandle.handle();
                                } else {
                                    connectFailHandle.handle();
                                    // notifyObservers(connectFailHandle);
                                }
                            }
                        }

                    } else {
                        onWiFiLost();
                    }
                    threadList.remove(this);
                }
            });
            thread.start();
        } else {
            System.out.println("已有进程在连接");
        }

    }

    public void networkTest() {
        Thread thread;
        threadList.add(thread =
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        if (bindNetWork()) {
                            try {
                                int code = NetworkTest.testNetwork("http://" + NetworkUtils.DNS("padeoe.com", 200) + "/generate_204");
                                setChanged();
                                notifyObservers(new Reachable(code));
                            } catch (Exception e) {
                                setChanged();
                                notifyObservers(new Reachable(-1));
                            }
                        } else {
                            onWiFiLost();
                        }
                        threadList.remove(this);
                    }
                });
        thread.start();
    }


    /**
     * 绑定wifi网络
     *
     * @param network
     */
    public static boolean bindNetWork(Network network) {
        if (network == null)
            return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            connectivityManager.bindProcessToNetwork(network);
            return true;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            connectivityManager.setProcessDefaultNetwork(network);
            return true;
        }
        Log.e("内部代码错误", "API14无需网络绑定");
        return false;
    }

    public static boolean bindNetWork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= 21) {
            Network wifi = null;
            Network vpn = null;
            for (Network network : connectivityManager.getAllNetworks()) {
                if (connectivityManager.getNetworkInfo(network).getType() == ConnectivityManager.TYPE_VPN) {
                    vpn = network;
                }
                if (connectivityManager.getNetworkInfo(network).getType() == ConnectivityManager.TYPE_WIFI) {
                    wifi = network;
                }
            }
            return wifi == null ? false : bindNetWork(vpn == null ? wifi : vpn);
        } else {
            NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return mWifi.isConnected();
        }
    }


    private static void saveCurrentSSID() {
        WifiManager mWifi = (WifiManager) App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        App.addWiFiSSID(wifiInfo.getSSID());
    }

    private static void removeCurrentSSID() {
        WifiManager mWifi = (WifiManager) App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        App.removeWiFiSSID(wifiInfo.getSSID());
    }

    public static void stopAllConnect() {
        //暂时不进行线程中断，以下语句是否注释不影响运行
/*        for (Thread thread : threadList) {
            thread.interrupt();
        }*/
    }

    public static String getInternetStatus() {
        switch (status) {
            case OFFLINE:
                return App.getAppContext().getResources().getString(R.string.offline);
            case ONLINE:
                return App.getAppContext().getResources().getString(R.string.online);
            case DETECTING:
                return App.getAppContext().getResources().getString(R.string.detecting);
            case WIFI_LOST:
                return App.getAppContext().getResources().getString(R.string.wifi_lost);
            default:
                return "unknown";
        }
    }

    private static void onWiFiLost() {
        ConnectManager.setStatus(ConnectManager.Status.WIFI_LOST);
        StatusNotificationManager.showStatus("未连接网络");
        Log.e("没有网络", "不应该进行连接操作");
        isConnecting.set(false);
    }

    public static int getColor(ConnectManager.Status status) {
        switch (status) {
            case ONLINE:
                return App.getAppContext().getResources().getColor(R.color.colorPrimary);
            case OFFLINE:
                return App.getAppContext().getResources().getColor(R.color.disconnect);
            case WIFI_LOST:
                return App.getAppContext().getResources().getColor(R.color.colorPrimary);
            case DETECTING:
                return App.getAppContext().getResources().getColor(R.color.colorPrimary);
            case REMOTE_ONLINE:
                return App.getAppContext().getResources().getColor(R.color.remote_online);
        }
        return App.getAppContext().getResources().getColor(R.color.colorPrimary);
    }
}
