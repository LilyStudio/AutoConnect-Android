package com.padeoe.nicservice.njuwlan.object;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 详单信息的行
 */
public class AcctRow {
    @JSONField(name = "pvlan")
    private String pvlan;
    @JSONField(name = "service_id")
    private String service_id;
    @JSONField(name = "area_id")
    private String area_id;
    @JSONField(name = "acctsessiontime")
    private String acctsessiontime;
    @JSONField(name = "mac")
    private String mac;
    @JSONField(name = "service_name")
    private String service_name;
    @JSONField(name = "ap_id")
    private String ap_id;
    @JSONField(name = "area_type")
    private String area_type;
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "username")
    private String username;
    @JSONField(name = "acctstarttime")
    private String acctstarttime;
    @JSONField(name = "area_name")
    private String area_name;
    @JSONField(name = "user_id")
    private String user_id;
    @JSONField(name = "acctoutputoctets_ipv4")
    private String acctoutputoctets_ipv4;
    @JSONField(name = "user_ipv4")
    private String user_ipv4;
    @JSONField(name = "refer_ipv6")
    private String refer_ipv6;
    @JSONField(name = "fullname")
    private String fullname;
    @JSONField(name = "acctoutputoctets_ipv6")
    private String acctoutputoctets_ipv6;
    @JSONField(name = "user_ipv6")
    private String user_ipv6;
    @JSONField(name = "refer_ipv4")
    private String refer_ipv4;
    @JSONField(name = "acctinputoctets_ipv6")
    private String acctinputoctets_ipv6;
    @JSONField(name = "acctinputoctets_ipv4")
    private String acctinputoctets_ipv4;
    @JSONField(name = "subport")
    private String subport;
    @JSONField(name = "acctsessionid")
    private String acctsessionid;
    @JSONField(name = "amount_ipv6")
    private String amount_ipv6;
    @JSONField(name = "acctstoptime")
    private String acctstoptime;
    @JSONField(name = "amount_ipv4")
    private String amount_ipv4;
    @JSONField(name = "svlan")
    private String svlan;
    @JSONField(name = "acctterminatecause")
    private String acctterminatecause;
    @JSONField(name = "src_ip")
    private String src_ip;
    @JSONField(name = "nas_ip")
    private String nas_ip;

    public String getPvlan() {
        return pvlan;
    }

    public void setPvlan(String pvlan) {
        this.pvlan = pvlan;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getAcctsessiontime() {
        return acctsessiontime;
    }

    public void setAcctsessiontime(String acctsessiontime) {
        this.acctsessiontime = acctsessiontime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getAp_id() {
        return ap_id;
    }

    public void setAp_id(String ap_id) {
        this.ap_id = ap_id;
    }

    public String getArea_type() {
        return area_type;
    }

    public void setArea_type(String area_type) {
        this.area_type = area_type;
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

    public String getAcctstarttime() {
        return acctstarttime;
    }

    public void setAcctstarttime(String acctstarttime) {
        this.acctstarttime = acctstarttime;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAcctoutputoctets_ipv4() {
        return acctoutputoctets_ipv4;
    }

    public void setAcctoutputoctets_ipv4(String acctoutputoctets_ipv4) {
        this.acctoutputoctets_ipv4 = acctoutputoctets_ipv4;
    }

    public String getUser_ipv4() {
        return user_ipv4;
    }

    public void setUser_ipv4(String user_ipv4) {
        this.user_ipv4 = user_ipv4;
    }

    public String getRefer_ipv6() {
        return refer_ipv6;
    }

    public void setRefer_ipv6(String refer_ipv6) {
        this.refer_ipv6 = refer_ipv6;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAcctoutputoctets_ipv6() {
        return acctoutputoctets_ipv6;
    }

    public void setAcctoutputoctets_ipv6(String acctoutputoctets_ipv6) {
        this.acctoutputoctets_ipv6 = acctoutputoctets_ipv6;
    }

    public String getUser_ipv6() {
        return user_ipv6;
    }

    public void setUser_ipv6(String user_ipv6) {
        this.user_ipv6 = user_ipv6;
    }

    public String getRefer_ipv4() {
        return refer_ipv4;
    }

    public void setRefer_ipv4(String refer_ipv4) {
        this.refer_ipv4 = refer_ipv4;
    }

    public String getAcctinputoctets_ipv6() {
        return acctinputoctets_ipv6;
    }

    public void setAcctinputoctets_ipv6(String acctinputoctets_ipv6) {
        this.acctinputoctets_ipv6 = acctinputoctets_ipv6;
    }

    public String getAcctinputoctets_ipv4() {
        return acctinputoctets_ipv4;
    }

    public void setAcctinputoctets_ipv4(String acctinputoctets_ipv4) {
        this.acctinputoctets_ipv4 = acctinputoctets_ipv4;
    }

    public String getSubport() {
        return subport;
    }

    public void setSubport(String subport) {
        this.subport = subport;
    }

    public String getAcctsessionid() {
        return acctsessionid;
    }

    public void setAcctsessionid(String acctsessionid) {
        this.acctsessionid = acctsessionid;
    }

    public String getAmount_ipv6() {
        return amount_ipv6;
    }

    public void setAmount_ipv6(String amount_ipv6) {
        this.amount_ipv6 = amount_ipv6;
    }

    public String getAcctstoptime() {
        return acctstoptime;
    }

    public void setAcctstoptime(String acctstoptime) {
        this.acctstoptime = acctstoptime;
    }

    public String getAmount_ipv4() {
        return amount_ipv4;
    }

    public void setAmount_ipv4(String amount_ipv4) {
        this.amount_ipv4 = amount_ipv4;
    }

    public String getSvlan() {
        return svlan;
    }

    public void setSvlan(String svlan) {
        this.svlan = svlan;
    }

    public String getAcctterminatecause() {
        return acctterminatecause;
    }

    public void setAcctterminatecause(String acctterminatecause) {
        this.acctterminatecause = acctterminatecause;
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

    @Override
    public String toString() {
        return "ClassPojo [pvlan = " + pvlan + ", service_id = " + service_id + ", area_id = " + area_id + ", acctsessiontime = " + acctsessiontime + ", mac = " + mac + ", service_name = " + service_name + ", ap_id = " + ap_id + ", area_type = " + area_type + ", id = " + id + ", username = " + username + ", acctstarttime = " + acctstarttime + ", area_name = " + area_name + ", user_id = " + user_id + ", acctoutputoctets_ipv4 = " + acctoutputoctets_ipv4 + ", user_ipv4 = " + user_ipv4 + ", refer_ipv6 = " + refer_ipv6 + ", fullname = " + fullname + ", acctoutputoctets_ipv6 = " + acctoutputoctets_ipv6 + ", user_ipv6 = " + user_ipv6 + ", refer_ipv4 = " + refer_ipv4 + ", acctinputoctets_ipv6 = " + acctinputoctets_ipv6 + ", acctinputoctets_ipv4 = " + acctinputoctets_ipv4 + ", subport = " + subport + ", acctsessionid = " + acctsessionid + ", amount_ipv6 = " + amount_ipv6 + ", acctstoptime = " + acctstoptime + ", amount_ipv4 = " + amount_ipv4 + ", svlan = " + svlan + ", acctterminatecause = " + acctterminatecause + ", src_ip = " + src_ip + ", nas_ip = " + nas_ip + "]";
    }
}
