package com.padeoe.nicservice.njuwlan.object;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 充值信息的行
 */
public class RechargeRow {
    private String id;

    private String amount;

    private String remark;

    private String oper_time;

    private String oper_username;

    private String oper_id;

    private String account_no;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getOper_username() {
        return oper_username;
    }

    public void setOper_username(String oper_username) {
        this.oper_username = oper_username;
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

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", amount = " + amount + ", remark = " + remark + ", oper_time = " + oper_time + ", oper_username = " + oper_username + ", oper_id = " + oper_id + ", account_no = " + account_no + "]";
    }
}
