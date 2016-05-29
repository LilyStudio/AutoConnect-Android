package com.padeoe.utils;

/**
 * 该类用于表示各种网站登陆时的登陆失败
 * @author padeoe
 * Date: 2016/4/7
 */
public class LoginException extends Exception {
    /**
     *
     * @param message 服务器返回的失败信息
     */
    public LoginException(String message) {
        super(message);
    }

    public LoginException() {
    }
}
