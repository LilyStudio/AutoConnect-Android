package com.padeoe.nicservice.njuwlan.object.portal.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.Base;
import com.padeoe.nicservice.njuwlan.object.portal.row.AcctRow;

/**
 *
 * 该类表示<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>中查询详单信息获得的列表列表,该列表显示了每一次登陆至下线的详细信息，
 * 具体包含一个包含{@link AcctRow}对象的数组以及详单列表的总页数
 * @author padeoe
 * Date: 2015/9/23
 */
public class Acct extends Base {
    @JSONField(name = "rows")
    private AcctRow[] acctRows;

    public Acct() {
    }

    public static Acct getFromJson(String jsonobject) {
        try {
            return JSON.parseObject(jsonobject, Acct.class);
        } catch (Exception e) {
            return null;
        }
    }

    public AcctRow[] getAcctRows() {
        return acctRows;
    }

    public void setAcctRows(AcctRow[] acctRows) {
        this.acctRows = acctRows;
    }

    @Override
    public String toString()
    {
        return "Acct:[\n" +
                "total = "+total+",\n" +
                "reply_code = "+reply_code+",\n" +
                "reply_msg = "+reply_msg+",\n"+
                acctRows.toString()+",\n]\n";
    }
}

