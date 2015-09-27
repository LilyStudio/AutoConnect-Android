package com.padeoe.nicservice.njuwlan.object.bras;

/**
 * Created by padeoe on 2015/9/23.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 基类
 */
public class Base {
    protected String max_page;

    protected String reply_code;

    protected String reply_msg;



    protected String request_time;

    protected String request_url;

    public String getMax_page() {
        return max_page;
    }

    public void setMax_page(String max_page) {
        this.max_page = max_page;
    }


    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getRequest_url() {
        return request_url;
    }

    public void setRequest_url(String request_url) {
        this.request_url = request_url;
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
