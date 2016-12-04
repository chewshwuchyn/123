package com.example.chewshwu.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MyProject extends AppCompatActivity {

    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";
    String json_string;
 //   private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);


//        Intent intent = getIntent();
 //       String result = intent.getStringExtra("result");
 //      tvWelcome = (TextView) findViewById(R.id.tvWelcome);
  //      tvWelcome.setText("Welcome " +);
    }

    public void getJSON(View view) {
        new BackgroundTask(this).execute();
    }

    public void taskList(View view){
      //  new BackgroundTask2().execute();
        startActivity(new Intent(this, TaskMain.class));


    }

    public void projectMember(View view){
        startActivity(new Intent(this, MemberNoList.class));

    }


    public void Logout (View view){
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent (MyProject.this, LoginActivity.class);

        MyProject.this.startActivity(intent);
        finish();
    }

    public void toDoList(View view){
      //  startActivity(new Intent(this, ToDoList.class));

        new BackgroundTask2(this).execute();
    }
    /**
     * public void parseJSON(View view){
     * if(json_string == null) {
     * Toast.makeText(getApplicationContext(), "First Get JSON", Toast.LENGTH_SHORT).show();
     * }
     * else {
     * Intent intent = new Intent(MyProject.this, DisplayListView.class);
     * intent.putExtra("json_data", json_string);
     * startActivity(intent);
     * }
     * <p>
     * <p>
     * <p>
     * <p>
     * }
     **/

    public class BackgroundTask extends AsyncTask<Void, Void, String> {

        Context context;
        String project_url;
        String JSON_STRING;

        BackgroundTask (Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();;

        }

        @Override
        protected String doInBackground(Void... params) {

            project_url = "http://192.168.137.1/project6/project.php";
            SharedPreferences preference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String userID = preference.getString("user_id", "0");


            try {
                URL url = new URL(project_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(userID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            // TextView textView = (TextView)findViewById(R.id.textview);
            // textView.setText(result);
            json_string = result;

            if (json_string == null) {
                Toast.makeText(getApplicationContext(), "No able to get data", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MyProject.this, DisplayListView.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);
            }
        }
    }


    public class BackgroundTask2 extends AsyncTask<Void, Void, String> {

        Context context;
        String project_url;
        String JSON_STRING;

        BackgroundTask2 (Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();;
        }

        @Override
        protected String doInBackground(Void... params) {

            SharedPreferences preference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String userID = preference.getString("user_id", "0");
            project_url = "http://192.168.137.1/project6/todolist.php";

            try {
                URL url = new URL(project_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(userID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            // TextView textView = (TextView)findViewById(R.id.textview);
            // textView.setText(result);
            json_string = result;

            if (json_string == null) {
                Toast.makeText(getApplicationContext(), "No able to get data", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MyProject.this, ToDoList.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);
            }
        }
    }




}
