package com.padeoe.nicservice.njuwlan.service;

/**
 * Created by padeoe on 2015/9/24.
 */
public interface DetailQuery {
    String queryBy(int catalog, int page, int row, boolean order);
    String getOnline(int page, int row);
    String getAuthLog(int page, int row, boolean order);
    String getAcct(int page, int row, boolean order);
    String getBills(int page, int row, boolean order);
    String getRecharge(int page, int row, boolean order);
}
