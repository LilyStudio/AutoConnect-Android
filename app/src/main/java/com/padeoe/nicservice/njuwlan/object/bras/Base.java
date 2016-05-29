package com.padeoe.nicservice.njuwlan.object.bras;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 基类
 */
public class Base {
    /**
     * 列表总行数
     */
    protected String total;
    /**
     * （无用信息）查询时间，即当前时间
     */
    protected String request_time;
    /**
     * 服务器返回信息，显示了查询是否成功
     */
    protected String reply_msg;
    /**
     * 列表最大页数
     */
    protected String max_page;
    /**
     * （无用信息）请求url
     */
    protected String request_url;
    /**
     * 返回码，显示了查询是否成功
     */
    protected String reply_code;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

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
