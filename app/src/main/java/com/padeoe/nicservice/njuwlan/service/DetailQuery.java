package com.padeoe.nicservice.njuwlan.service;

/**
 * 该接口用于描述<a href="http://p.nju.edu.cn">南京大学网络认证系统</a>的和<a href="http://bras.nju.edu.cn">南京大学网络认证计费系统自助平台</a>都提供了的某些查询功能，
 * 包括获取当前在线设备，获取详单信息，获取认证信息，获取账单信息，获取充值信息
 * @author padeoe
 * Date: 2015/9/24
 */
public interface DetailQuery {
    String queryBy(int catalog, int page, int row, boolean order);
    String getOnline(int page, int row);
    String getAuthLog(int page, int row, boolean order);
    String getAcct(int page, int row, boolean order);
    String getBills(int page, int row, boolean order);
    String getRecharge(int page, int row, boolean order);
}
