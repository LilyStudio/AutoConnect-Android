package com.padeoe.nicservice.njuwlan.object.portal;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 该类表示的信息是用户登陆<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>时返回的数据{@link ReturnData}的一部分，表示了当前登陆用户的简要信息
 * 具体包当前用户的用户名，服务名，余额等信息
 * @author padeoe
 * Date: 2015/4/24
 */
public class UserInfo implements Parcelable{
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

    public static UserInfo getFromJson(String jsonobject) {
        try {
            return JSON.parseObject(jsonobject, UserInfo.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.fullname);
        dest.writeString(this.service_name);
        dest.writeString(this.area_name);
        dest.writeString(this.acctstarttime);
        dest.writeString(this.balance);
        dest.writeString(this.useripv4);
        dest.writeString(this.useripv6);
        dest.writeString(this.mac);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.username = in.readString();
        this.fullname = in.readString();
        this.service_name = in.readString();
        this.area_name = in.readString();
        this.acctstarttime = in.readString();
        this.balance = in.readString();
        this.useripv4 = in.readString();
        this.useripv6 = in.readString();
        this.mac = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
