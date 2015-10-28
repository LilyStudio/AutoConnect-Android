package com.padeoe.nicservice.njuwlan.object;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 充值信息
 */
public class Recharge extends Base {
    @JSONField(name = "rows")
    private RechargeRow[] rechargeRows;

    public RechargeRow[] getRechargeRows() {
        return rechargeRows;
    }

    public void setRechargeRows(RechargeRow[] rechargeRows) {
        this.rechargeRows = rechargeRows;
    }

    @Override
    public String toString() {
        return "ClassPojo [total = " + total + ", reply_code = " + reply_code + ", rows = " + rechargeRows + ", reply_msg = " + reply_msg + "]";
    }
}
