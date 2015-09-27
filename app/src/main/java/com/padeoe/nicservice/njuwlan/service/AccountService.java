package com.padeoe.nicservice.njuwlan.service;

import com.padeoe.nicservice.njuwlan.utils.NetworkUtils;

/**
 * Created by padeoe on 2015/9/26.
 */
public class AccountService {
    /**
     * 修改密码
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public static String changePassword(String username, String oldPassword, String newPassword) {
        if (LoginService.isPortalOnline()) {
            String result = NetworkUtils.connectAndPost("oldpassword=" + oldPassword + "&newpassword=" + newPassword + "&confirmpassword=" + newPassword, "http://"+NetworkUtils.getCurrentPortalIP()+"/portal_io/selfservice/modifypassword", 200);
            return result;
        } else {
            //首先登陆bras.nju.edu.cn获取cookie
            String cookie = null;
            String[] loginResult = NetworkUtils.postAndGetCookie("username=" + username + "&password=" + oldPassword, "http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/auth/login", 200);
            if (OfflineQueryService.isLoginSuccess(loginResult[0])) {
                cookie = loginResult[1];
                //登陆成功，修改密码
                String result = NetworkUtils.postWithCookie("oldPassword=" + oldPassword + "&newPassword=" + newPassword + "&confirmPassword=" + newPassword, cookie, "http://"+NetworkUtils.getCurrentBrasIP()+":8080/manage/self/userinfo/modifypassword", 200);
                return result;
            } else {
                return loginResult[0];
            }
        }
    }

    public static boolean isChangePasswSuccess(String result) {
        if (result.equals("{\"reply_msg\":\"操作成功\",\"reply_code\":0}\n") || result.endsWith("\"reply_code\":0,\"request_uri\":\"/manage/self/userinfo/modifypassword\"}\n")) {
            return true;
        }
        return false;
    }

}
