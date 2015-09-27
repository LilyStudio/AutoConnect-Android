package com.padeoe.nicservice.njuwlan.object.bras.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.bras.Base;
import com.padeoe.nicservice.njuwlan.object.bras.row.OnlineRowBras;

/**
 * Created by padeoe on 2015/9/24.
 */
public class OnlineBras extends Base
{
    @JSONField(name = "rows")
    private OnlineRowBras[] onlineRowBrases;
    protected String pageNum;

    public OnlineBras(){}
    public static OnlineBras getFromJson(String jsonobject){
        try {
            OnlineBras onlineBras = JSON.parseObject(jsonobject, OnlineBras.class);
            return onlineBras;
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

    public OnlineRowBras[] getOnlineRowBrases() {
        return onlineRowBrases;
    }

    public void setOnlineRowBrases(OnlineRowBras[] onlineRowBrases) {
        this.onlineRowBrases = onlineRowBrases;
    }

    @Override
    public String toString()
    {
        return "OnlineBras:[\n" +
                "max_page = "+max_page+",\n" +
                "reply_code = "+reply_code+",\n" +
                "reply_msg = "+reply_msg+",\n"+
                "pageNum = "+pageNum+",\n"+
                "request_time = "+request_time+",\n"+
                "request_url = "+request_url+",\n"+
                onlineRowBrases.toString()+",\n]\n";
    }
}
