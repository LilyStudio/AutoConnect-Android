package com.padeoe.nicservice.njuwlan.object.portal;

/**
 * Created by padeoe on 2015/9/23.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.row.BasicInfoRow;

/**
 * 当前登陆信息
 */
public class BasicInfo extends Base {
    @JSONField(name = "rows")
    private BasicInfoRow[] basicInfoRows;

    public static BasicInfo getFromJson(String jsonobject) {
        try {
            BasicInfo basicInfo = JSON.parseObject(jsonobject, BasicInfo.class);
            return basicInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public BasicInfoRow[] getBasicInfoRows() {
        return basicInfoRows;
    }

    public void setBasicInfoRows(BasicInfoRow[] basicInfoRows) {
        this.basicInfoRows = basicInfoRows;
    }


    @Override
    public String toString() {
        return "ClassPojo [total = " + total + ", reply_code = " + reply_code + ", rows = " + basicInfoRows + ", reply_msg = " + reply_msg + "]";
    }
}