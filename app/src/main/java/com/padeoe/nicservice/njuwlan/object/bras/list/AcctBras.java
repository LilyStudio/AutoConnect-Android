package com.padeoe.nicservice.njuwlan.object.bras.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.bras.Base;
import com.padeoe.nicservice.njuwlan.object.bras.row.AcctRowBras;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 详单信息
 */
public class AcctBras extends Base {
    @JSONField(name = "rows")
    private AcctRowBras[] acctRowBrases;
    protected String pageNum;
    public AcctBras() {
    }

    public static AcctBras getFromJson(String jsonobject) {
        try {
            AcctBras acctBras = JSON.parseObject(jsonobject, AcctBras.class);
            return acctBras;
        } catch (Exception e) {
            return null;
        }
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public AcctRowBras[] getAcctRowBrases() {
        return acctRowBrases;
    }

    public void setAcctRowBrases(AcctRowBras[] acctRowBrases) {
        this.acctRowBrases = acctRowBrases;
    }

    @Override
    public String toString()
    {
        return "AcctBras:[\n" +
                "max_page = "+max_page+",\n" +
                "reply_code = "+reply_code+",\n" +
                "reply_msg = "+reply_msg+",\n"+
                "pageNum = "+pageNum+",\n"+
                "request_time = "+request_time+",\n"+
                "request_url = "+request_url+",\n"+
                acctRowBrases.toString()+",\n]\n";
    }
}

