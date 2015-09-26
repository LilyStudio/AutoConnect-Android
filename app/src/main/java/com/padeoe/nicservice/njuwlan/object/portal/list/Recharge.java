package com.padeoe.nicservice.njuwlan.object.portal.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.Base;
import com.padeoe.nicservice.njuwlan.object.portal.row.RechargeRow;

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
    public static Recharge getFromJson(String jsonobject){
        try {
            Recharge recharge = JSON.parseObject(jsonobject, Recharge.class);
            return recharge;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public String toString()
    {
        return "Recharge:[\n" +
                "total = "+total+",\n" +
                "reply_code = "+reply_code+",\n" +
                "reply_msg = "+reply_msg+",\n"+
                rechargeRows.toString()+",\n]\n";
    }
}
