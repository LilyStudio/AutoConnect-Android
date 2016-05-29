package com.padeoe.oa;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by padeoe on 2016/4/17.
 */
public class LoginService {
    public static boolean checkPassword(String username,String password){
        String data0="encoded=false&goto=&gotoOnFail=&IDToken0=&loginLT=4dbd65e0-d0b5-4bbc-9e12-8f93132c7f3f&IDButton=Submit&username="+username+"&password="+password+"&gx_charset=UTF-8";
        int returnCode=getRuturnCode("POST",data0,"http://mids.nju.edu.cn/_ids_mobile/webLogin20_2",null,"UTF-8",1000);
        return returnCode==200;
    }

    public static int getRuturnCode(String action,String postData,String URL,Map<String,String> requestProperty,String inputEncoding,int timeout){
        try {
            byte[] postAsBytes=new byte[]{};
            if(postData!=null){
                postAsBytes= postData.getBytes(inputEncoding);
            }
            java.net.URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(timeout);
            connection.setDoOutput(true);
            connection.setRequestMethod(action);
            connection.setUseCaches(false);
            if(requestProperty!=null){
                for(Map.Entry<String, String> entry : requestProperty.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setRequestProperty("Content-Length", String.valueOf(postAsBytes.length));

            OutputStream outputStream = null;
            try {
                outputStream = connection.getOutputStream();
                outputStream.write(postAsBytes);
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            connection.connect();
            int code=connection.getResponseCode();
            connection.disconnect();
            return code;
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
            return -1;
        } catch (MalformedURLException malformedURLException) {
            System.out.println(malformedURLException);
            return -2;
        } catch (ProtocolException protocolException) {
            System.out.println(protocolException);
            return -3;
        }catch(NoRouteToHostException noroute){
            return -7;
        }
        catch (BindException ss){
            return -6;
        }
        catch (UnknownHostException sss){
            return -5;
        }
        catch (IOException ioException) {
            System.out.println(ioException);
            return -4;
        }
    }



}
