package com.example.chewshwu.myproject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CHEW SHWU on 11/22/2016.
 */

public class Connector {

    public static HttpURLConnection connect(String urlAddress) {

        try {
            URL url = new URL(urlAddress);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            return httpURLConnection;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }
}
