package com.padeoe.nicservice.njuwlan.object.bras.row;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 登陆日志的行
 */
public class AuthLogRowBras {
    protected String area_id;
    protected String subport;
    protected String mac;
    protected String ap_id;
    protected String reply_msg;
    protected String area_type;
    protected String svlan;
    protected String username;
    protected String area_name;
    protected String src_ip;
    protected String nas_ip;
    protected String datetime;

    public AuthLogRowBras() {
    }


    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getSubport() {
        return subport;
    }

    public void setSubport(String subport) {
        this.subport = subport;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAp_id() {
        return ap_id;
    }

    public void setAp_id(String ap_id) {
        this.ap_id = ap_id;
    }

    public String getReply_msg() {
        return reply_msg;
    }

    public void setReply_msg(String reply_msg) {
        this.reply_msg = reply_msg;
    }


    public String getArea_type() {
        return area_type;
    }

    public void setArea_type(String area_type) {
        this.area_type = area_type;
    }

    public String getSvlan() {
        return svlan;
    }

    public void setSvlan(String svlan) {
        this.svlan = svlan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getSrc_ip() {
        return src_ip;
    }

    public void setSrc_ip(String src_ip) {
        this.src_ip = src_ip;
    }

    public String getNas_ip() {
        return nas_ip;
    }

    public void setNas_ip(String nas_ip) {
        this.nas_ip = nas_ip;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "ClassPojo [area_id = " + area_id + ", subport = " + subport +
                ", mac = " + mac + ", ap_id = " + ap_id + ", reply_msg = " + reply_msg +
                ", area_type = " + area_type + ", svlan = " + svlan + ", username = "
                + username + ", area_name = " + area_name + ", src_ip = " + src_ip + ", nas_ip = "
                + nas_ip + ", datetime = " + datetime + "]";
    }
}



