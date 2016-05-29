package com.padeoe.nicservice.njuwlan.object.bras;

/**
 * 该类表示的信息是用户在<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>中查询当前用户信息获得的信息{@link BrasIDInfo}中的一部分
 * @author padeoe
 * Date: 2016/3/12.
 */
public class BrasIDInfoRow {
    private String account_pay_type;

    private String service_id;

    private String remark;

    private String service_name;

    private String expirydate;

    private String next_service_name;

    private String last_service_name;

    private String account_no;

    private UserAppendDefine[] userAppendDefine;

    private String createtime;

    private String id;

    private String username;

    private String session_limit;

    private String account_balance;

    private String last_service_id;

    private String fullname;

    private String account_type;

    private String next_service_id;

    private String client_info;

    private String passwd_expirydate;

    private String bind_area_id;

    private String account_createTime;

    private String status;

    private String alias;

    private String certification_no;

    private String default_account_no;

    private String bind_area_name;

    private UserAppend[] userAppend;

    private String mobile;

    private String bind_mac;

    public String getAccount_pay_type() {
        return account_pay_type;
    }

    public void setAccount_pay_type(String account_pay_type) {
        this.account_pay_type = account_pay_type;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getNext_service_name() {
        return next_service_name;
    }

    public void setNext_service_name(String next_service_name) {
        this.next_service_name = next_service_name;
    }

    public String getLast_service_name() {
        return last_service_name;
    }

    public void setLast_service_name(String last_service_name) {
        this.last_service_name = last_service_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public UserAppendDefine[] getUserAppendDefine() {
        return userAppendDefine;
    }

    public void setUserAppendDefine(UserAppendDefine[] userAppendDefine) {
        this.userAppendDefine = userAppendDefine;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSession_limit() {
        return session_limit;
    }

    public void setSession_limit(String session_limit) {
        this.session_limit = session_limit;
    }

    public String getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(String account_balance) {
        this.account_balance = account_balance;
    }

    public String getLast_service_id() {
        return last_service_id;
    }

    public void setLast_service_id(String last_service_id) {
        this.last_service_id = last_service_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getNext_service_id() {
        return next_service_id;
    }

    public void setNext_service_id(String next_service_id) {
        this.next_service_id = next_service_id;
    }

    public String getClient_info() {
        return client_info;
    }

    public void setClient_info(String client_info) {
        this.client_info = client_info;
    }

    public String getPasswd_expirydate() {
        return passwd_expirydate;
    }

    public void setPasswd_expirydate(String passwd_expirydate) {
        this.passwd_expirydate = passwd_expirydate;
    }

    public String getBind_area_id() {
        return bind_area_id;
    }

    public void setBind_area_id(String bind_area_id) {
        this.bind_area_id = bind_area_id;
    }

    public String getAccount_createTime() {
        return account_createTime;
    }

    public void setAccount_createTime(String account_createTime) {
        this.account_createTime = account_createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCertification_no() {
        return certification_no;
    }

    public void setCertification_no(String certification_no) {
        this.certification_no = certification_no;
    }

    public String getDefault_account_no() {
        return default_account_no;
    }

    public void setDefault_account_no(String default_account_no) {
        this.default_account_no = default_account_no;
    }

    public String getBind_area_name() {
        return bind_area_name;
    }

    public void setBind_area_name(String bind_area_name) {
        this.bind_area_name = bind_area_name;
    }

    public UserAppend[] getUserAppend() {
        return userAppend;
    }

    public void setUserAppend(UserAppend[] userAppend) {
        this.userAppend = userAppend;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBind_mac() {
        return bind_mac;
    }

    public void setBind_mac(String bind_mac) {
        this.bind_mac = bind_mac;
    }

    @Override
    public String toString() {
        return "ClassPojo [account_pay_type = " + account_pay_type + ", service_id = " + service_id + ", remark = " + remark + ", service_name = " + service_name + ", expirydate = " + expirydate + ", next_service_name = " + next_service_name + ", last_service_name = " + last_service_name + ", account_no = " + account_no + ", userAppendDefine = " + userAppendDefine + ", createtime = " + createtime + ", id = " + id + ", username = " + username + ", session_limit = " + session_limit + ", account_balance = " + account_balance + ", last_service_id = " + last_service_id + ", fullname = " + fullname + ", account_type = " + account_type + ", next_service_id = " + next_service_id + ", client_info = " + client_info + ", passwd_expirydate = " + passwd_expirydate + ", bind_area_id = " + bind_area_id + ", account_createTime = " + account_createTime + ", status = " + status + ", alias = " + alias + ", certification_no = " + certification_no + ", default_account_no = " + default_account_no + ", bind_area_name = " + bind_area_name + ", userAppend = " + userAppend + ", mobile = " + mobile + ", bind_mac = " + bind_mac + "]";
    }
}
