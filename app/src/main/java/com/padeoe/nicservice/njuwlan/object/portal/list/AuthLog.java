package com.padeoe.nicservice.njuwlan.object.portal.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.Base;
import com.padeoe.nicservice.njuwlan.object.portal.row.AuthLogRow;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 认证信息
 */
public class AuthLog extends Base {
    @JSONField(name = "rows")
    private AuthLogRow[] authLogRows;

    public AuthLog() {
    }

    public static AuthLog getFromJson(String jsonobject) {
        try {
            AuthLog authLog = JSON.parseObject(jsonobject, AuthLog.class);
            return authLog;
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

