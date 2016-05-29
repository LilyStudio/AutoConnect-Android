package com.padeoe.nicservice.njuwlan.object.bras.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.bras.Base;
import com.padeoe.nicservice.njuwlan.object.bras.row.OnlineRowBras;

/**
 * 该类表示<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>中查询当前在线设备获得的列表
 * 具体包含一个包含{@link OnlineRowBras}对象的数组以及在线设备列表的总页数
 * @author padeoe
 * Date: 2015/9/24
 */
public class OnlineBras extends Base
{
    @JSONField(name = "rows")
    private OnlineRowBras[] onlineRowBrases;

    protected String pageNum;

    public OnlineBras(){}
    public static OnlineBras getFromJson(String jsonobject){
        try {
            return JSON.parseObject(jsonobject, OnlineBras.class);
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
