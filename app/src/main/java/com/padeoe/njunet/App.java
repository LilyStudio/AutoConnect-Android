package com.padeoe.njunet;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.padeoe.nicservice.njuwlan.service.LoginService;
import com.padeoe.nicservice.njuwlan.service.OfflineQueryService;
import com.padeoe.njunet.util.PrefFileManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by padeoe on 2016/4/21.
 */
public class App extends Application {
    public static Context context;
    private static Boolean showNotification = null;
    private static Set<String> portalWiFiSSIDSet = null;
    private static Set<String> suspiciousWiFiSSIDSet = null;
    private static int maxTry = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LoginService loginService = LoginService.getInstance();
        loginService.setTimeout(Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).getString("timeout_portal", "200")));
        String portal_ip = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).getString("portal_server", "").trim();

        loginService.setSettingsPortalIP(portal_ip.equals("") ? null : portal_ip);

        String bras_ip = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).getString("bras_server", "").trim();
        OfflineQueryService.setSettingsBrasIP(bras_ip.equals("") ? null : bras_ip);
    }

    public static Context getAppContext() {
        return context;
    }

    public static Boolean getShowNotification() {
        return showNotification != null ? showNotification : (showNotification = PrefFileManager.getAccountPref().getBoolean("showNotification", true));
    }

    public static void setShowNotification(Boolean showNotification) {
        PrefFileManager.getAccountPref().edit().putBoolean("showNotification", App.showNotification = showNotification).apply();
    }

    private static Set<String> getPortalWiFiSSIDSet() {
        return portalWiFiSSIDSet != null ? portalWiFiSSIDSet : (portalWiFiSSIDSet = PrefFileManager.getWiFiPref().getStringSet("portalWiFiSSIDSet", new HashSet<String>()));
    }

    public static void addWiFiSSID(String SSID) {
        if (getPortalWiFiSSIDSet().add(SSID)) {
            PrefFileManager.getWiFiPref().edit().putStringSet("portalWiFiSSIDSet", portalWiFiSSIDSet).apply();
            if (getSuspiciousWiFiSSIDSet().remove(SSID))
                PrefFileManager.getWiFiPref().edit().putStringSet("suspiciousWiFiSSIDSet", suspiciousWiFiSSIDSet).apply();
        }
    }

    public static void removeWiFiSSID(String SSID) {
        if (getPortalWiFiSSIDSet().remove(SSID)) {
            PrefFileManager.getWiFiPref().edit().putStringSet("portalWiFiSSIDSet", portalWiFiSSIDSet).apply();
            if (getSuspiciousWiFiSSIDSet().add(SSID))
                PrefFileManager.getWiFiPref().edit().putStringSet("suspiciousWiFiSSIDSet", suspiciousWiFiSSIDSet).apply();
        }
    }

    private static Set<String> getSuspiciousWiFiSSIDSet() {
        return suspiciousWiFiSSIDSet != null ? suspiciousWiFiSSIDSet : (suspiciousWiFiSSIDSet = PrefFileManager.getWiFiPref().getStringSet("suspiciousWiFiSSIDSet", new HashSet<String>()));
    }

    public static boolean isInPortalWiFiSet(String SSID) {
        return getPortalWiFiSSIDSet().contains(SSID);
    }

    public static boolean isInSuspiciousWiFiSSIDSet(String SSID) {
        return getSuspiciousWiFiSSIDSet().contains(SSID);
    }

    public static int getMaxTry() {
        return maxTry == -1 ? Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString("times_relogin", "4")) : maxTry;
    }

    public static void setMaxTry(int maxTry) {
        App.maxTry = maxTry;
    }
}
