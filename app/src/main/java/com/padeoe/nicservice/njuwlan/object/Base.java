package com.padeoe.nicservice.njuwlan.object;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 基类
 */
public class Base {
    protected String total;

    protected String reply_code;

    protected String reply_msg;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getReply_code() {
        return reply_code;
    }

    public void setReply_code(String reply_code) {
        this.reply_code = reply_code;
    }

    public String getReply_msg() {
        return reply_msg;
    }

    public void setReply_msg(String reply_msg) {
        this.reply_msg = reply_msg;
    }
}
