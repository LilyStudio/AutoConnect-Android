package com.padeoe.nicservice.njuwlan.object;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by padeoe on 2015/9/23.
 */

/**
 * 详单信息
 */
public class Acct extends Base {
    @JSONField(name = "rows")
    private AcctRow[] acctRows;

    public Acct() {
    }

    public static Acct getFromJson(String jsonobject) {
        try {
            Acct acct = JSON.parseObject(jsonobject, Acct.class);
            return acct;
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
    public String toString() {
        return "ClassPojo [total = " + total + ", reply_code = " + reply_code + ", rows = " + acctRows + ", reply_msg = " + reply_msg + "]";
    }
}

