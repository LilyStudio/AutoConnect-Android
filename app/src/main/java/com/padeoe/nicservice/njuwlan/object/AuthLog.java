package com.padeoe.nicservice.njuwlan.object;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

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
    public String toString() {
        return "ClassPojo [total = " + total + ", reply_code = " + reply_code + ", rows = " + authLogRows + ", reply_msg = " + reply_msg + "]";
    }
}

