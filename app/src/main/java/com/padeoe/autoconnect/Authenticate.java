package com.padeoe.autoconnect;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by padeoe on 4/20/15.
 *
 * @author yus
 */
public class Authenticate {
    public static final String LOGINURL="http://219.219.114.15/portal_io/login";
    public static final String LOGOUTURL="http://219.219.114.15/portal_io/logout";
    public static int i = 0;
    public Authenticate(Context context){

    }
    public static String connectAndPost(String postData,String URL) throws InterruptedException {
        try {
            byte[] postAsBytes = postData.getBytes("UTF-8");
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(5000);
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
            Log.i("LOOKHERE", "成功登陆");
            i = 0;
            return data;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static String disconnect() {
        try {
            String result = connectAndPost("",LOGOUTURL);
            ReturnData returnData;
            String reply_message;
            if (result != null && (returnData = new Gson().fromJson(result, ReturnData.class)) != null
                    && (reply_message = returnData.getReply_message()) != null) {
                return reply_message;
            } else {
                return (String) MainActivity.ctx.getResources().getText(R.string.disconnect_fail);
            }
        } catch (InterruptedException e) {
            Log.getStackTraceString(e);
            return (String) MainActivity.ctx.getResources().getText(R.string.disconnect_fail);
        }

    }

    public static String connect(String postdata,String URL) {
        try {
            String result = connectAndPost(postdata,LOGINURL);
            ReturnData returnData = null;
            userinfo userinfo = null;
            if (result != null && (returnData = new Gson().fromJson(result, ReturnData.class)) != null
                    && (userinfo = returnData.getUserinfo()) != null) {
                return userinfo.getFullname() + userinfo.getUsername() + "\n" + returnData.getReply_message();
            } else {
                if (returnData != null && userinfo == null) {
                    return returnData.getReply_message();
                } else {
                    return (String) MainActivity.ctx.getResources().getText(R.string.login_fail);
                }
            }
        } catch (InterruptedException e) {
            Log.getStackTraceString(e);
            return (String) MainActivity.ctx.getResources().getText(R.string.login_fail);
        }
    }
}
