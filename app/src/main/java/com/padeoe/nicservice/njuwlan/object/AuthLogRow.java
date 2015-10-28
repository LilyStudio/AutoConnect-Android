package com.padeoe.nicservice.njuwlan.object;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 登陆日志的行
 */
public class AuthLogRow {
    @JSONField(name = "pvlan")
    private String pvlan;
    @JSONField(name = "area_id")
    private String area_id;
    @JSONField(name = "subport")
    private String subport;
    @JSONField(name = "reply_code")
    private String reply_code;
    @JSONField(name = "acctsessionid")
    private String acctsessionid;
    @JSONField(name = "mac")
    private String mac;
    @JSONField(name = "ap_id")
    private String ap_id;
    @JSONField(name = "reply_msg")
    private String reply_msg;
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "area_type")
    private String area_type;
    @JSONField(name = "svlan")
    private String svlan;
    @JSONField(name = "username")
    private String username;
    @JSONField(name = "area_name")
    private String area_name;
    @JSONField(name = "src_ip")
    private String src_ip;
    @JSONField(name = "nas_ip")
    private String nas_ip;
    @JSONField(name = "datetime")
    private String datetime;
    @JSONField(name = "area_type_name")
    private String area_type_name;

    public AuthLogRow() {
    }

    public String getPvlan() {
        return pvlan;
    }

    public void setPvlan(String pvlan) {
        this.pvlan = pvlan;
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

    public String getReply_code() {
        return reply_code;
    }

    public void setReply_code(String reply_code) {
        this.reply_code = reply_code;
    }

    public String getAcctsessionid() {
        return acctsessionid;
    }

    public void setAcctsessionid(String acctsessionid) {
        this.acctsessionid = acctsessionid;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getArea_type_name() {
        return area_type_name;
    }

    public void setArea_type_name(String area_type_name) {
        this.area_type_name = area_type_name;
    }

    @Override
    public String toString() {
        return "ClassPojo [pvlan = " + pvlan + ", area_id = " + area_id + ", subport = " + subport + ", reply_code = " + reply_code + ", acctsessionid = " + acctsessionid + ", mac = " + mac + ", ap_id = " + ap_id + ", reply_msg = " + reply_msg + ", id = " + id + ", area_type = " + area_type + ", svlan = " + svlan + ", username = " + username + ", area_name = " + area_name + ", src_ip = " + src_ip + ", nas_ip = " + nas_ip + ", datetime = " + datetime + ", area_type_name = " + area_type_name + "]";
    }
}


