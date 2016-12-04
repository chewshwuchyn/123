package com.example.chewshwu.myproject;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CHEW SHWU on 10/9/2016.
 */

public class CreateTask extends AppCompatActivity {

    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";
    private String userID = "";


    EditText etTaskName, etTStartDate, etTEndDate, etProjID;
    Button bTCreate;
    String taskName, projectID;
    String taskStartDate, taskEndDate;
    //    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userID = preferences.getString("user_id", "0");

        bTCreate = (Button) findViewById(R.id.bTCreate);
        etTaskName = (EditText) findViewById(R.id.etTaskName);
        etTStartDate = (EditText) findViewById(R.id.etTStartDate);
        etTEndDate = (EditText) findViewById(R.id.etTEndDate);
        //   startDate = new setDate(etStartDate, this);
        etProjID = (EditText) findViewById(R.id.etProjID);
        //   finishDate = new setDate(etFinishDate, this);

        Intent intent = getIntent();
        String pID = intent.getStringExtra("projectID");
        etProjID.setText(pID);

    }

    public void CreateTask(View view) {

        taskName = etTaskName.getText().toString();
        taskStartDate = etTStartDate.getText().toString();
        //  startDate = new setDate(etStartDate, this);
        //  finishDate = new setDate(etFinishDate, this);
        taskEndDate = etTEndDate.getText().toString();
        projectID = etProjID.getText().toString();
        if (taskName.equals("")) {
            Toast.makeText(CreateTask.this, "Please fill in task name.", Toast.LENGTH_LONG).show();
        } else {

            BackgroundWorker backgroundWorker = new BackgroundWorker();
            backgroundWorker.execute(taskName, taskStartDate, taskEndDate, projectID, userID);
        }
        //    noOfMilestone = Integer.parseInt(etNoOfMilestone.getText().toString());
        //    curMilestone = Integer.parseInt(etCurMilestone.getText().toString());
    }

    public void onStart() {
        super.onStart();

        etTStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialog dialog = new DateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });

        etTEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialog dialog = new DateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });


    }

    public class BackgroundWorker extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String taskName = params[0];
            String taskStartDate = params[1];
            String taskEndDate = params[2];
            String projectID = params[3];
            String userID = params[4];

            String result = "";
            int line;
            String register_url = "http://192.168.137.1/project6/createtask.php";

            try {

                URL url = new URL(register_url);
                String urlParams = "taskName=" + taskName + "&taskStartDate=" + taskStartDate + "&taskEndDate=" + taskEndDate + "&projectID=" + projectID + "&user_id=" + userID;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(urlParams.getBytes());
                outputStream.flush();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();


                while ((line = inputStream.read()) != -1) {
                    result += (char) line;
                }
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

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
            if (result.equals("")) {
                result = "New Task added.";
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                //  Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                //   RegisterActivity.this.startActivity(intent);
            }
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();


        }

        /**    if (success) {
         Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
         RegisterActivity.this.startActivity(intent);
         } else {
         AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
         builder.setMessage("Register Failed")
         .setNegativeButton("Retry", null)
         .create()
         .show();
         }**/


    }

}





