package com.padeoe.nicservice.njuwlan.object.bras.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.bras.Base;
import com.padeoe.nicservice.njuwlan.object.bras.row.AuthLogRowBras;

/**
 * 该类表示<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>中查询认证信息获得的列表，该列表包含认证的每一次记录
 * 具体包含一个包含{@link AuthLogRowBras}对象的数组以及认证列表的总页数
 * @author padeoe
 * Date: 2015/9/24
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
            return JSON.parseObject(jsonobject, AuthLogBras.class);
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

