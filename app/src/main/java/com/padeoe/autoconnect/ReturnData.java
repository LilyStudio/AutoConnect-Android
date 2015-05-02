package com.padeoe.autoconnect;
import com.google.gson.annotations.SerializedName;

/**
 * Created by padeoe on 4/24/15.
 */

/**
 * p.nju.edu.cn的返回信息对象
 */
public class ReturnData{
    @SerializedName("reply_code")
    private String reply_code;
    @SerializedName("reply_message")
    private String reply_message;
    @SerializedName("userinfo")
    private userinfo userinfo;

    public void setReply_code(String reply_code){
        this.reply_code = reply_code;
    }
    public String getReply_code(){
        return this.reply_code;
    }
    public void setReply_message(String reply_message){
        this.reply_message = reply_message;
    }
    public String getReply_message(){
        return this.reply_message;
    }
    public void setUserinfo(userinfo userinfo){
        this.userinfo = userinfo;
    }
    public userinfo getUserinfo(){
        return this.userinfo;
    }

}