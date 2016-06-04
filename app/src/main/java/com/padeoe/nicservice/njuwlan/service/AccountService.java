package com.padeoe.nicservice.njuwlan.service;

import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;

import java.io.IOException;

/**
 * 该类用于实现<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的账户管理功能，包括修改密码功能
 * Created by padeoe on 2015/9/26.
 */
public class AccountService {
    /**
     * 修改<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的密码，将会依次尝试
     * 1.通过<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的接口修改密码，
     * 2.通过<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>的接口修改密码。
     *
     * @param username <a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的用户名
     * @param oldPassword <a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的旧密码
     * @param newPassword <a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的新密码
     * @return <a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的服务器返回消息,可以通过 {@link #isChangePasswSuccess(String)}来对返回值分析是否改密成功
     */
    public static String changePassword(String username, String oldPassword, String newPassword) throws IOException {
        if (new OnlineQueryService().isPortalOnline()) {
            return NetworkUtils.connectAndPost("oldpassword=" + oldPassword + "&newpassword=" + newPassword + "&confirmpassword=" + newPassword, "http://"+NetworkUtils.getCurrentPortalIP()+"/portal_io/selfservice/modifypassword", 200);
        } else {
            //首先登陆bras.nju.edu.cn获取cookie
            String cookie;
            String[] loginResult = NetworkUtils.postAndGetCookie("username=" + username + "&password=" + oldPassword, "http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/auth/login", 200);
            if (OfflineQueryService.isLoginSuccess(loginResult[0])) {
                cookie = loginResult[1];
                //登陆成功，修改密码
                return NetworkUtils.postWithCookie("oldPassword=" + oldPassword + "&newPassword=" + newPassword + "&confirmPassword=" + newPassword, cookie, "http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/userinfo/modifypassword", 200);
            } else {
                isChangePasswSuccess("");
                return loginResult[0];
            }
        }
    }

    /**
     * 判断{@link #changePassword(String, String, String)}是否改密成功
     * @param result {@link AccountService#changePassword(String username, String oldPassword, String newPassword)}返回值
     * @return 是否改密成功
     */
    public static boolean isChangePasswSuccess(String result) {
        return result.equals("{\"reply_msg\":\"操作成功\",\"reply_code\":0}\n") || result.endsWith("\"reply_code\":0,\"request_uri\":\"/manage/self/userinfo/modifypassword\"}\n");
    }

}
