package com.padeoe.njunet.connect;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.padeoe.njunet.App;
import com.padeoe.njunet.R;
import com.padeoe.njunet.connect.controller.ConnectManager;
import com.padeoe.njunet.connect.controller.ConnectService;

/**
 * Created by padeoe on 2016/5/18.
 */
public class StatusNotificationManager {
    private static final int notificationID = 123456888;

    public static void showStatus() {
        showStatus(Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(App.getAppContext()).getString("notification_frequency", "3")));
    }

    public static void showStatus(int notification_frequency) {
        WifiManager mWifi = (WifiManager) App.getAppContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        boolean isNJUWLAN = App.isInPortalWiFiSet(wifiInfo.getSSID()) || App.isInSuspiciousWiFiSSIDSet(wifiInfo.getSSID());
        if (isNJUWLAN) {
            StatusNotificationManager.showStatus(App.getAppContext().getResources().getString(R.string.NJUWLAN), notification_frequency);
        } else {
            if (notification_frequency != 2) {
                StatusNotificationManager.showStatus(App.getAppContext().getResources().getString(R.string.Unknown_WLAN), notification_frequency);
            } else {
                removeNotification();
            }
        }
    }

    public static void showStatus(String detail) {
        showStatus(detail, Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(App.getAppContext()).getString("notification_frequency", "3")));
    }

    public static void showStatus(String detail, int notification_frequency) {
        if (notification_frequency != 1) {
            Intent loginIntent = new Intent(App.getAppContext(), ConnectService.class);
            loginIntent.setAction(ConnectService.SCAN_AND_CONNECT_ACTION);
            PendingIntent piDismiss = PendingIntent.getService(App.getAppContext(), 0, loginIntent, 0);
            android.support.v4.app.NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(App.getAppContext());
            builder
                    .setContentText(App.getAppContext().getResources().getString(R.string.pull_down_notification))
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setColor(0xff123456)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(detail));
            Intent resultIntent = new Intent(App.getAppContext(), MainActivity.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(App.getAppContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);
            switch (ConnectManager.getStatus()) {
                case OFFLINE:
                    builder.setColor(App.getAppContext().getResources().getColor(R.color.disconnect)).setContentTitle(App.getAppContext().getResources().getString(R.string.internet_unavailable)).setSmallIcon(R.drawable.icon_wifi_nointernet).addAction(R.drawable.ic_settings,
                            App.getAppContext().getResources().getString(R.string.login), piDismiss);
                    break;
                case ONLINE:
                    builder.setColor(App.getAppContext().getResources().getColor(R.color.colorPrimary)).setContentTitle(App.getAppContext().getResources().getString(R.string.internet_available)).setSmallIcon(R.drawable.ic_wifi_internet).addAction(R.drawable.ic_settings,
                            App.getAppContext().getResources().getString(R.string.refresh), piDismiss);
                    break;
                case WIFI_LOST:
                    if (notification_frequency == 2) {
                        removeNotification();
                        return;
                    }
                    builder.setColor(App.getAppContext().getResources().getColor(R.color.disconnect)).setContentTitle(App.getAppContext().getResources().getString(R.string.no_wifi)).setSmallIcon(R.drawable.ic_wifi_off).addAction(R.drawable.ic_settings,
                            App.getAppContext().getResources().getString(R.string.scan_and_connect), piDismiss);
                    break;
                case DETECTING:
                    builder.setColor(App.getAppContext().getResources().getColor(R.color.disconnect)).setContentTitle(App.getAppContext().getResources().getString(R.string.checking)).setSmallIcon(R.drawable.icon_wifi_nointernet).addAction(R.drawable.ic_settings,
                            App.getAppContext().getResources().getString(R.string.refresh), piDismiss);
                case REMOTE_ONLINE:
                    builder.setColor(App.getAppContext().getResources().getColor(R.color.remote_online)).setContentTitle(App.getAppContext().getResources().getString(R.string.remote_online)).setSmallIcon(R.drawable.icon_wifi_nointernet).addAction(R.drawable.ic_settings,
                            App.getAppContext().getResources().getString(R.string.force_login), piDismiss);
                    break;
            }
            builder.setOngoing(notification_frequency == 3);
            NotificationManager mNotificationManager =
                    (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(
                    notificationID,
                    builder.build());
        }
    }

    public static void removeNotification() {
        NotificationManager mNotificationManager =
                (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notificationID);
    }
}
