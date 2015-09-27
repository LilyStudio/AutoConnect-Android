package com.padeoe.nicservice.njuwlan.service;

import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 *
 */
public class OnlineQueryService implements DetailQuery {
    private static OnlineQueryService onlineQueryService;
    private int timeout = 200;
    public static final int BASICINFO=0;
    public static final int ONLINE = 1;
    public static final int AUTHLOG = 2;
    public static final int ACCT = 3;
    public static final int BILLS = 4;
    public static final int RECHARGE = 5;

    private OnlineQueryService() {
    }

    /**
     * 获取对象实例
     * @return
     */
    public static OnlineQueryService getInstance() {
        if (onlineQueryService == null) {
            onlineQueryService = new OnlineQueryService();
        }
        return onlineQueryService;
    }

    /**
     * 根据项目类别进行查询
     *
     * @param catalog 项目类别
     * @param page    当前页数
     * @param row     每页行数
     * @return
     */
    public String queryBy(int catalog, int page, int row) {
        return queryBy(catalog, page, row);
    }

    /**
     * 根据项目类别进行查询
     *
     * @param catalog 项目类别
     * @param page    当前页数
     * @param row     每页行数
     * @param order   true时间由近及远,false由远及近
     * @return
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
     * @return
     */
    @Override
    public String getOnline(int page, int row) {
        String result = NetworkUtils.connectAndPost("page=" + page + "&rows=" + row, "http://"+NetworkUtils.getCurrentPortalIP()+"/portal_io/selfservice/online/getlist", timeout);
        return result;
    }


    /**
     * 获取BasicInfo
     *
     * @return
     */
    public String getBasicInfo() {
        String result = NetworkUtils.connectAndPost("", "http://"+NetworkUtils.getCurrentPortalIP()+"/portal_io/selfservice/volume/getlist", timeout);
        return result;
    }

    /**
     * 获取登陆历史
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return
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
     * @return
     */
    @Override
    public String getAuthLog(int page, int row, boolean order) {
        String result = NetworkUtils.connectAndPost("sort=id&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+NetworkUtils.getCurrentPortalIP()+"/portal_io/selfservice/authlog/getlist", timeout);
        return result;
    }

    /**
     * 获取详单信息
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return
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
     * @return
     */
    @Override
    public String getAcct(int page, int row, boolean order) {
        String result = NetworkUtils.connectAndPost("sort=acctstoptime&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+NetworkUtils.getCurrentPortalIP()+"/portal_io/selfservice/acct/getlist", timeout);
        return result;
    }

    /**
     * 获取账单信息
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return
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
     * @return
     */
    @Override
    public String getBills(int page, int row, boolean order) {
        String result = NetworkUtils.connectAndPost("sort=id&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+NetworkUtils.getCurrentPortalIP()+"/portal_io/selfservice/bill/getlist", timeout);
        return result;
    }

    /**
     * 获取充值信息
     *
     * @param page 当前页数
     * @param row  每页行数
     * @return
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
     * @return
     */
    @Override
    public String getRecharge(int page, int row, boolean order) {
        String result = NetworkUtils.connectAndPost("sort=id&desc=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://"+NetworkUtils.getCurrentPortalIP()+"/portal_io/selfservice/recharge/getlist", timeout);
        return result;
    }

    /**
     * 根据返回结果字符串判断是否查询成功
     *
     * @param result  返回的字符串
     * @param catalog 查询的项目类别
     * @return
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

    public int getTimeout() {
        return timeout;
    }

    /**
     * 设置超时时间
     * @param timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
