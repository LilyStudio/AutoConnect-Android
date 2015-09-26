package com.padeoe.nicservice.njuwlan.object.portal;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by padeoe on 4/24/15.
 * Modified on 9/17/2015
 */

/**
 * 用户信息
 */
public class UserInfo {
    @JSONField(name = "username")
    private String username;
    @JSONField(name = "fullname")
    private String fullname;
    @JSONField(name = "service_name")
    private String service_name;
    @JSONField(name = "area_name")
    private String area_name;
    @JSONField(name = "acctstarttime")
    private String acctstarttime;
    @JSONField(name = "balance")
    private String balance;
    @JSONField(name = "useripv4")
    private String useripv4;
    @JSONField(name = "useripv6")
    private String useripv6;
    @JSONField(name = "mac")
    private String mac;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getAcctstarttime() {
        return acctstarttime;
    }

    public void setAcctstarttime(String acctstarttime) {
        this.acctstarttime = acctstarttime;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUseripv4() {
        return useripv4;
    }

    public void setUseripv4(String useripv4) {
        this.useripv4 = useripv4;
    }

    public String getUseripv6() {
        return useripv6;
    }

    public void setUseripv6(String useripv6) {
        this.useripv6 = useripv6;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
