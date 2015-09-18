package com.padeoe.nicservice.njuwlan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by padeoe on 4/20/15.
 * Modified on 9/15/2015
 *
 * @author yus, padeoe
 */
public class ConnectPNJU {
    private static final String LOGINURL = "http://219.219.114.15/portal_io/login";
    private static final String LOGOUTURL = "http://219.219.114.15/portal_io/logout";

    /**
     * Prevents this class from being instantiated.
     */
    private ConnectPNJU() {

    }

    /**
     * Post请求
     *
     * @param postData
     * @param URL
     * @throws InterruptedException
     * @returnPost
     */
    private static String connectAndPost(String postData, String URL, int timeout) {
        try {
            byte[] postAsBytes = postData.getBytes("UTF-8");
            java.net.URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(timeout);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(postAsBytes.length));
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

            byte[] readData = new byte[1024];
            int len;
/*          java 1.6 do not support
            try (InputStream inputStream = connection.getInputStream()) {
                len = inputStream.read(readData);
            }*/
            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
                len = inputStream.read(readData);
            } finally {
                if (inputStream != null) {
                    {
                        inputStream.close();
                    }
                }
            }
            connection.disconnect();
            String data = new String(readData, 0, len, "UTF-8");
            return data;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
        catch (MalformedURLException malformedURLException){
            return malformedURLException.getMessage();
        }
        catch(ProtocolException protocolException){
            return protocolException.getMessage();
        }
        catch (IOException ioException){
            return ioException.getMessage();
        }
    }

    /**
     * 断开连接
     *
     * @return 是否成功
     */
    public static String disconnect(int timeout) {
        String result = connectAndPost("", LOGOUTURL, timeout);
        return result;
    }

    /**
     * @param username
     * @param password
     * @param timeout
     * @return 返回的字符串的JSON解析
     */
    public static String connect(String username, String password, int timeout) {
        String postdata = "action=login&username=" + username + "&password=" + password;
        String result = connectAndPost(postdata, LOGINURL, timeout);
        return result;
    }
}

