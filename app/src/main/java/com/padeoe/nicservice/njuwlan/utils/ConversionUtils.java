package com.padeoe.nicservice.njuwlan.utils;

/**
 * 该类集合了一些单位转换的静态方法，包括数据单位转换，ip转换
 * @author padeoe
 * Date: 2015/12/17
 */
public class ConversionUtils {
    /**
     * 将以B为单位的流量转化成合适的单位
     *
     * @param octetsSize long类型的数字,字节数,例如"2049"
     * @return 例如"1.01KB","2.34GB"
     */
    public static String getShownDataSize(String octetsSize) {
        long size_b = Long.parseLong(octetsSize);
        boolean isMultiple = size_b % 1024 == 0;
        if (size_b < 1024) {
            return size_b + "B";
        }
        if (size_b < 1048576) {
            return round(size_b / 1024.0, isMultiple) + "KB";
        }
        if (size_b < 1073741824) {
            return round(size_b / 1048576.0, isMultiple) + "MB";
        }
        if (size_b < 1099511627776L) {
            return round(size_b / 1073741824.0, isMultiple) + "GB";
        }
        return round(size_b / 1099511627776.0, isMultiple) + "TB";
    }

    private static String round(double number, boolean isMultiple) {
        if (isMultiple) {
            return String.valueOf((int) number);
        }
        return String.format("%.2f", number);
    }

    /**
     * 将十进制的IPv4地址转化成点段式IP地址
     *
     * @param ipv4 例如“1926545573”
     * @return 十进制点段式IP地址，例如“114.212.192.165”
     */
    public static String getIPv4(String ipv4) {
        long ip = Long.parseLong(ipv4);
        long ip1 = (ip >>> 24) & 0x0ff;
        long ip2 = (ip >>> 16) & 0x0ff;
        long ip3 = (ip >>> 8) & 0x0ff;
        long ip4 = ip & 0x0ff;
        return String.format("%d.%d.%d.%d", ip1, ip2, ip3, ip4);
    }

}
