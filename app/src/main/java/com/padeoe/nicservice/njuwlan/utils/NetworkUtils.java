package com.padeoe.nicservice.njuwlan.utils;

import com.padeoe.nicservice.njuwlan.service.LoginService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * Created by padeoe on 2015/9/23.
 */
public class NetworkUtils {
    private static String portalIP = null;
    private static String brasIP = null;

    /**
     * Post请求
     *
     * @param postData
     * @param URL
     * @throws InterruptedException
     * @returnPost
     */
    public static String connectAndPost(String postData, String URL, int timeout) {
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

            //读取10KB返回数据
            byte[] readData = new byte[10240];
            int len = 0;
/*          java 1.6 do not support
            try (InputStream inputStream = connection.getInputStream()) {
                len = inputStream.read(readData);
            }*/
            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
                len = inputStream.read(readData, len, readData.length - len);
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
        } catch (MalformedURLException malformedURLException) {
            return malformedURLException.getMessage();
        } catch (ProtocolException protocolException) {
            return protocolException.getMessage();
        } catch (IOException ioException) {
            resetPortalIP();
            return ioException.getMessage();
        }
    }

    public static String postWithCookie(String postData, String cookie, String URL, int timeout) {
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
            connection.setRequestProperty("Cookie", cookie);
            System.out.println("发送的Cookie是" + cookie);
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

            //读取10KB返回数据
            byte[] readData = new byte[20480];
            int len = 0;
            int wholelen = 0;
/*          java 1.6 do not support
            try (InputStream inputStream = connection.getInputStream()) {
                len = inputStream.read(readData);
            }*/
            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
                while (true) {
                    len = inputStream.read(readData, wholelen, readData.length - wholelen);
                    if (len == -1 || readData.length - wholelen == len) {
                        break;
                    }
                    wholelen += len;

                }

            } finally {
                if (inputStream != null) {
                    {
                        inputStream.close();
                    }
                }
            }

            connection.disconnect();
            String data = new String(readData, 0, wholelen, "UTF-8");
            return data;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        } catch (MalformedURLException malformedURLException) {
            return malformedURLException.getMessage();
        } catch (ProtocolException protocolException) {
            return protocolException.getMessage();
        } catch (IOException ioException) {
            resetBrasIP();
            return ioException.getMessage();

        }


    }

    public static String[] postAndGetCookie(String postData, String URL, int timeout) {
        String cookie = null;
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

            //读取10KB返回数据
            byte[] readData = new byte[10240];
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

            cookie = connection.getHeaderField("Set-Cookie");
            cookie = cookie.substring(0, cookie.indexOf(";"));
            connection.disconnect();
            String data = new String(readData, 0, len, "UTF-8");
            return new String[]{data, cookie};

        } catch (UnsupportedEncodingException e) {
            return new String[]{e.getMessage(), null};
        } catch (MalformedURLException malformedURLException) {
            return new String[]{malformedURLException.getMessage(), null};
        } catch (ProtocolException protocolException) {
            return new String[]{protocolException.getMessage(), null};
        } catch (IOException ioException) {
            resetBrasIP();
            return new String[]{ioException.getMessage(), null};
        }
    }

    public static String getCurrentPortalIP() {
        if (portalIP == null) {
            System.out.println("即将初始化IP");
            resetPortalIP();
        }
        return portalIP;
    }

    public static void resetPortalIP() {
        InetAddress[] inetAddresses;
        try {
            System.out.println("请求DNS解析");
            inetAddresses = InetAddress.getAllByName("p.nju.edu.cn");
            portalIP = inetAddresses[0].getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("DNS无法解析，将使用默认IP");
            portalIP = "210.28.129.9";
        }
        LoginService.resetIP();
    }

    public static String getCurrentBrasIP() {
        if (brasIP == null) {
            System.out.println("即将初始化bras IP");
            resetBrasIP();
        }
        return brasIP;
    }

    public static void resetBrasIP() {
        InetAddress[] inetAddresses;
        try {
            System.out.println("请求DNS解析");
            inetAddresses = InetAddress.getAllByName("bras.nju.edu.cn");
            brasIP = inetAddresses[0].getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("DNS无法解析，将使用默认IP");
            brasIP = "219.219.114.254";
        }
    }


}
