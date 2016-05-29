package com.padeoe.nicservice.njuwlan.object.portal.row;

import com.padeoe.nicservice.njuwlan.object.bras.row.RechargeRowBras;

/**
 * 该类表示<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>中查询充值记录获得的充值记录列表中具体的一条(行)信息
 * 具体包括充值金额，充值时间等信息
 * @author padeoe
 * Date: 2015/9/23
 */
public class RechargeRow extends RechargeRowBras{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", amount = " + amount + ", remark = " + remark + ", oper_time = " + oper_time + ", oper_username = " + oper_username + ", oper_id = " + oper_id + ", account_no = " + account_no + "]";
    }
}
