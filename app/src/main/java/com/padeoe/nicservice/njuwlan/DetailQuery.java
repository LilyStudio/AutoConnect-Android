package com.padeoe.nicservice.njuwlan;

import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 *
 */
public class DetailQuery {
    public static final int BASICINFO = 1;
    public static final int AUTHLOG = 2;
    public static final int ACCT = 3;
    public static final int BILLS = 4;

    /**
     * 防止类被实例化
     */
    private DetailQuery() {
    }

    /**
     * 根据项目类别进行查询
     *
     * @param catalog 项目类别
     * @param page    当前页数
     * @param row     每页行数
     * @param timeout 超时时间
     * @return
     */
    public static String queryBy(int catalog, int page, int row, int timeout) {
        return queryBy(catalog, page, row, timeout);
    }

    /**
     * 根据项目类别进行查询
     *
     * @param catalog 项目类别
     * @param page    当前页数
     * @param row     每页行数
     * @param order   true时间由近及远,false由远及近
     * @param timeout 超时时间
     * @return
     */
    public static String queryBy(int catalog, int page, int row, boolean order, int timeout) {
        switch (catalog) {
            case BASICINFO:
                return getBills(page, row, order, timeout);
            case AUTHLOG:
                return getAuthLog(page, row, order, timeout);
            case ACCT:
                return getAcct(page, row, order, timeout);
            case BILLS:
                return getBills(page, row, order, timeout);
        }
        return null;
    }

    /**
     * 获取BasicInfo
     *
     * @return
     */
    public static String getBasicInfo() {
        String result = NetworkUtils.connectAndPost("", "http://p.nju.edu.cn/portal_io/selfservice/volume/getlist", 200);
        return result;
    }

    /**
     * 获取登陆历史
     *
     * @param page    当前页数
     * @param row     每页行数
     * @param timeout 超时时间
     * @return
     */
    public static String getAuthLog(int page, int row, int timeout) {
        return getAuthLog(page, row, true, timeout);
    }

    /**
     * 获取登陆历史
     *
     * @param page    当前页数
     * @param row     每页行数
     * @param order   true时间由近及远,false由远及近
     * @param timeout 超时时间
     * @return
     */
    public static String getAuthLog(int page, int row, boolean order, int timeout) {
        String result = NetworkUtils.connectAndPost("sort=id&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://p.nju.edu.cn/portal_io/selfservice/authlog/getlist", timeout);
        return result;
    }

    /**
     * 获取详单信息
     *
     * @param page    当前页数
     * @param row     每页行数
     * @param timeout 超时时间
     * @return
     */
    public static String getAcct(int page, int row, int timeout) {
        return getAcct(page, row, true, timeout);
    }

    /**
     * 获取详单信息
     *
     * @param page    当前页数
     * @param row     每页行数
     * @param order   true时间由近及远,false由远及近
     * @param timeout 超时时间
     * @return
     */
    public static String getAcct(int page, int row, boolean order, int timeout) {
        String result = NetworkUtils.connectAndPost("sort=acctstoptime&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://p.nju.edu.cn/portal_io/selfservice/acct/getlist", timeout);
        return result;
    }

    /**
     * 获取账单信息
     *
     * @param page    当前页数
     * @param row     每页行数
     * @param timeout 超时时间
     * @return
     */
    public static String getBills(int page, int row, int timeout) {
        return getBills(page, row, true, timeout);
    }

    /**
     * 获取账单信息
     *
     * @param page    当前页数
     * @param row     每页行数
     * @param order   true时间由近及远,false由远及近
     * @param timeout 超时时间
     * @return
     */
    public static String getBills(int page, int row, boolean order, int timeout) {
        String result = NetworkUtils.connectAndPost("sort=id&order=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://p.nju.edu.cn/portal_io/selfservice/bill/getlist", timeout);
        return result;
    }

    /**
     * 获取充值信息
     *
     * @param page    当前页数
     * @param row     每页行数
     * @param timeout 超时时间
     * @return
     */
    public static String getRecharge(int page, int row, int timeout) {
        return getRecharge(page, row, true, timeout);
    }

    /**
     * 获取充值信息
     *
     * @param page    当前页数
     * @param row     每页行数
     * @param order   true时间由近及远,false由远及近
     * @param timeout 超时时间
     * @return
     */
    public static String getRecharge(int page, int row, boolean order, int timeout) {
        String result = NetworkUtils.connectAndPost("sort=id&desc=" + (order ? "desc" : "asc") + "&page=" + page + "&rows=" + row, "http://p.nju.edu.cn/portal_io/selfservice/recharge/getlist", timeout);
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
            case AUTHLOG:
                if (result.endsWith("\"reply_code\":0}\n")) return true;
                break;
            case ACCT:
                if (result.endsWith("\"reply_code\":0}\n")) return true;
                break;
            case BILLS:
                if (result.endsWith("\"reply_code\":0}\n")) return true;
                break;
            default:
                return false;
        }
        return false;
    }

}
