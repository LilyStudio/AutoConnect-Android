package com.padeoe.nicservice.njuwlan.object.portal.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.Base;
import com.padeoe.nicservice.njuwlan.object.portal.row.BillsRow;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 账单信息
 */
public class Bills extends Base {
    @JSONField(name = "rows")
    private BillsRow[] billsRows;

    public Bills() {
    }

    public static Bills getFromJson(String jsonobject) {
        try {
            Bills bills = JSON.parseObject(jsonobject, Bills.class);
            return bills;
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


