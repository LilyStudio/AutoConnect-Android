package com.padeoe.nicservice.njuwlan.object.bras;

/**
 * 该类表示的信息是用户在<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>中查询当前用户信息获得的信息{@link BrasIDInfo}中的一部分
 * Created by padeoe on 2016/3/12.
 */
public class UserAppendDefine
{
    private String colName;

    private String colValue;

    public String getColName ()
    {
        return colName;
    }

    public void setColName (String colName)
    {
        this.colName = colName;
    }

    public String getColValue ()
    {
        return colValue;
    }

    public void setColValue (String colValue)
    {
        this.colValue = colValue;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [colName = "+colName+", colValue = "+colValue+"]";
    }
}
