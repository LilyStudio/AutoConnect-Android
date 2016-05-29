package com.padeoe.nicservice.njuwlan.service;

import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 该类用于提供<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的接口。
 * 包括了登陆，下线等功能。
 * 该类使用单例模式，因为该类对一个用户的账号进行实例化，而单个终端只支持一个用户账户同时在线。
 *
 * @author padeoe
 *         Date: 2015/9/15
 * @author Nifury, padeoe
 */
public class LoginService {
    private static LoginService loginService;
    /**
     * 缓存的<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的服务器IP，为null时表示当前无缓存
     */
    private String cachedPortalIP = null;
    /**
     * 用户为<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>设置指定的IP,为null时表示用户未指定
     */
    private String settingsPortalIP = null;
    /**
     * 向<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>服务器发送消息的超时时间
     */
    private int timeout = 200;

    /**
     * 防止类被实例化
     */
    private LoginService() {
    }

    public static LoginService getInstance() {
        System.out.println("获取实例");
        if (loginService == null)
            loginService = new LoginService();
        return loginService;
    }

    /**
     * 获取挑战字
     *
     * @return challenge，null时失败
     */
    private String[] getChallenge() {
        String result = NetworkUtils.connectAndPost("", "http://" + getPortalIP() + "/portal_io/getchallenge", timeout);
        if (result != null && result.startsWith("{\"reply_msg\":\"操作成功\"")) {
            return new String[]{result.substring(result.indexOf("\"challenge\":\"") + 13, result.indexOf("\",\"reply_code\"")), result};
        }
        return new String[]{null, result};
    }


    /**
     * 登陆<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>,旧版,不加密传输
     *
     * @param username <a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的用户名
     * @param password <a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的密码
     * @param timeout  超时时间
     * @return 返回的字符串的JSON解析
     */
    @Deprecated
    public String oldConnect(String username, String password, int timeout) {
        String postdata = "action=login&username=" + username + "&password=" + password;
        return NetworkUtils.connectAndPost(postdata, "http://" + getPortalIP() + "/portal_io/login", timeout);
    }

    /**
     * 登陆<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>，新版，加密传输
     *
     * @param username <a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的用户名
     * @param password <a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的密码
     * @return 服务器返回数据
     */
    public String connect(String username, String password) {
        String challenge[] = getChallenge();
        if (challenge[0] != null) {
            String postdata = "username=" + username + "&password=" + createChapPassword(password, challenge[0]) + "&challenge=" + challenge[0];
            return NetworkUtils.connectAndPost(postdata, "http://" + getPortalIP() + "/portal_io/login", timeout);
        } else {
            return challenge[1];
        }

    }

    /**
     * 从<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>下线
     *
     * @return 服务器返回的数据
     */
    public String disconnect() {
        return NetworkUtils.connectAndPost("", "http://" + getPortalIP() + "/portal_io/logout", timeout);
    }

    /**
     * 创建chap密码
     *
     * @param password  密码明文
     * @param challenge 密码密文
     * @return 服务器返回数据
     */
    private static String createChapPassword(String password, String challenge) {
        byte[] str = new byte[password.length() + 1 + 16];
        char id = (char) ((int) Math.round(Math.random() * 10000) % 256);
        int counter = 0;
        str[counter++] = (byte) id;
        for (char c : password.toCharArray()) {
            str[counter++] = (byte) c;
        }
        for (int i = 0; i < challenge.length(); i += 2) {
            String hex = challenge.substring(i, i + 2);
            int dec = (int) Long.parseLong(hex, 16);
            str[counter++] = ((byte) dec);
        }
        String hash = md5(str);
        return ((id < 16) ? "0" : "") + Integer.toHexString(id) + hash;
    }

    /**
     * 计算字符串的md5
     *
     * @param str byte数组
     * @return md5值
     */
    private static String md5(byte str[]) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(str);
            byte[] digest = m.digest();
            BigInteger bigString = new BigInteger(1, digest);
            String hashtext = bigString.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据返回消息判断是否登陆成功
     *
     * @param result {@link LoginService#connect(String, String)}或{@link LoginService#oldConnect(String, String, int)}返回值
     * @return 是否登陆成功
     */
    public static boolean isLoginSuccess(String result) {
        return result.startsWith("{\"reply_code\":1") || result.startsWith("{\"reply_code\":6");
    }

    /**
     * 根据返回消息判断是否成功连接了服务器
     *
     * @param result
     * @return
     */
    public static boolean isLoginConnectSuccess(String result) {
        return result.startsWith("{\"reply_code\"");
    }

    /**
     * 根据返回消息判断是否下线成功
     *
     * @param result {@link LoginService#disconnect()}返回值
     * @return 是否下线成功
     */
    public static boolean isLogoutSuccess(String result) {
        return result.startsWith("{\"reply_code\":101");
    }

    /**
     * 查询当前是否已登录<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>
     *
     * @return 表示当前是否在线
     */
    public boolean isPortalOnline() {
        String result = NetworkUtils.connectAndPost("", "http://" + getPortalIP() + "/portal_io/getinfo", 200);
        return result.endsWith("\"reply_code\":0,\"reply_msg\":\"操作成功\"}\n");
    }

    /**
     * 获取缓存的<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的IP
     *
     * @return 当前缓存的IP，如果不存在将会调用{@link NetworkUtils#getCurrentPortalIP()}当即解析
     */
    private String getCachedPortalIP() {
        return cachedPortalIP == null ? cachedPortalIP = NetworkUtils.getCurrentPortalIP() : cachedPortalIP;
    }

    /**
     * 获取<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的服务器IP地址，查询方式优先级如下：
     * 查询用户设置的IP，
     * 查询缓存IP，
     * DNS解析获得IP，
     * 默认IP
     *
     * @return <a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的服务器IP地址
     */
    private String getPortalIP() {
        return settingsPortalIP == null ? getCachedPortalIP() : settingsPortalIP;
    }

    /**
     * 获取用户设置的<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的IP地址
     *
     * @return 用户设置的 <a href="http://p.nju.edu.cn"> 南京大学网络认证系统</a>的IP地址
     */
    public String getSettingsPortalIP() {
        return settingsPortalIP;
    }

    /**
     * 设置用户设置的<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的IP地址
     *
     * @param settingsPortalIP 用户设置的<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的IP地址
     */
    public void setSettingsPortalIP(String settingsPortalIP) {
        this.settingsPortalIP = settingsPortalIP;
    }

    /**
     * 获得当前登录<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的超时时间
     *
     * @return
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * 设置登录<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的超时时间
     *
     * @return
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}

