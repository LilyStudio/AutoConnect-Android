package com.padeoe.nicservice.njuwlan.object.bras.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.bras.Base;
import com.padeoe.nicservice.njuwlan.object.bras.row.AcctRowBras;

/**
 *
 * 该类表示<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>中查询详单信息获得的列表列表,该列表显示了每一次登陆至下线的详细信息，
 * 具体包含一个包含{@link AcctRowBras}对象的数组以及详单列表的总页数
 * @author padeoe
 * Date: 2015/9/23
 */
public class AcctBras extends Base {
    /**
     * 详单信息的列表，数组每一个元素包含了一个{@link AcctRowBras}，记录了具体的单个详细信息
     */
    @JSONField(name = "rows")
    private AcctRowBras[] acctRowBrases;
    /**
     * 详单信息列表的页号
     */
    protected String pageNum;
    public AcctBras() {
    }

    /**
     *
     * @param jsonobject json格式的字符串
     * @return {@link AcctBras}对象
     */
    public static AcctBras getFromJson(String jsonobject) {
        try {
            return JSON.parseObject(jsonobject, AcctBras.class);
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

