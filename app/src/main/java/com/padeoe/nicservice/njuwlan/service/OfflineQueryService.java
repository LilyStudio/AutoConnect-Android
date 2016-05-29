package com.padeoe.nicservice.njuwlan.service;

import com.padeoe.nicservice.njuwlan.object.bras.BrasIDInfo;
import com.padeoe.nicservice.njuwlan.object.bras.list.AuthLogBras;
import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;
import com.padeoe.utils.LoginException;

import java.util.Calendar;

/**
 * 该类实现了<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>的各类查询功能接口。
 * 包括查询用户信息，当前在线设备，详单信息，认证信息，充值信息，账单信息等。
 * @author padeoe
 * Date: 2015/9/24
 */
public class OfflineQueryService implements DetailQuery {
    private int timeout = 200;
    private String cookie = null;
    private String authkey = null;
    public static final int BASICINFO = 0;
    public static final int ONLINE = 1;
    public static final int AUTHLOG = 2;
    public static final int ACCT = 3;
    public static final int BILLS = 4;
    public static final int RECHARGE = 5;
    private static String cachedBrasIP = null;
    private static String settingsBrasIP = null;

    /**
     * 通过用户名密码构造
     * @param username 用户名:学号/工号
     * @param password <a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>的密码
     * @throws LoginException 登陆失败
     */
    public OfflineQueryService(String username, String password) throws LoginException {
        authkey = "username=" + username + "&password=" + password;
        resetCookie(authkey);
    }

    /**
     * 根据项目类别进行查询
     *
     * @param catalog 项目类别
     * @param page    当前页数
     * @param row     每页行数
     * @return 服务器返回的json格式的数据
     */
    public String queryBy(int catalog, int page, int row) {
        return queryBy(catalog, page, row, true);
    }

    /**
     * 根据项目类别进行查询
     *
     * @param catalog 项目类别
     * @param page    当前页数
     * @param row     每页行数
     * @param order   true时间由近及远,false由远及近
     * @return 服务器返回的json格式的数据
     */
    @Override
    public String queryBy(int catalog, int page, int row, boolean order) {
        switch (catalog) {
            case ONLINE:
                return getOnline(page, row);
            case AUTHLOG:
                return getAuthLog(page, row, order);
            case ACCT:
                return getAcct(page, row, order);
            case BILLS:
                return getBills(page, row, order);
            case RECHARGE:
                return getRecharge(page, row);
        }
        return null;
    }

    @Override
    public String getOnline(int page, int row) {
        return NetworkUtils.postWithCookie("page=" + page + "&rows=" + row, cookie, "http://" + getBrasIP() + ":8080/manage/self/online/getlist", timeout);
    }

    @Override
    public String getAuthLog(int page, int row, boolean order) {
        return NetworkUtils.postWithCookie("sort=datetime&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, cookie, "http://" + getBrasIP() + ":8080/manage/self/authlog/getlist", timeout);
    }

    public String getAuthLog(int page, int row) {
        return getAuthLog(page, row, true);
    }

    @Override
    public String getAcct(int page, int row, boolean order) {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return getAcct(currentMonth, page, row, order);
    }

    public String getAcct(int month, int page, int row, boolean order) {
        return NetworkUtils.postWithCookie("month=" + month + "&sort=acctstarttime&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, cookie, "http://" + getBrasIP() + ":8080/manage/self/detail/getlist", timeout);
    }

    public String getAcct(int page, int row) {
        return getAcct(page, row, true);
    }

    @Override
    public String getBills(int page, int row, boolean order) {
        return NetworkUtils.postWithCookie("sort=createtime&order=" + (order ? "DESC" : "ASC") + "&page=" + page + "&rows=" + row, cookie, "http://" + getBrasIP() + ":8080/manage/self/bill/getlist", timeout);
    }

    public String getBills(int page, int row) {
        return getBills(page, row, true);
    }

    @Override
    public String getRecharge(int page, int row, boolean order) {
        return NetworkUtils.postWithCookie("sort=oper_time&order=" + (order ? "DESC" : "ASC") + "&page=" + page + "&rows=" + row, cookie, "http://" + getBrasIP() + ":8080/manage/self/recharge/getlist", timeout);
    }

    public String getRecharge(int page, int row) {
        return getRecharge(page, row, true);
    }

    public void resetCookie(String authkey) throws LoginException {
        String[] returnData = login(authkey, timeout);
        cookie = returnData[1];
    }

    public static String[] login(String username, String password, int timeout) throws LoginException {
        return login("username=" + username + "&password=" + password, timeout);
    }

    public static String[] login(String authkey, int timeout) throws LoginException {
        String[] result = NetworkUtils.postAndGetCookie(authkey, "http://" + getBrasIP() + ":8080/manage/self/auth/login", timeout);
        if (result[0].endsWith("\"reply_code\":0}\n")) {
            return result;
        } else {
            throw new LoginException(result[0]);
        }
    }

    public boolean isCookieInvalid(String result) {
        return result.endsWith("\"reply_code\":8}\n");
    }

    public static boolean isLoginSuccess(String result) {
        return result.endsWith("\"reply_code\":0}\n");
    }

    /**
     * 根据返回结果字符串判断是否查询成功
     *
     * @param result  返回的字符串
     * @param catalog 查询的项目类别
     * @return 是否查询成功
     */
    public static boolean isQuerySuccess(String result, int catalog) {
        switch (catalog) {
            case BASICINFO:
                if (result.endsWith("\"reply_code\":0}\n")) return true;
                break;
            case ONLINE:
                if (result.endsWith("\"reply_code\":0}\n")) return true;
                break;
            case AUTHLOG:
                if (result.endsWith("\"reply_code\":0}\n")) return true;
                break;
            case ACCT:
                if (result.endsWith("\"reply_code\":0}\n")) return true;
                break;
            case BILLS:
                if (result.endsWith("\"reply_code\":0}\n")) return true;
                break;
            case RECHARGE:
                if (result.endsWith("\"reply_code\":0}\n")) return true;
                break;
            default:
                return false;
        }
        return false;
    }

    private static String getCachedBrasIP() {
        if (cachedBrasIP == null) {
            return cachedBrasIP = NetworkUtils.getCurrentBrasIP();
        }
        System.out.println("获得缓存IP:" + cachedBrasIP);
        return cachedBrasIP;
    }

    public static String getBrasIP() {
        if (settingsBrasIP == null) {
            return getCachedBrasIP();
        }
        System.out.println("获用户指定IP:" + settingsBrasIP);
        return settingsBrasIP;
    }

    public static String getSettingsBrasIP() {
        return settingsBrasIP;
    }

    public static void setSettingsBrasIP(String settingsBrasIP) {
        OfflineQueryService.settingsBrasIP = settingsBrasIP;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public static BrasIDInfo getBrasIDInfo(String cookie) {
        String result = NetworkUtils.postWithCookie(null, cookie, "http://bras.nju.edu.cn:8080/manage/self/userinfo/getinfo", 1000);
        return BrasIDInfo.getFromJson(result);
    }

    public static AuthLogBras getAuthLog(int page, int row, boolean order, String cookie) {
        String result = NetworkUtils.postWithCookie("sort=datetime&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, cookie, "http://" + "bras.nju.edu.cn" + ":8080/manage/self/authlog/getlist", 300);
        return AuthLogBras.getFromJson(result);
    }
}
