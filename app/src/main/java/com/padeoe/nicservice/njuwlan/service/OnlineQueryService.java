package com.padeoe.nicservice.njuwlan.service;

import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;

import java.io.IOException;

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
    public String queryBy(int catalog, int page, int row) throws IOException {
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
    public String queryBy(int catalog, int page, int row, boolean order) throws IOException {
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
    public String getOnline(int page, int row) throws IOException {
        return NetworkUtils.connectAndPost("page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/online/getlist", timeout);
    }


    /**
     * 获取BasicInfo
     *
     * @return 服务器返回的查询结果,json格式
     */
    public String getBasicInfo() throws IOException {
        return NetworkUtils.connectAndPost("", "http://"+getPortalIP()+"/portal_io/selfservice/volume/getlist", timeout);
    }

    /**
     * 获取登陆历史
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return 服务器返回的查询结果,json格式
     */
    public String getAuthLog(int page, int row) throws IOException {
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
    public String getAuthLog(int page, int row, boolean order) throws IOException {
        return NetworkUtils.connectAndPost("sort=id&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/authlog/getlist", timeout);
    }

    /**
     * 获取详单信息
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return 服务器返回的查询结果,json格式
     */
    public String getAcct(int page, int row) throws IOException {
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
    public String getAcct(int page, int row, boolean order) throws IOException {
        return NetworkUtils.connectAndPost("sort=acctstoptime&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/acct/getlist", timeout);
    }

    /**
     * 获取账单信息
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return 服务器返回的查询结果,json格式
     */
    public String getBills(int page, int row) throws IOException {
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
    public String getBills(int page, int row, boolean order) throws IOException {
        return NetworkUtils.connectAndPost("sort=id&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/bill/getlist", timeout);
    }

    /**
     * 获取充值信息
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return 服务器返回的查询结果,json格式
     */
    public String getRecharge(int page, int row) throws IOException {
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
    public String getRecharge(int page, int row, boolean order) throws IOException {
        return NetworkUtils.connectAndPost("sort=id&desc=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+getPortalIP()+"/portal_io/selfservice/recharge/getlist", timeout);
    }

    /**
     * 查询当前设备登录了<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的用户信息，如果没登陆，将返回错误提示
     * @return 服务器返回的查询结果,json格式，可被{@link com.padeoe.nicservice.njuwlan.object.portal.ReturnData#getFromJson(String)}解析为对象
     */
    public String getCurrentUserInfo() throws IOException {
        return NetworkUtils.connectAndPost("", "http://" + getPortalIP() + "/portal_io/getinfo", 200);
    }

    /**
     * 查询当前是否已登录<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>
     *
     * @return 表示当前是否在线
     */
    public boolean isPortalOnline() throws IOException {
        return isPortalOnline(getCurrentUserInfo());
    }

    /**
     * 根据已有的服务器返回信息查询当前是否已登录<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>
     * @param result 调用{@link #getCurrentUserInfo()} 查询获得的结果
     * @return 表示当前是否在线
     */
    public static boolean isPortalOnline(String result) {
        return -1 != result.indexOf("\"reply_code\":0,\"reply_msg\":\"操作成功\"");
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
                if (result.indexOf("\"reply_code\":0") != -1) return true;
                break;
            case ONLINE:
                if (result.indexOf("\"reply_code\":0") != -1) return true;
                break;
            case AUTHLOG:
                if (result.indexOf("\"reply_code\":0") != -1) return true;
                break;
            case ACCT:
                if (result.indexOf("\"reply_code\":0") != -1) return true;
                break;
            case BILLS:
                if (result.indexOf("\"reply_code\":0") != -1) return true;
                break;
            case RECHARGE:
                if (result.indexOf("\"reply_code\":0") != -1) return true;
                break;
            default:
                return false;
        }
        return false;
    }

    private String getCachedPortalIP(){
        return cachedPortalIP==null?cachedPortalIP=NetworkUtils.getCurrentPortalIP():cachedPortalIP;
    }

    private String getPortalIP(){
        return settingsPortalIP==null?getCachedPortalIP():settingsPortalIP;
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
