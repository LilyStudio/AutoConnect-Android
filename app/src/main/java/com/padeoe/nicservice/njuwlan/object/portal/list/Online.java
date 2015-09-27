package com.padeoe.nicservice.njuwlan.object.portal.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.Base;
import com.padeoe.nicservice.njuwlan.object.portal.row.OnlineRow;

/**
 * Created by padeoe on 2015/9/24.
 */
public class Online extends Base
{
    @JSONField(name = "rows")
    private OnlineRow[] onlineRows;

    public Online(){}
    public static Online getFromJson(String jsonobject){
        try {
            Online online = JSON.parseObject(jsonobject, Online.class);
            return online;
        } catch (Exception e) {
            return null;
        }
    }


    public OnlineRow[] getOnlineRows() {
        return onlineRows;
    }

    public void setOnlineRows(OnlineRow[] onlineRows) {
        this.onlineRows = onlineRows;
    }

    @Override
    public String toString()
    {
        return "Online:[\n" +
                "total = "+total+",\n" +
                "reply_code = "+reply_code+",\n" +
                "reply_msg = "+reply_msg+",\n"+
                onlineRows.toString()+",\n]\n";
    }
}
