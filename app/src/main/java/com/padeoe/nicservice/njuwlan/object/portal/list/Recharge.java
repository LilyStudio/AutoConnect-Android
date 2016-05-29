package com.padeoe.nicservice.njuwlan.object.portal.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.Base;
import com.padeoe.nicservice.njuwlan.object.portal.row.RechargeRow;

/**
 * 该类表示<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>中查询充值信息获得的列表
 * 具体包含一个包含{@link RechargeRow}对象的数组以及充值列表的总页数
 * @author padeoe
 * Date: 2015/9/23
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
            return JSON.parseObject(jsonobject, Recharge.class);
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
