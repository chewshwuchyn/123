package com.example.chewshwu.myproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CHEW SHWU on 10/5/2016.
 */

public class TaskMain extends AppCompatActivity {

    SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";
    private String userID = "";

    String json_string;
    String sprojectString, sprojectID, sprojectName;
    String projectID, projectName;
    int projectIDS;
    //   EditText projectIDEt;
    Context ctx = this;
    String text;
    ArrayList<spinnerObjProj> proObj;
    spinnerObjProj sop ;
    Spinner sp;

    ArrayList<HashMap<String, String>> spinnerobjpro;

    String urlAddress = "http://192.168.137.1/project6/getprojectlist.php";
    //  String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintask);

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userID = preferences.getString("user_id", "0");


       sp = (Spinner) findViewById(R.id.spinner);


        new spinnerDownloader(TaskMain.this, urlAddress, sp).execute();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //    ArrayList<HashMap<String, String>> spinnerobjpro = new ArrayList<HashMap<String, String>>();

            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                sprojectString = parent.getSelectedItem().toString();

               sprojectString = sprojectString.substring(1, sprojectString.length() - 1);
                String[] choppedSprojectString = sprojectString.split("=");
                sprojectString = choppedSprojectString[2];
                sprojectName = choppedSprojectString[1];
                String[] choppedSprojectName = sprojectName.split(",");
                sprojectName = choppedSprojectName[0];




                //      sprojectID = parent.getItemAtPosition(position).get("projectName").toString;
                //     sprojectName = spinnerobjpro.get(position).get("projectName").toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //    projectIDEt = (EditText) findViewById(R.id.etProjID);

    }

    public void ShowAllProject(View view) {
        new BackgroundTask2(this).execute();
    }

    public void ShowTask(View view) {
        //  projectID = projectIDEt.getText().toString();
   //     SharedPreferences preference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
   //     String userID = preference.getString("user_id", "0");
        Intent intent = new Intent(TaskMain.this, TaskRList.class);
        intent.putExtra("projectID", sprojectString);
        intent.putExtra("projectName", sprojectName);
        startActivity(intent);

     //   BackgroundTask3 backgroundTask3 = new BackgroundTask3();
     //   backgroundTask3.execute(sprojectString, userID);
    }

    public void CreateNewTask(View view) {
        Intent intent = new Intent(TaskMain.this, CreateTask.class);
        //   projectID = projectIDEt.getText().toString();
        intent.putExtra("projectID", sprojectString);
        startActivity(intent);

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
            project_url = "http://192.168.137.1/project6/task.php";
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
                Intent intent = new Intent(TaskMain.this, TaskListView.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);
            }
        }


    }

    public class BackgroundTask3 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String JSON_STRING;

        @Override
        protected String doInBackground(String... params) {
            String projectID = params[0];
            String userID = params[1];
            String register_url = "http://192.168.137.1/project6/taskdetail.php";

            try {

                URL url = new URL(register_url);
                String urlParams = "projectID=" + projectID + "&user_id=" + userID;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(urlParams.getBytes());
                outputStream.flush();
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
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json_string = result;

            if (json_string == null) {
                Toast.makeText(getApplicationContext(), "No able to get data", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(TaskMain.this, TaskDetailList.class);
                intent.putExtra("json_data", json_string);
                intent.putExtra("projectID", sprojectString);
                startActivity(intent);
            }
        }
    }

}





