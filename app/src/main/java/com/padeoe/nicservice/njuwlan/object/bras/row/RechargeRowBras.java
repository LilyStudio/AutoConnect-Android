package com.padeoe.nicservice.njuwlan.object.bras.row;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 该类表示<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>中查询充值记录获得的充值记录列表中具体的一条(行)信息
 * 具体包括充值金额，充值时间等信息
 * @author padeoe
 * Date: 2015/9/23
 */
public class RechargeRowBras {

    protected String amount;

    protected String remark;

    protected String oper_time;

    protected String oper_id;

    protected String account_no;

    @JSONField(name = "oper_name")
    protected String oper_username;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOper_time() {
        return oper_time;
    }

    public void setOper_time(String oper_time) {
        this.oper_time = oper_time;
    }

    public String getOper_id() {
        return oper_id;
    }

    public void setOper_id(String oper_id) {
        this.oper_id = oper_id;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getOper_username() {
        return oper_username;
    }

    public void setOper_username(String oper_username) {
        this.oper_username = oper_username;
    }
    @Override
    public String toString() {
        return "ClassPojo " + ", amount = " + amount + ", remark = " + remark + ", oper_time = " + oper_time + ", oper_id = " + oper_id + ", account_no = " + account_no + "]";
    }
}
