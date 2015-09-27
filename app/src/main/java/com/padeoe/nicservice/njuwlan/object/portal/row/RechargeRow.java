package com.padeoe.nicservice.njuwlan.object.portal.row;

/**
 * Created by padeoe on 2015/9/23.
 */

import com.padeoe.nicservice.njuwlan.object.bras.row.RechargeRowBras;

/**
 * 充值信息的行
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
