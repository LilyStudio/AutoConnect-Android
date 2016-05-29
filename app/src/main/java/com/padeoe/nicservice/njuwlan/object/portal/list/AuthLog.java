package com.padeoe.nicservice.njuwlan.object.portal.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.Base;
import com.padeoe.nicservice.njuwlan.object.portal.row.AuthLogRow;

/**
 * 该类表示<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>中查询认证信息获得的列表，该列表包含认证的每一次记录
 * 具体包含一个包含{@link AuthLogRow}对象的数组以及认证列表的总页数
 * @author padeoe
 * Date: 2015/9/23
 */
public class AuthLog extends Base {
    @JSONField(name = "rows")
    private AuthLogRow[] authLogRows;

    public AuthLog() {
    }

    public static AuthLog getFromJson(String jsonobject) {
        try {
            return JSON.parseObject(jsonobject, AuthLog.class);
        } catch (Exception e) {
            return null;
        }
    }

    public AuthLogRow[] getAuthLogRows() {
        return authLogRows;
    }

    public void setAuthLogRows(AuthLogRow[] authLogRows) {
        this.authLogRows = authLogRows;
    }

    @Override
    public String toString()
    {
        return "Authlog:[\n" +
                "total = "+total+",\n" +
                "reply_code = "+reply_code+",\n" +
                "reply_msg = "+reply_msg+",\n"+
                authLogRows.toString()+",\n]\n";
    }
}

