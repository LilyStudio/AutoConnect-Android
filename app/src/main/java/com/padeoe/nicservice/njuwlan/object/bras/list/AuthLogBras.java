package com.padeoe.nicservice.njuwlan.object.bras.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.bras.Base;
import com.padeoe.nicservice.njuwlan.object.bras.row.AuthLogRowBras;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 认证信息
 */
public class AuthLogBras extends Base {
    @JSONField(name = "rows")
    private AuthLogRowBras[] authLogRowBrases;
    @JSONField(name = "page")
    protected String pageNum;

    public AuthLogBras() {
    }

    public static AuthLogBras getFromJson(String jsonobject) {
        try {
            AuthLogBras authLogBras = JSON.parseObject(jsonobject, AuthLogBras.class);
            return authLogBras;
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
    public AuthLogRowBras[] getAuthLogRowBrases() {
        return authLogRowBrases;
    }

    public void setAuthLogRowBrases(AuthLogRowBras[] authLogRowBrases) {
        this.authLogRowBrases = authLogRowBrases;
    }

    @Override
    public String toString()
    {
        return "AuthLogBras:[\n" +
                "max_page = "+max_page+",\n" +
                "reply_code = "+reply_code+",\n" +
                "reply_msg = "+reply_msg+",\n"+
                "pageNum = "+pageNum+",\n"+
                "request_time = "+request_time+",\n"+
                "request_url = "+request_url+",\n"+
                authLogRowBrases.toString()+",\n]\n";
    }
}

