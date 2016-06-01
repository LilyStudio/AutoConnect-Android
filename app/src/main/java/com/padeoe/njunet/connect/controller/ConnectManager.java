package com.padeoe.njunet.connect.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.padeoe.nicservice.njuwlan.object.portal.BasicInfo;
import com.padeoe.nicservice.njuwlan.object.portal.ReturnData;
import com.padeoe.nicservice.njuwlan.object.portal.row.BasicInfoRow;
import com.padeoe.nicservice.njuwlan.service.LoginService;
import com.padeoe.nicservice.njuwlan.service.OnlineQueryService;
import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;
import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.StatusNotificationManager;
import com.padeoe.njunet.connect.uihandler.AuthFailHandle;
import com.padeoe.njunet.connect.uihandler.BackgrReturnDataHandle;
import com.padeoe.njunet.connect.uihandler.ConnectFailHandle;
import com.padeoe.njunet.connect.uihandler.ConnectResultHandle;
import com.padeoe.njunet.connect.uihandler.ErrorHandle;
import com.padeoe.njunet.connect.uihandler.NoPasswordHandle;
import com.padeoe.njunet.connect.uihandler.OfflineHandle;
import com.padeoe.njunet.connect.uihandler.OnlineTimeHandle;
import com.padeoe.njunet.connect.uihandler.Reachable;
import com.padeoe.njunet.connect.uihandler.ReturnDataHandle;
import com.padeoe.njunet.connect.uihandler.WifiLostHandle;
import com.padeoe.njunet.util.MyObservable;
import com.padeoe.njunet.util.NetworkTest;

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
    public static final String NOT_TARGET_ACTION = "com.padeoe.njunet.NOT_TARGET_ACTION";
    public static final String NETWORK_TEST = "com.padeoe.njunet.NETWORK_TEST_ACTION";
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

    public enum Status {ONLINE, OFFLINE, WIFI_LOST, DETECTING}

    private static Status status = Status.OFFLINE;

    public void disconnectNow() {
        if (isConnecting.compareAndSet(false, true)) {
            Thread thread;
            threadList.add(thread = new Thread() {
                @Override
                public void run() {
                    // bindNetWork(null);
                    String result = LoginService.getInstance().disconnect();
                    isConnecting.set(false);
                    ReturnData returnData = ReturnData.getFromJson(result);
                    setChanged();
                    notifyObservers(returnData == null ? new ErrorHandle(result) : new OfflineHandle(returnData));
                    threadList.remove(this);
                }
            });
            thread.start();
        }
    }

    public void backgrConnect() {
        backgrConnect(null);
    }

    public void backgrConnect(final ConnectFailHandle connectFailHandle) {
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
                            String result = LoginService.getInstance().connect(ConnectService.getUsername(), ConnectService.getPassword());
                            isConnecting.set(false);
                            System.out.println("后台登陆结束" + result);
                            setChanged();
                            if (LoginService.isLoginConnectSuccess(result)) {
                                saveCurrentSSID();
                                if (LoginService.isLoginSuccess(result)) {
                                    notifyObservers(new BackgrReturnDataHandle(result));
                                } else {
                                    notifyObservers(new AuthFailHandle(ReturnData.getFromJson(result)));
                                }
                            } else {
                                removeCurrentSSID();
                                if (connectFailHandle == null) {
                                    ConnectFailHandle newConnectFailHandle = new ConnectFailHandle(ConnectManager.this, 1);
                                    newConnectFailHandle.handle();
                                    //
                                } else {
                                    connectFailHandle.handle();
                                    // notifyObservers(connectFailHandle);
                                }

                            }
                        }

                    } else {
                        setChanged();
                        notifyObservers(new WifiLostHandle());
                        Log.e("没有网络", "不应该进行连接操作");
                        isConnecting.set(false);
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
                            ConnectManager.setStatus(ConnectManager.Status.WIFI_LOST);
                            StatusNotificationManager.showStatus("未连接网络");
                            Log.e("没有网络", "不应该进行连接操作");
                            isConnecting.set(false);
                        }
                        threadList.remove(this);
                    }
                });
        thread.start();
    }

    public void updateOnlineTime() {
        Thread thread;
        threadList.add(thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OnlineQueryService onlineQueryService = new OnlineQueryService();
                onlineQueryService.setTimeout(200);
                String result = onlineQueryService.getBasicInfo();
                BasicInfo basicInfo = BasicInfo.getFromJson(result);
                int seconds;
                if (basicInfo != null && basicInfo.getBasicInfoRows() != null && basicInfo.getBasicInfoRows().length > 0) {
                    BasicInfoRow basicInfoRow = basicInfo.getBasicInfoRows()[0];
                    try {
                        seconds = Integer.parseInt(basicInfoRow.getTotal_ipv4_volume());
                        setChanged();
                        notifyObservers(new OnlineTimeHandle(seconds));
                    } catch (NumberFormatException e) {
                        setChanged();
                        notifyObservers(new ErrorHandle(App.context.getString(R.string.illegal_result_from_server)));
                    }
                } else {
                    ConnectManager.setStatus(ConnectManager.Status.WIFI_LOST);
                    StatusNotificationManager.showStatus("未连接网络");
                    Log.e("没有网络", "不应该进行连接操作");
                    isConnecting.set(false);
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


    public static void saveCurrentSSID() {
        WifiManager mWifi = (WifiManager) App.getAppContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        App.addWiFiSSID(wifiInfo.getSSID());
    }

    public static void removeCurrentSSID() {
        WifiManager mWifi = (WifiManager) App.getAppContext().getSystemService(Context.WIFI_SERVICE);
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
}
