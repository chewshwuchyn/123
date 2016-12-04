package com.example.chewshwu.myproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * Created by CHEW SHWU on 11/27/2016.
 */

public class HttpHandler  {

    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";

    Context c;
    String urlAddress;

    public HttpHandler(){


    }

    public String downloadData(Context c, String urlAddress) {
        HttpURLConnection httpURLConnection = Connector.connect(urlAddress);

        SharedPreferences preference = c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String userID = preference.getString("user_id", "0");

        if (httpURLConnection == null) {
            return null;
        }

        InputStream inputStream = null;
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            StringBuffer stringBuffer = new StringBuffer();

            if (bufferedReader != null) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                bufferedReader.close();

            } else {
                return null;
            }

            return stringBuffer.toString();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
