package com.example.chewshwu.myproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

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
 * Created by CHEW SHWU on 11/30/2016.
 */

public class spinnerDownloader2 extends AsyncTask<String, Void, String> {

        private SharedPreferences preferences;
        public static final String PREF_NAME = "PrefKey";
        public static final String KEY_USERID = "user_id";

        Context c;
        String urlAddress;
        Spinner sp;
        String projectID;

        ProgressDialog pd;

        public spinnerDownloader2(Context c, String urlAddress, Spinner sp, String projectID){
            this.c = c;
            this.urlAddress = urlAddress;
            this.sp = sp;
            this.projectID = projectID;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            pd= new ProgressDialog(c);
            pd.setTitle("In Progress");
            pd.setMessage("In progress...Please wait");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params){
            return this.downloadData();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

            if (s == null) {
                Toast.makeText(c, "Unable to Retrieve, null Returned", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(c, "Success", Toast.LENGTH_SHORT).show();

                //CALL PARSES CLASS TO PARSE

                //      SharedPreferences preferences = c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                //      int key = preferences.getInt("key", 0);
                //     int key2 = preferences.getInt("key2", 0);
                //      int key3 = preferences.getInt("key3", 0);



       /*     if (key == 1) {
                spinnerDataParser parser = new spinnerDataParser(c, sp, s);
                parser.execute();


            }if(key2 == 2){
                //if (key2 == 2) {
                    spinnerDataUser parserUser = new spinnerDataUser(c, sp, s);
                    parserUser.execute();

                } if(key3 == 1){
            //else {
                    //if (key3 == 1) {
                        spinnerDataParser parser = new spinnerDataParser(c, sp, s);
                        parser.execute();

                    }*/
            }


            spinnerDataUser parserUser = new spinnerDataUser(c, sp, s);
            parserUser.execute();

          //    spinnerDataParser parser = new spinnerDataParser(c, sp, s);
            //     parser.execute();
        }



        private String downloadData(){


            HttpURLConnection httpURLConnection = Connector.connect(urlAddress);
            SharedPreferences preference = c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String userID = preference.getString("user_id", "0");

            if(httpURLConnection==null){
                return null;
            }

            InputStream inputStream = null;
            try {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(userID,"UTF-8")+"&"+URLEncoder.encode("projectID","UTF-8")+"="+URLEncoder.encode(projectID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = null;
                StringBuffer stringBuffer = new StringBuffer();

                if(bufferedReader != null){
                    while((line= bufferedReader.readLine()) != null){
                        stringBuffer.append(line+"\n");
                    }

                    bufferedReader.close();

                }else{
                    return null;
                }

                return  stringBuffer.toString();


            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(inputStream != null){
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


