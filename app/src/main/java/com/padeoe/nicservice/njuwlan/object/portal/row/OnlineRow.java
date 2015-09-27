package com.padeoe.nicservice.njuwlan.object.portal.row;

import com.padeoe.nicservice.njuwlan.object.bras.row.OnlineRowBras;

/**
 * Created by padeoe on 2015/9/24.
 */
public class OnlineRow extends OnlineRowBras
{
    protected String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [pvlan = "+pvlan+", service_id = "+service_id+", area_id = "+area_id+", acctsessiontime = "+acctsessiontime+", mac = "+mac+", service_name = "+service_name+", ap_id = "+ap_id+", area_type = "+area_type+", id = "+id+", username = "+username+", acctstarttime = "+acctstarttime+", area_name = "+area_name+", acctoutputoctets_ipv4 = "+acctoutputoctets_ipv4+", user_ipv4 = "+user_ipv4+", fullname = "+fullname+", acctoutputoctets_ipv6 = "+acctoutputoctets_ipv6+", user_ipv6 = "+user_ipv6+", acctinputoctets_ipv6 = "+acctinputoctets_ipv6+", acctinputoctets_ipv4 = "+acctinputoctets_ipv4+", subport = "+subport+", acctsessionid = "+acctsessionid+", svlan = "+svlan+", src_ip = "+src_ip+", nas_ip = "+nas_ip+"]";
    }
}


