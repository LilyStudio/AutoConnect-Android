package com.padeoe.nicservice.njuwlan;

/**
 * Created by padeoe on 4/24/15.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * p.nju.edu.cn
 */
public class ReturnData {
    @JSONField(name = "reply_code")
    private String reply_code;
    @JSONField(name = "reply_msg")
    private String reply_message;
    @JSONField(name = "userinfo")
    private UserInfo userInfo;
    public ReturnData(){

    }
    public ReturnData(String reply_code,String reply_message, UserInfo userInfo){
        this.reply_code=reply_code;
        this.reply_message=reply_message;
        this.userInfo = userInfo;
    }
    public static ReturnData getFromJson(String jsonobject){
        return JSON.parseObject(jsonobject, ReturnData.class);
      //  return new Gson().fromJson(jsonobject,ReturnData.class);
    }
    public void setReply_code(String reply_code) {
        this.reply_code = reply_code;
    }

    public String getReply_code() {
        return this.reply_code;
    }

    public void setReply_message(String reply_message) {
        this.reply_message = reply_message;
    }

    public String getReply_message() {
        return this.reply_message;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

}