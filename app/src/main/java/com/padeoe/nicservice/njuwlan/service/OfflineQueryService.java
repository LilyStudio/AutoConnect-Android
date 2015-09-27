package com.padeoe.nicservice.njuwlan.service;

import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;

import java.util.Calendar;

/**
 * Created by padeoe on 2015/9/24.
 */
public class OfflineQueryService implements DetailQuery{
    private static OfflineQueryService offlineQueryService;
    private int timeout=200;
    private String cookie=null;
    private String authkey=null;
    public static final int BASICINFO = 0;
    public static final int ONLINE = 1;
    public static final int AUTHLOG = 2;
    public static final int ACCT = 3;
    public static final int BILLS = 4;
    public static final int RECHARGE = 5;
    private OfflineQueryService(String username, String password){
        System.out.println("构造函数");
        authkey="username="+username+"&password="+password;
        resetCookie(authkey);
    }

    public static OfflineQueryService getInstance(String username,String password){
        System.out.println("获取实例");
        if(offlineQueryService ==null){
            offlineQueryService =new OfflineQueryService(username, password);
        }
        return offlineQueryService;
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

    @Override
    public String getOnline(int page, int row){
        if(cookie==null){
            resetCookie(authkey);
        }
        String result=NetworkUtils.postWithCookie("page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/online/getlist",timeout);
        if(isCookieInvalid(result)){
            resetCookie(authkey);
            result=NetworkUtils.postWithCookie("page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/online/getlist",timeout);
        }
        return result;
    }

    @Override
    public String getAuthLog(int page, int row, boolean order) {
        if(cookie==null){
            resetCookie(authkey);
        }
        String result=NetworkUtils.postWithCookie("sort=datetime&order="+(order?"desc":"asc")+"&page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/authlog/getlist",timeout);
        if(isCookieInvalid(result)) {
            resetCookie(authkey);
            result=NetworkUtils.postWithCookie("sort=datetime&order="+(order?"desc":"asc")+"&page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/authlog/getlist",timeout);
        }
        return result;
    }

    public String getAuthLog(int page, int row) {
        return getAuthLog(page, row, true);
    }

    @Override
    public String getAcct(int page, int row, boolean order) {
        int currentMonth=Calendar.getInstance().get(Calendar.MONTH)+1;
        return getAcct(currentMonth, page, row, order);
    }

    public String getAcct(int month,int page, int row, boolean order) {
        if(cookie==null){
            resetCookie(authkey);
        }
        String result=NetworkUtils.postWithCookie("month="+month+"&sort=acctstarttime&order="+(order?"desc":"asc")+"&page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/detail/getlist",timeout);
        if(isCookieInvalid(result)) {
            resetCookie(authkey);
            result=NetworkUtils.postWithCookie("month="+month+"&sort=acctstarttime&order="+(order?"desc":"asc")+"&page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/detail/getlist",timeout);
        }
        return result;
    }
    public String getAcct(int page, int row) {
        return getAcct(page, row,true);
    }

    @Override
    public String getBills(int page, int row, boolean order) {
        if(cookie==null){
            resetCookie(authkey);
        }
        String result=NetworkUtils.postWithCookie("sort=createtime&order="+(order?"DESC":"ASC")+"&page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/bill/getlist",timeout);
        if(isCookieInvalid(result)) {
            resetCookie(authkey);
            result=NetworkUtils.postWithCookie("sort=createtime&order="+(order?"DESC":"ASC")+"&page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/bill/getlist",timeout);
        }
        return result;
    }

    public String getBills(int page, int row) {
        return getBills(page, row,true);
    }

    @Override
    public String getRecharge(int page, int row, boolean order) {
        if(cookie==null){
            resetCookie(authkey);
        }
        String result=NetworkUtils.postWithCookie("sort=oper_time&order="+(order?"DESC":"ASC")+"&page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/recharge/getlist",timeout);
        if(isCookieInvalid(result)) {
            resetCookie(authkey);
            result=NetworkUtils.postWithCookie("sort=oper_time&order="+(order?"DESC":"ASC")+"&page="+page+"&rows="+row,cookie,"http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/recharge/getlist",timeout);
        }
        return result;
    }

    public String getRecharge(int page, int row) {
        return getRecharge(page,row,true);
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String resetCookie(String authkey){
        String[] result= NetworkUtils.postAndGetCookie(authkey, "http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/auth/login",timeout);
        System.out.println("试图登录:"+result[0]);
        if(isLoginSuccess(result[0])){
            System.out.println("试图登录:cookie:"+result[1]);
            cookie=result[1];
        }
        return result[0];
    }

    public boolean isCookieInvalid(String result){
        if(result.endsWith("\"reply_code\":8}\n")){
            return true;
        }
        return false;
    }

    public static boolean isLoginSuccess(String result){

        if(result.endsWith("\"reply_code\":0}\n")){
            return true;
        }
        return false;
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


}
