package com.padeoe.nicservice.njuwlan.object.bras.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.bras.Base;
import com.padeoe.nicservice.njuwlan.object.bras.row.RechargeRowBras;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 充值信息
 */
public class RechargeBras extends Base {
    @JSONField(name = "rows")
    private RechargeRowBras[] rechargeRowBrases;
    protected String pageNum;

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public RechargeRowBras[] getRechargeRowBrases() {
        return rechargeRowBrases;
    }

    public void setRechargeRowBrases(RechargeRowBras[] rechargeRowBrases) {
        this.rechargeRowBrases = rechargeRowBrases;
    }

    public static RechargeBras getFromJson(String jsonobject){
        try {
            RechargeBras rechargeBras = JSON.parseObject(jsonobject, RechargeBras.class);
            return rechargeBras;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString()
    {
        return "RechargeBras:[\n" +
                "max_page = "+max_page+",\n" +
                "reply_code = "+reply_code+",\n" +
                "reply_msg = "+reply_msg+",\n"+
                "pageNum = "+pageNum+",\n"+
                "request_time = "+request_time+",\n"+
                "request_url = "+request_url+",\n"+
                rechargeRowBrases.toString()+",\n]\n";
    }
}
