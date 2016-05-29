package com.padeoe.utils;

/**
 * Created by padeoe on 2016/4/22.
 */
public class ConvertionUtils {
    public static String getIP(String ip) {
        StringBuilder stringBuilder = new StringBuilder();
        int intip=(int)Long.parseLong(ip);
        stringBuilder
                .append((intip >> 24) & 0xFF).append('.')
                .append((intip >> 16) & 0xFF).append('.')
                .append((intip >> 8) & 0xFF).append('.')
                .append(intip & 0xFF);
        return stringBuilder.toString();

    }
}
