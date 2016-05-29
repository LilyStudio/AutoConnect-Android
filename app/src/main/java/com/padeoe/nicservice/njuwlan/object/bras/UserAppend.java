package com.padeoe.nicservice.njuwlan.object.bras;

/**
 * 该类表示的信息是用户在<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>中查询当前用户信息获得的信息{@link BrasIDInfo}中的一部分
 * @author padeoe
 * Date: 2016/3/12
 */
public class UserAppend
{
    private String userColName;

    private String userColValue;

    public String getUserColName ()
    {
        return userColName;
    }

    public void setUserColName (String userColName)
    {
        this.userColName = userColName;
    }

    public String getUserColValue ()
{
    return userColValue;
}

    public void setUserColValue (String userColValue)
    {
        this.userColValue = userColValue;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [userColName = "+userColName+", userColValue = "+userColValue+"]";
    }
}

