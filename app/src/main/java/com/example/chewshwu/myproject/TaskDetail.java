package com.example.chewshwu.myproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by CHEW SHWU on 10/3/2016.
 */

public class TaskDetail extends AppCompatActivity{

    String projectName;
    int totalNoOfTask, projectID;

    private TextView PName, task, pID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskdetail);

        Bundle bundle = getIntent().getExtras();
        projectID = bundle.getInt("projectID");
        projectName = bundle.getString("projectName");
        totalNoOfTask = bundle.getInt("totalNoOfTask");

        PName = (TextView)findViewById(R.id.tvProjectName);
        task = (TextView)findViewById(R.id.tvTask);
        pID = (TextView)findViewById(R.id.tvProjectID);

        showTask();

        BackgroundWorker backgroundWorker = new BackgroundWorker();
        backgroundWorker.execute(Integer.toString(projectID));

    }

    public class BackgroundWorker extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String JSON_STRING;
        @Override
        protected String doInBackground(String... params) {
            String projectID = params[0];
            String register_url = "http://192.168.137.1/project6/taskdetail.php";

            try {

                URL url = new URL(register_url);
                String urlParams = "projectID=" + projectID;

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
        protected void onPostExecute(String result) {

        }

    }
        public void showTask() {
            PName.setText(projectName);
            task.setText("Total Number of Task :" + Integer.toString(totalNoOfTask));
            pID.setText("Project ID :" + Integer.toString(projectID));

        }

    }









   /**     public void updateproject() {

            final String namet = name.getText().toString().trim();
            final String typet = type.getText().toString().trim();
            final String startDatet = startDate.getText().toString().trim();
            final String finishDatet = finishDate.getText().toString().trim();
            final String noOfMilestonet = noOfMilestone.getText().toString().trim();
            final String curMilestonet = curMilestone.getText().toString().trim();

            class BackgroundTask extends AsyncTask<Void,Void,String> {
                //   ProgressDialog loading;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //   loading = ProgressDialog.show(ViewEmployee.this,"Updating...","Wait...",false,false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //   loading.dismiss();
                    Toast.makeText(com.example.chewshwu.myproject.Detail.this,s,Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), MyProject.class);
                    startActivity(intent);


                }

                @Override
                protected String doInBackground(Void... params) {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",String.valueOf(id));
                    hashMap.put("name",namet);
                    hashMap.put("type",typet);
                    hashMap.put("startDate",startDatet);
                    hashMap.put("finishDate",finishDatet);
                    hashMap.put("noOfMilestone",noOfMilestonet);
                    hashMap.put("curMilestone",curMilestonet);

                    RequestHandler requestHandler = new RequestHandler();

                    String s = requestHandler.sendPostRequest("http://192.168.137.1/project6/updateproject.php",hashMap);

                    return s;
                }
            }

            BackgroundTask task = new BackgroundTask();
            task.execute();
        }


        private void deleteproject(){
            class BackgroundTask extends AsyncTask<Void,Void,String> {
                //   ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //    loading = ProgressDialog.show(ViewEmployee.this, "Updating...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //    loading.dismiss();
                    Toast.makeText(com.example.chewshwu.myproject.Detail.this, s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    RequestHandler requestHandler = new RequestHandler();
//                String temp = Integer.toString(id);
//                String s = requestHandler.sendGetRequestParam("http://192.168.137.1/project6/deleteproject.php", temp);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",String.valueOf(id));
                    String s = requestHandler.sendPostRequest("http://192.168.137.1/project6/deleteproject.php", hashMap);
                    return s;
                }
            }

            BackgroundTask task = new BackgroundTask();
            task.execute();
        }

        private void confirmDeleteEmployee(){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure you want to delete this project?");

            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            deleteproject();
                            startActivity(new Intent(com.example.chewshwu.myproject.Detail.this,MyProject.class));
                        }
                    });

            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


        /**    public void updateproject() {
         // Project project = new Project();


         class BackgroundTask extends AsyncTask<Void, Void, String> {

         String project_url;
         String JSON_STRING;
         Project project;
         String result ="";
         String text="";

         @Override
         protected void onPreExecute() {
         project_url = "http://192.168.137.1/project6/updateproject.php";
         }

         @Override
         protected String doInBackground(Void... params) {
         try {
         URL url = new URL(project_url);
         HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
         }try{
         JSONArray JO = new JSONArray(text);
         for(int i=0; i<JO.length(); i++) {
         JSONObject json = JO.getJSONObject(i);

         project.setId(json.getInt("id"));
         project.setName(json.getString("name"));
         project.setType(json.getString("type"));
         project.setStartDate(json.getString("startDate"));
         project.setFinishDate(json.getString("finishDate"));
         project.setNoOfMilestone(json.getInt("noOfMilestone"));
         project.setCurMilestone(json.getInt("curMilestone"));
         }
         } catch (JSONException e) {
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
         if(result.equals("")){
         result = "Update saved.";
         }
         else{
         Toast.makeText(Detail.this, "Error",Toast.LENGTH_SHORT).show();
         }


         }
         }

         BackgroundTask backgroundTask = new BackgroundTask();
         backgroundTask.execute();
         }**/


/**
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            if (id == android.R.id.home) {
                finish();
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onClick(View v){
            if(v == buttonEdit){
                updateproject();
            }
            if(v == buttonDelete){
                confirmDeleteEmployee();
            }

        }
    }




}**/
