package com.padeoe.utils.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * 该类用于负责http网络请求，包含get，set等方法
 *
 * @author padeoe, Nifury
 *         Date: 2015/12/13
 */
public class MyHttpRequest {
    /**
     * POST请求
     *
     * @param action          post或get请求
     * @param postData        数据
     * @param URL             服务器地址
     * @param requestProperty 请求头
     * @param cookie          cookie若无则置为空
     * @param inputEncoding   请求编码
     * @param outputEncoding  响应编码
     * @param timeout         超时时间
     * @return 字符串数组，第一个元素是响应数据,若长度为2则第二个是返回的cookie
     */
    public static String[] action(String action, String postData, String URL, Map<String, String> requestProperty, String cookie, String inputEncoding, String outputEncoding, int timeout) throws IOException {
        byte[] postAsBytes = new byte[]{};
        if (postData != null) {
            postAsBytes = postData.getBytes(inputEncoding);
        }
        java.net.URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setConnectTimeout(timeout);
        connection.setDoOutput(true);
        connection.setRequestMethod(action);
        connection.setUseCaches(false);
           /*           java 1.6 does not support
           requestProperty.forEach((k,v) -> connection.setRequestProperty(k, v));
           */
        if (requestProperty != null) {
            for (Map.Entry<String, String> entry : requestProperty.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        connection.setRequestProperty("Content-Length", String.valueOf(postAsBytes.length));
        if (cookie != null) {
            connection.setRequestProperty("Cookie", cookie);
        }
        connection.connect();

           /*          java 1.6 do not support
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(postAsBytes);
            }*/
        OutputStream outputStream = null;
        try {
            outputStream = connection.getOutputStream();
            outputStream.write(postAsBytes);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }

        }

        //读取返回数据
        MyByteArray myByteArray = new MyByteArray();
/*          java 1.6 do not support
            try (InputStream inputStream = connection.getInputStream()) {
                len = inputStream.read(readData);
            }*/
        InputStream inputStream = null;
        if (postData != null) {
            try {
                inputStream = connection.getInputStream();
                while (true) {
                    myByteArray.ensureCapacity(4096);
                    int len = inputStream.read(myByteArray.getBuffer(), myByteArray.getOffset(), 4096);
                    if (len == -1) {
                        break;
                    }
                    myByteArray.addOffset(len);
                }

            } finally {
                if (inputStream != null) {
                    {
                        inputStream.close();
                    }
                }
            }
        }

        String newCookie;
        newCookie = connection.getHeaderField("Set-Cookie");
        if (newCookie != null) {
            newCookie = newCookie.substring(0, newCookie.indexOf(";"));
        }
        connection.disconnect();
        String data = new String(myByteArray.getBuffer(), 0, myByteArray.getSize(), outputEncoding);
        if (newCookie != null) {
            return new String[]{data, newCookie};
        }
        return new String[]{data};

    }

    /**
     * 获得cookie的POST请求
     *
     * @param postData        请求数据
     * @param URL             服务器地址
     * @param requestProperty 请求头
     * @param inputEncoding   请求编码
     * @param outputEncoding  响应编码
     * @param timeout         超时时间
     * @return 字符串数组，第一个元素是响应数据,第二个是返回的cookie
     */
    public static String[] postAndGetCookie(String postData, String URL, Map<String, String> requestProperty, String inputEncoding, String outputEncoding, int timeout) throws IOException {
        return action("POST", postData, URL, requestProperty, null, inputEncoding, outputEncoding, timeout);
    }

    /**
     * 发送cookie的POST请求
     *
     * @param postData        请求数据
     * @param URL             服务器地址
     * @param requestProperty 请求头
     * @param cookie          发送的cookie
     * @param inputEncoding   请求编码
     * @param outputEncoding  响应编码
     * @param timeout         超时时间
     * @return 响应数据
     */
    public static String postWithCookie(String postData, String URL, Map<String, String> requestProperty, String cookie, String inputEncoding, String outputEncoding, int timeout) throws IOException {
        return action("POST", postData, URL, requestProperty, cookie, inputEncoding, outputEncoding, timeout)[0];
    }

    /**
     * POST请求(不含cookie)
     *
     * @param postData        请求数据
     * @param URL             服务器地址
     * @param requestProperty 请求头
     * @param inputEncoding   请求编码
     * @param outputEncoding  响应编码
     * @param timeout         超时时间
     * @return 响应数据
     */
    public static String post(String postData, String URL, Map<String, String> requestProperty, String inputEncoding, String outputEncoding, int timeout) throws IOException {
        return action("POST", postData, URL, requestProperty, null, inputEncoding, outputEncoding, timeout)[0];
    }


    /**
     * 获得cookie的POST请求
     *
     * @param data            请求数据
     * @param URL             服务器地址
     * @param requestProperty 请求头
     * @param inputEncoding   请求编码
     * @param outputEncoding  响应编码
     * @param timeout         超时时间
     * @return 字符串数组，第一个元素是响应数据,第二个是返回的cookie
     */
    public static String[] getAndGetCookie(String data, String URL, Map<String, String> requestProperty, String inputEncoding, String outputEncoding, int timeout) throws IOException {
        return action("GET", data, URL, requestProperty, null, inputEncoding, outputEncoding, timeout);
    }

    /**
     * 发送cookie的POST请求
     *
     * @param data            请求数据
     * @param URL             服务器地址
     * @param requestProperty 请求头
     * @param cookie          发送的cookie
     * @param inputEncoding   请求编码
     * @param outputEncoding  响应编码
     * @param timeout         超时时间
     * @return 响应数据
     */
    public static String getWithCookie(String data, String URL, Map<String, String> requestProperty, String cookie, String inputEncoding, String outputEncoding, int timeout) throws IOException {
        return action("GET", data, URL, requestProperty, cookie, inputEncoding, outputEncoding, timeout)[0];
    }

    /**
     * POST请求(不含cookie)
     *
     * @param data            请求数据
     * @param URL             服务器地址
     * @param requestProperty 请求头
     * @param inputEncoding   请求编码
     * @param outputEncoding  响应编码
     * @param timeout         超时时间
     * @return 响应数据
     */
    public static String get(String data, String URL, Map<String, String> requestProperty, String inputEncoding, String outputEncoding, int timeout) throws IOException {
        return action("GET", data, URL, requestProperty, null, inputEncoding, outputEncoding, timeout)[0];
    }
}
