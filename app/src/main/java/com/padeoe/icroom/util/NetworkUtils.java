package com.padeoe.icroom.util;

import com.padeoe.utils.network.MyHttpRequest;

import java.io.IOException;
import java.util.HashMap;

/**
 * 该类用于负责{@link com.padeoe.icroom}内的网络通信,重新封装了{@link MyHttpRequest}内的方法，指定了特定http header
 * @author  padeoe
 * Date: 2016/3/7
 */
public class NetworkUtils {
    public static String mygetWithCookie(String data,String URL,String cookie,int timeout) throws IOException {
        HashMap<String,String> attr=new HashMap<>();
        attr.put("Accept","application/json, text/javascript, */*; q=0.01");
        attr.put("Referer", "http://114.212.7.24/ClientWeb/xcus/IC2/Default.aspx");
        attr.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        attr.put("X-Requested-With", "XMLHttpRequest");
        return MyHttpRequest.getWithCookie(data,URL,attr,cookie,"UTF-8", "UTF-8",timeout);
    }
    public static String[] myPostAndGetCookie(String data,String URL,int timeout) throws IOException {
        HashMap<String,String> attr=new HashMap<>();
        attr.put("Accept","application/json, text/javascript, */*; q=0.01");
        attr.put("Referer", "http://114.212.7.24/ClientWeb/xcus/IC2/Default.aspx");
        attr.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        attr.put("X-Requested-With", "XMLHttpRequest");
        return MyHttpRequest.postAndGetCookie(data,URL,attr,"UTF-8", "UTF-8",timeout);
    }
}
