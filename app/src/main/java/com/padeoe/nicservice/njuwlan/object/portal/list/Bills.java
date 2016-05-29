package com.padeoe.nicservice.njuwlan.object.portal.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.Base;
import com.padeoe.nicservice.njuwlan.object.portal.row.BillsRow;

/**
 * 该类表示<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>中查询账单信息获得的列表
 * 具体包含一个包含{@link BillsRow}对象的数组以及账单列表的总页数
 * @author padeoe
 * Date: 2015/9/23
 */
public class Bills extends Base {
    @JSONField(name = "rows")
    private BillsRow[] billsRows;

    public Bills() {
    }

    public static Bills getFromJson(String jsonobject) {
        try {
            return JSON.parseObject(jsonobject, Bills.class);
        } catch (Exception e) {
            return null;
        }
    }

    public BillsRow[] getBillsRows() {
        return billsRows;
    }

    public void setBillsRows(BillsRow[] billsRows) {
        this.billsRows = billsRows;
    }

    @Override
    public String toString()
    {
        return "Bills:[\n" +
                "total = "+total+",\n" +
                "reply_code = "+reply_code+",\n" +
                "reply_msg = "+reply_msg+",\n"+
                billsRows.toString()+",\n]\n";
    }
}


