package com.padeoe.njunet.util;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by padeoe on 2016/5/18.
 */

public class NetworkTest {
    public static int testNetwork(String URL) {
        HttpURLConnection urlConnection = null;
        try {
            java.net.URL url = new URL(URL);
            urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(false);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setUseCaches(false);
            urlConnection.getInputStream();
            urlConnection.setConnectTimeout(2000);
            urlConnection.setReadTimeout(2000);
            int httpResponseCode = urlConnection.getResponseCode();
            if (httpResponseCode == 200 && urlConnection.getContentLength() == 0) {
                httpResponseCode = 204;
            }
            return httpResponseCode;
        } catch (Exception e) {
            return -1;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
