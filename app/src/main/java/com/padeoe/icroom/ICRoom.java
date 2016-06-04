package com.padeoe.icroom;

import com.padeoe.icroom.util.NetworkUtils;
import com.padeoe.utils.LoginException;

import java.io.IOException;

/**
 * 该类用于实现 <a href="http://114.212.7.24/ClientWeb/xcus/IC2/">南京大学IC空间管理系统</a> 的接口
 * @author padeoe
 * Date: 2016/03/09
 */

public class ICRoom {
    private String cookie = null;

    /**
     * 通过用户名，密码构造，将会登陆后获取登录cookie
     *
     * @param username 用户名:学号/工号
     * @param password <a href="http://114.212.7.24/ClientWeb/xcus/IC2/">南京大学IC空间管理系统</a>的密码:初始值是用户名
     */
    public ICRoom(String username, String password) throws LoginException, IOException {
        String[] returnData = login(username, password);
        cookie = returnData[1];
    }

    /**
     * 直接通过已有cookie进行构造
     *
     * @param cookie 已获得的cookie
     */
    public ICRoom(String cookie) {
        if (cookie != null) {
            this.cookie = cookie;
        }

    }

    /**
     * 用于登陆
     *
     * @param username 用户名:学号/工号
     * @param password <a href="http://114.212.7.24/ClientWeb/xcus/IC2/">南京大学IC空间管理系统</a>的密码:初始值是用户名
     * @return 数组长度为2，第一个元素是服务器返回的原始数据，第二个元素是cookie
     * @throws LoginException 登陆失败
     */
    public static String[] login(String username, String password) throws LoginException, IOException {
        String returnInfo[] = NetworkUtils.myPostAndGetCookie("id=" + username + "&pwd=" + password + "&act=login", "http://114.212.7.24/ClientWeb/pro/ajax/login.aspx", 10000);
        if (returnInfo == null || returnInfo[0] == null || returnInfo[0].startsWith("{\"ret\":0")) {
            if (returnInfo != null &&returnInfo[0] != null)
                throw new LoginException(returnInfo[0]);
            else
                throw new LoginException();
        } else
            return returnInfo;
    }

    /**
     * 预约研讨间页面具有的查找用户信息的功能，包括学生的姓名，院系，手机号（因为具有隐私泄露风险已向网站建设方报告并删除了手机号显示）
     *
     * @param ID 用户名:学号/工号
     * @return 服务器返回的用户信息字符串，非json格式，需要调用 {@link #parse(String)}方法处理成json格式
     */
    public String queryStudentInfo(String ID) throws IOException {
        String time = String.valueOf(System.currentTimeMillis());
        return NetworkUtils.mygetWithCookie("type=&&term=" + ID + "&_=" + time, "http://114.212.7.24/ClientWeb/pro/ajax/data/searchAccount.aspx?type=&&term=" + ID + "&_=" + time,
                cookie, 20000);
    }

    /**
     * 对服务器返回的数据进行处理，处理成json格式，并排除空数据，脏数据
     * @param result {@link #queryStudentInfo(String)}获得的数据，非json格式，且含有脏数据
     * @return 处理后的数据，是json格式，可以继续调用 {@link ICRoomUser#getFromJson(String)}对返回值进行处理获得对象
     */
    public static String parse(String result){
        int length=result.length();
        if(length<4){
            return null;
        }
        result=result.substring(1,length-1);
        if(result.equals("")){
            return null;
        }
        if(result.indexOf("}")!=length-3){
            return result.substring(0,result.indexOf("}")+1);
        }
        //排除脏数据
/*        if(result.indexOf("SKL3344")!=-1||result.indexOf("赵文胜")!=-1){
            return null;
        }*/
        return result;
    }

    /**
     * 预约研讨间页面具有的查找用户信息的功能，包括学生的姓名，院系，手机号（因为具有隐私泄露风险已向网站建设方报告并删除了手机号显示）,该方法会对特定ID查询其信息
     * @param ID 用户名:学号/工号
     * @return 用户信息的对象格式 {@link ICRoomUser}
     */
    public ICRoomUser queryStudentInfo_Object(String ID) throws IOException {
        String result = queryStudentInfo(ID);
        String newResult=parse(result);
        return ICRoomUser.getFromJson(newResult);
    }
}
