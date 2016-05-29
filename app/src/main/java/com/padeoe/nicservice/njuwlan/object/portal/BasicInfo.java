package com.padeoe.nicservice.njuwlan.object.portal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.padeoe.nicservice.njuwlan.object.portal.row.BasicInfoRow;

/**
 * 该类表示<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>中查询当前登陆用户获得的用户信息列表
 * @author padeoe
 * Date: 2015/9/23
 */
public class BasicInfo extends Base {
    @JSONField(name = "rows")
    private BasicInfoRow[] basicInfoRows;

    public static BasicInfo getFromJson(String jsonobject) {
        try {
            return JSON.parseObject(jsonobject, BasicInfo.class);
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