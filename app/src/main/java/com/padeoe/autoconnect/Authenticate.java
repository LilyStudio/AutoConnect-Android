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
    public Authenticate(Context context){

    }
    public static String connectAndPost(String postData,String URL) throws InterruptedException {
        try {
            byte[] postAsBytes = postData.getBytes("UTF-8");
            java.net.URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(200);
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
            return data;
        } catch (IOException e) {
            Log.i("Authenticate/Exception",e.toString());
        }
        return null;
    }

    public static String disconnect() {
        try {
            String result = connectAndPost("",App.LOGOUTURL);
            ReturnData returnData;
            String reply_message;
            if (result != null && (returnData = new Gson().fromJson(result, ReturnData.class)) != null
                    && (reply_message = returnData.getReply_message()) != null) {
                return reply_message;
            } else {

                return (String) App.context.getText(R.string.disconnect_fail);
            }
        } catch (InterruptedException e) {
            Log.getStackTraceString(e);
            return (String) App.context.getResources().getText(R.string.disconnect_fail);
        }

    }

    public static ConnectResult connect(String postdata,String URL) {
        try {
            String result = connectAndPost(postdata,App.LOGINURL);
            System.out.println(result);
            ReturnData returnData = null;
            userinfo userinfo = null;
            if (result != null && (returnData = new Gson().fromJson(result, ReturnData.class)) != null
                    && (userinfo = returnData.getUserinfo()) != null) {
                return new ConnectResult(true,returnData.getReply_message(),userinfo.getFullname(),userinfo.getUsername());
            } else {
                if (returnData != null && userinfo == null) {
                    return new ConnectResult(false,returnData.getReply_message(),null,null);
                } else {
                    return new ConnectResult(false,null,null,null);
                }
            }
        } catch (InterruptedException e) {
            Log.getStackTraceString(e);
            return new ConnectResult(false,null,null,null);
        }
    }
}
