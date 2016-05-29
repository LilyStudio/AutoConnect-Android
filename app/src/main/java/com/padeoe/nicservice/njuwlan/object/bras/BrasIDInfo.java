package com.padeoe.nicservice.njuwlan.object.bras;

import com.alibaba.fastjson.JSON;

/**
 * 该类表示<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>中查询用户信息获得的用户信息列表，该列表包含了用户id，用户网络使用总时长，网费余额等信息
 * 具体包括一个包含用户信息列表的数组以及响应值，相应信息等信息
 * @author padeoe
 * Date: 2016/3/12
 */
public class BrasIDInfo
{
    private BrasIDInfoRow[] results;

    private String reply_code;

    private String request_uri;

    private String request_time;

    private String reply_msg;


    public static BrasIDInfo getFromJson(String jsonobject){
        try {
            return JSON.parseObject(jsonobject, BrasIDInfo.class);
        } catch (Exception e) {
            return null;
        }
    }

    public BrasIDInfoRow[] getResults ()
    {
        return results;
    }

    public void setResults (BrasIDInfoRow[] results)
    {
        this.results = results;
    }

    public String getReply_code ()
    {
        return reply_code;
    }

    public void setReply_code (String reply_code)
    {
        this.reply_code = reply_code;
    }

    public String getRequest_uri ()
    {
        return request_uri;
    }

    public void setRequest_uri (String request_uri)
    {
        this.request_uri = request_uri;
    }

    public String getRequest_time ()
    {
        return request_time;
    }

    public void setRequest_time (String request_time)
    {
        this.request_time = request_time;
    }

    public String getReply_msg ()
    {
        return reply_msg;
    }

    public void setReply_msg (String reply_msg)
    {
        this.reply_msg = reply_msg;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+results[0].toString()+", reply_code = "+reply_code+", request_uri = "+request_uri+", request_time = "+request_time+", reply_msg = "+reply_msg+"]";
    }
}
