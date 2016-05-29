package com.padeoe.nicservice.njuwlan.object.portal.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.Base;
import com.padeoe.nicservice.njuwlan.object.portal.row.OnlineRow;

/**
 * 该类表示<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>中查询当前在线设备获得的列表
 * 具体包含一个包含{@link OnlineRow}对象的数组以及在线设备列表的总页数
 * @author padeoe
 * Date: 2015/9/24
 */
public class Online extends Base
{
    @JSONField(name = "rows")
    private OnlineRow[] onlineRows;

    public Online(){}
    public static Online getFromJson(String jsonobject){
        try {
            return JSON.parseObject(jsonobject, Online.class);
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
