package com.example.chewshwu.myproject;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class CreateProject extends AppCompatActivity {

    EditText etName, etType, etStartDate, etFinishDate, etNoOfMilestone, etCurMilestone;
    Button bCreate;
    String name, type;
    String startDate, finishDate;
    //    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String noOfMilestone, curMilestone;
    Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        bCreate = (Button) findViewById(R.id.bCreate);
        etName = (EditText) findViewById(R.id.etName);
        etType = (EditText) findViewById(R.id.etType);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        //   startDate = new setDate(etStartDate, this);
        etFinishDate = (EditText) findViewById(R.id.etFinishDate);
        //   finishDate = new setDate(etFinishDate, this);
        etNoOfMilestone = (EditText) findViewById(R.id.etNoOfMilestone);
        etCurMilestone = (EditText) findViewById(R.id.etCurMilestone);

    }

    public void CreateProject(View view) {

        name = etName.getText().toString();
        type = etType.getText().toString();
        //  startDate = new setDate(etStartDate, this);
        //  finishDate = new setDate(etFinishDate, this);
        startDate = etStartDate.getText().toString();
        finishDate = etFinishDate.getText().toString();
        noOfMilestone = etNoOfMilestone.getText().toString();
        curMilestone = etCurMilestone.getText().toString();
        if (name.equals("") || startDate.equals("") || finishDate.equals("")) {
            Toast.makeText(CreateProject.this, "Please fill in required fields.", Toast.LENGTH_LONG).show();
        } else {


            BackgroundWorker backgroundWorker = new BackgroundWorker();
            backgroundWorker.execute(name, type, startDate, finishDate, noOfMilestone, curMilestone);
        }
        //    noOfMilestone = Integer.parseInt(etNoOfMilestone.getText().toString());
        //    curMilestone = Integer.parseInt(etCurMilestone.getText().toString());
    }

    public void onStart() {
        super.onStart();

        etStartDate.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialog dialog = new DateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });

        etFinishDate.setOnFocusChangeListener(new OnFocusChangeListener() {
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
            String name = params[0];
            String type = params[1];
            String startDate = params[2];
            String finishDate = params[3];
            String noOfMilestone = params[4];
            String curMilestone = params[5];

            String result = "";
            int line;
            String register_url = "http://192.168.137.1/project6/createproject.php";

            try {

                URL url = new URL(register_url);
                String urlParams = "name=" + name + "&type=" + type + "&startDate=" + startDate + "&finishDate=" + finishDate + "&noOfMilestone=" + noOfMilestone + "&curMilestone=" + curMilestone;

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
                result = "New project added.";
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



