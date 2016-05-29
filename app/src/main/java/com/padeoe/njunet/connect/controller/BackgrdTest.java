package com.padeoe.njunet.connect.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.padeoe.njunet.App;
import com.padeoe.njunet.connect.uihandler.Reachable;
import com.padeoe.njunet.util.MyObservable;
import com.padeoe.njunet.util.MyObserver;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by padeoe on 2016/5/18.
 */
public class BackgrdTest implements MyObserver<Reachable> {
    /*用于控制上一个连接没有结束时不再发起新的连接*/
    static AtomicBoolean isRunning = new AtomicBoolean(false);
    public BackgrdTest(){}
    public void start(){
        if(isRunning.compareAndSet(false,true)){
            System.out.println("正在定义闹钟");
            Intent intent = new Intent(App.getAppContext(), ConnectService.class);
            intent.setAction(ConnectService.CONNECTY_TEST);
            // Create a PendingIntent to be triggered when the alarm goes off
            final PendingIntent pIntent = PendingIntent.getService(App.getAppContext(),0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) App.getAppContext().getSystemService(Context.ALARM_SERVICE);
            // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
            // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
            alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis()+2000,
                    10000, pIntent);
        }

    }
    public void end(){
        if(isRunning.compareAndSet(true,false)){
            Intent intent = new Intent(App.getAppContext(), ConnectService.class);
            intent.setAction(ConnectService.CONNECTY_TEST);
            final PendingIntent pIntent = PendingIntent.getService(App.getAppContext(),0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) App.getAppContext().getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pIntent);
        }

    }

    public void startTest(){
        ConnectManager connectManager=new ConnectManager();
        connectManager.addObserver(this);
        connectManager.networkTest();
    }

    @Override
    public void update(MyObservable myObservable, Reachable data) {
        //更改全局状态
        ConnectManager.setStatus(data.isInternetAvailable()?ConnectManager.Status.ONLINE:ConnectManager.Status.OFFLINE);
        //发送广播，通知其他界面更新信息
        Intent intent = new Intent(ConnectManager.NETWORK_TEST);
        intent.putExtra("NETWORK_TEST", data);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(App.getAppContext());
        broadcastManager.sendBroadcast(intent);
    }
}
