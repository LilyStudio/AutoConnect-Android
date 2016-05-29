package com.padeoe.nicservice.njuwlan.object.bras.row;

/**
 * 该类表示<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>中查询账单信息获得的详单信息列表中具体的一条(行)信息
 * 具体包括起始时间，id，消费金额等信息
 * @author padeoe
 * Date: 2015/9/23
 */
public class BillsRowBras {
    protected String createtime;

    protected String id;

    protected String beginning_balance;

    protected String startdate;

    protected String recharge_amount;

    protected String ending_balance;

    protected String enddate;

    protected String costs_amount;

    protected String account_no;

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
