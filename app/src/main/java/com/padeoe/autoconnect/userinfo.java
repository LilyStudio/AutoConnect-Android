package com.padeoe.autoconnect;

import com.google.gson.annotations.SerializedName;

/**
 * Created by padeoe on 4/24/15.
 */

/**
 * p.nju.edu.cn的返回信息对象中关于用户的信息
 */
public class userinfo {
    @SerializedName("username")
    private String username;
    @SerializedName("userip")
    private String userip;
    @SerializedName("useripv6")
    private String useripv6;
    @SerializedName("mac")
    private String mac;
    @SerializedName("acctstarttime")
    private String acctstarttime;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("service_name")
    private String service_name;
    @SerializedName("area_name")
    private String area_name;
    @SerializedName("payamount")
    private String payamount;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getUserip() {
        return this.userip;
    }

    public void setUseripv6(String useripv6) {
        this.useripv6 = useripv6;
    }

    public String getUseripv6() {
        return this.useripv6;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return this.mac;
    }

    public void setAcctstarttime(String acctstarttime) {
        this.acctstarttime = acctstarttime;
    }

    public String getAcctstarttime() {
        return this.acctstarttime;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_name() {
        return this.service_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getArea_name() {
        return this.area_name;
    }

    public void setPayamount(String payamount) {
        this.payamount = payamount;
    }

    public String getPayamount() {
        return this.payamount;
    }

}