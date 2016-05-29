package com.padeoe.nicservice.njuwlan.object.portal.row;

import com.padeoe.nicservice.njuwlan.object.bras.row.AuthLogRowBras;

/**
 * 该类表示<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>中查询登陆日志详单获得的详单信息列表中具体的一条(行)信息
 * 包括登陆区域，mac地址，登陆返回信息，用户名等大量信息
 * @author padeoe
 * Date: 2015/9/23
 */
public class AuthLogRow extends AuthLogRowBras {
    protected String pvlan;
    protected String reply_code;
    protected String acctsessionid;
    protected String id;
    protected String area_type_name;

    public AuthLogRow() {
    }

    public String getPvlan() {
        return pvlan;
    }

    public void setPvlan(String pvlan) {
        this.pvlan = pvlan;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


