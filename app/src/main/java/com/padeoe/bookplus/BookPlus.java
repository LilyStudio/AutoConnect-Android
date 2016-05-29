package com.padeoe.bookplus;

import com.padeoe.icroom.util.NetworkUtils;
import com.padeoe.utils.LoginException;

import java.util.HashMap;

/**
 * 该类用于实现<a href="http://book.njulib.cn/">南京大学图书馆Book+</a>的接口
 *
 * @author padeoe
 *         Date: 2016/3/10.
 */
public class BookPlus {
    private String cookie = null;

    /**
     * 使用用户名密码构造
     *
     * @param username 用户名:学号/工号
     * @param password <a href="http://book.njulib.cn/">南京大学图书馆Book+</a>的密码:初始密码是用户名
     * @throws LoginException 登陆失败
     */
    public BookPlus(String username, String password) throws LoginException {
        String[] returnInfo = login(username, password);
        cookie = returnInfo[1];
    }

    /**
     * 使用已有的cookie进行构造
     *
     * @param cookie cookie
     */
    public BookPlus(String cookie) {
        this.cookie = cookie;
    }

    /**
     * 用于登陆<a href="http://book.njulib.cn/">南京大学图书馆Book+</a>
     *
     * @param username 用户名:学号/工号
     * @param password <a href="http://book.njulib.cn/">南京大学图书馆Book+</a>的密码:初始密码是用户名
     * @return 一个长度为2的字符数组，第一个元素是服务器返回的数据，第二个是获取的cookie
     * @throws LoginException 登陆失败
     */
    public static String[] login(String username, String password) throws LoginException {
        double random = Math.random();
        String url = "http://book.njulib.cn/smarty_lib/control/controller.php?control=control_log&action=user_login&user_id=" + username + "&user_pwd=" + password + "&rand=" + random;
        String returnInfo[] = NetworkUtils.myPostAndGetCookie("control=control_log&action=user_login&user_id=" + username + "&user_pwd=" + password + "&rand=" + random, url, 2000);
        if (returnInfo == null || returnInfo[0] == null || !returnInfo[0].equals("ok!")) {
            if (returnInfo != null && returnInfo[0] != null)
                throw new LoginException(returnInfo[0]);
            else
                throw new LoginException();
        } else
            return returnInfo;
    }

    /**
     * 显示用户信息，包括姓名和性别的html文本
     *
     * @return 服务器返回的包括姓名和性别的html文本
     */
    public String showReaderInfo() {
        HashMap<String, String> attr = new HashMap<>();
        attr.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        attr.put("Accept-Encoding", "gzip, deflate, sdch");
        attr.put("Referer", "http://book.njulib.cn/book_lst.php");
        attr.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        attr.put("Upgrade-Insecure-Requests", "1");
        attr.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4,zh-TW;q=0.2");
        return NetworkUtils.mygetWithCookie(null, "http://book.njulib.cn/reder_infor.php", cookie, 5000);
    }

    /**
     * 显示用户的性别，来源于{@linkplain #showReaderInfo()}中的信息提取
     *
     * @return 用户的性别，“男”或“女”
     */
    public String showGender() {
        String returnData = showReaderInfo();
        return getGender(returnData);
    }

    /**
     * 从{@link #showReaderInfo()}中提取性别
     *
     * @param returnData{@link #showReaderInfo()}的返回值
     * @return 用户性别，"男"或"女"
     */
    public static String getGender(String returnData) {
        if (returnData.contains("女"))
            return "女";
        if (returnData.contains("男"))
            return "男";
        return "";
    }
}
