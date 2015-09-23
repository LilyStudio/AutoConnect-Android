package com.padeoe.nicservice.njuwlan.object;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 当前登录基本信息的行
 */
public class BillsRow {
    private String createtime;

    private String id;

    private String beginning_balance;

    private String startdate;

    private String recharge_amount;

    private String ending_balance;

    private String enddate;

    private String costs_amount;

    private String account_no;

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

    public String getBeginning_balance() {
        return beginning_balance;
    }

    public void setBeginning_balance(String beginning_balance) {
        this.beginning_balance = beginning_balance;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(String recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public String getEnding_balance() {
        return ending_balance;
    }

    public void setEnding_balance(String ending_balance) {
        this.ending_balance = ending_balance;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getCosts_amount() {
        return costs_amount;
    }

    public void setCosts_amount(String costs_amount) {
        this.costs_amount = costs_amount;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    @Override
    public String toString() {
        return "ClassPojo [createtime = " + createtime + ", id = " + id + ", beginning_balance = " + beginning_balance + ", startdate = " + startdate + ", recharge_amount = " + recharge_amount + ", ending_balance = " + ending_balance + ", enddate = " + enddate + ", costs_amount = " + costs_amount + ", account_no = " + account_no + "]";
    }
}
