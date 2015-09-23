package com.padeoe.nicservice.njuwlan.object;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by padeoe on 2015/9/23.
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
    public String toString() {
        return "ClassPojo [total = " + total + ", reply_code = " + reply_code + ", rows = " + billsRows + ", reply_msg = " + reply_msg + "]";
    }
}


