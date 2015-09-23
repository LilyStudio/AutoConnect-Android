package com.padeoe.nicservice.njuwlan;

import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by padeoe on 4/20/15.
 * Modified on 9/15/2015
 *
 * @author yus, padeoe
 */
public class ConnectPNJU {
    private static final String LOGINURL = "http://219.219.114.15/portal_io/login";
    private static final String LOGOUTURL = "http://219.219.114.15/portal_io/logout";

    /**
     * 防止类被实例化
     */
    private ConnectPNJU() {
    }

    /**
     * 获取挑战字
     *
     * @return challenge，null时失败
     */
    public static String getChallenge() {
        String result = NetworkUtils.connectAndPost("", "http://219.219.114.15/portal_io/getchallenge", 200);
        if (result != null && result.startsWith("{\"reply_msg\":\"操作成功\"")) {
            return result.substring(result.indexOf("\"challenge\":\"") + 13, result.indexOf("\",\"reply_code\""));
        }
        return null;
    }


    /**
     * 旧版登陆，不加密传输
     *
     * @param username
     * @param password
     * @param timeout
     * @return 返回的字符串的JSON解析
     */
    @Deprecated
    public static String oldConnect(String username, String password, int timeout) {
        String postdata = "action=login&username=" + username + "&password=" + password;
        String result = NetworkUtils.connectAndPost(postdata, LOGINURL, timeout);
        return result;
    }

    /**
     * 登陆，新版，加密传输
     *
     * @param username
     * @param password
     * @param timeout
     * @return
     */
    public static String connect(String username, String password, int timeout) {
        String challenge = getChallenge();
        if(challenge!=null){
            String postdata = "username=" + username + "&password=" + createChapPassword(password, challenge) + "&challenge=" + challenge;
            String result = NetworkUtils.connectAndPost(postdata, LOGINURL, timeout);
            return result;
        }
        else{
            String result=oldConnect(username, password, timeout);
            return result;
        }
    }

    /**
     * 下线
     *
     * @return 是否成功
     */
    public static String disconnect(int timeout) {
        String result = NetworkUtils.connectAndPost("", LOGOUTURL, timeout);
        return result;
    }

    /**
     * 创建chap密码
     *
     * @param password
     * @param challenge
     * @return
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
        String chappassword = ((id < 16) ? "0" : "") + Integer.toHexString(id) + hash;
        return chappassword;
    }

    /**
     * 计算md5
     *
     * @param str
     * @return
     */
    private static String md5(byte str[]) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(str);
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
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
     * 根据返回字符串判断是否登陆成功
     *
     * @param result
     * @return
     */
    public static boolean isLoginSuccess(String result) {
        if (result.startsWith("{\"reply_code\":1") || result.startsWith("{\"reply_code\":6")) {
            return true;
        }
        return false;
    }

    /**
     * 根据返回字符串判断是否下线成功
     *
     * @param result
     * @return
     */
    public static boolean isLogoutSuccess(String result) {
        if (result.startsWith("{\"reply_code\":101")) {
            return true;
        }
        return false;
    }


}

