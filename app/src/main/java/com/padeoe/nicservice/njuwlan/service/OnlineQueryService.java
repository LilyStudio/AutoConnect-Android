package com.padeoe.nicservice.njuwlan.service;

import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;

/**
 * 该类实现了<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>提供的各类查询功能。
 * 包括查询用户信息，当前在线设备，详单信息，认证信息，充值信息，账单信息等。
 * @author padeoe
 * Date: 2015/9/23.
 */

public class OnlineQueryService implements DetailQuery {
    private int timeout = 200;
    public static final int BASICINFO=0;
    public static final int ONLINE = 1;
    public static final int AUTHLOG = 2;
    public static final int ACCT = 3;
    public static final int BILLS = 4;
    public static final int RECHARGE = 5;
    private String cachedPortalIP=null;
    private String settingsPortalIP=null;

    /**
     * 根据项目类别进行查询
     *
     * @param catalog 项目类别
     * @param page    当前页数
     * @param row     每页行数
     * @return 服务器返回的查询结果,json格式
     */
    public String queryBy(int catalog, int page, int row) {
        return queryBy(catalog, page, row,true);
    }

    /**
     * 根据项目类别进行查询
     *
     * @param catalog 项目类别
     * @param page    当前页数
     * @param row     每页行数
     * @param order   true时间由近及远,false由远及近
     * @return 服务器返回的查询结果,json格式
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
                return getRecharge(page,row);
        }
        return null;
    }

    /**
     * 获取当前账号在线用户
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return 服务器返回的查询结果,json格式
     */
    @Override
    public String getOnline(int page, int row) {
        return NetworkUtils.connectAndPost("page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/online/getlist", timeout);
    }


    /**
     * 获取BasicInfo
     *
     * @return 服务器返回的查询结果,json格式
     */
    public String getBasicInfo() {
        return NetworkUtils.connectAndPost("", "http://"+getPortalIP()+"/portal_io/selfservice/volume/getlist", timeout);
    }

    /**
     * 获取UserInfo
     *
     * @return 服务器返回的查询结果,json格式
     */
    public String getUserInfo() {
        return NetworkUtils.connectAndPost("", "http://"+getPortalIP()+"/portal_io/getinfo", timeout);
    }
    /**
     * 获取登陆历史
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return 服务器返回的查询结果,json格式
     */
    public String getAuthLog(int page, int row) {
        return getAuthLog(page, row, true);
    }

    /**
     * 获取登陆历史
     *
     * @param page  当前页数
     * @param row   每页行数
     * @param order true时间由近及远,false由远及近
     * @return 服务器返回的查询结果,json格式
     */
    @Override
    public String getAuthLog(int page, int row, boolean order) {
        return NetworkUtils.connectAndPost("sort=id&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/authlog/getlist", timeout);
    }

    /**
     * 获取详单信息
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return 服务器返回的查询结果,json格式
     */
    public String getAcct(int page, int row) {
        return getAcct(page, row, true);
    }

    /**
     * 获取详单信息
     *
     * @param page  当前页数
     * @param row   每页行数
     * @param order true时间由近及远,false由远及近
     * @return 服务器返回的查询结果,json格式
     */
    @Override
    public String getAcct(int page, int row, boolean order) {
        return NetworkUtils.connectAndPost("sort=acctstoptime&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/acct/getlist", timeout);
    }

    /**
     * 获取账单信息
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return 服务器返回的查询结果,json格式
     */
    public String getBills(int page, int row) {
        return getBills(page, row, true);
    }

    /**
     * 获取账单信息
     *
     * @param page  当前页数
     * @param row   每页行数
     * @param order true时间由近及远,false由远及近
     * @return 服务器返回的查询结果,json格式
     */
    @Override
    public String getBills(int page, int row, boolean order) {
        return NetworkUtils.connectAndPost("sort=id&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/bill/getlist", timeout);
    }

    /**
     * 获取充值信息
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return 服务器返回的查询结果,json格式
     */
    public String getRecharge(int page, int row) {
        return getRecharge(page, row, true);
    }

    /**
     * 获取充值信息
     *
     * @param page  当前页数
     * @param row   每页行数
     * @param order true时间由近及远,false由远及近
     * @return 服务器返回的查询结果,json格式
     */
    @Override
    public String getRecharge(int page, int row, boolean order) {
        return NetworkUtils.connectAndPost("sort=id&desc=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/recharge/getlist", timeout);
    }

    /**
     * 根据返回消息判断是否查询成功
     *
     * @param result  返回的字符串
     * @param catalog 查询的项目类别
     * @return 服务器返回的查询结果,json格式
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

    private String getCachedPortalIP(){
        if(cachedPortalIP==null){
            return cachedPortalIP=NetworkUtils.getCurrentPortalIP();
        }
        System.out.println("获得缓存IP:"+cachedPortalIP);
        return cachedPortalIP;
    }

    private String getPortalIP(){
        if(settingsPortalIP==null){
            return getCachedPortalIP();
        }
        System.out.println("获用户指定IP:"+settingsPortalIP);
        return settingsPortalIP;
    }

    public String getSettingsPortalIP() {
        return settingsPortalIP;
    }

    public void setSettingsPortalIP(String settingsPortalIP) {
        this.settingsPortalIP = settingsPortalIP;
    }

    public int getTimeout() {
        return timeout;
    }

    /**
     * 设置超时时间
     * @param timeout 超时时间
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
