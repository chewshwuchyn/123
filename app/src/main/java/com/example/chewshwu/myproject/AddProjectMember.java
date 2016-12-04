package com.example.chewshwu.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class AddProjectMember extends AppCompatActivity {

    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";
    private String userID = "";
    private String projectID = "";



    String json_string;
    String sprojectString, sprojectName, sname;

    ArrayList<spinnerObjProj> proObj;
    //   spinnerObjProj sop = new spinnerObjProj();

    ArrayList<HashMap<String, String>> spinnerobjpro = new ArrayList<HashMap<String, String>>();

    String urlAddress = "http://192.168.137.1/project6/getprojectlist.php";
    //String urlAddress2 = "http://192.168.137.1/project6/getuser.php";
    //  String projectName;

    EditText etProjectID, etUserID, etPosition;
    Button bAdd;
    Spinner sp, sp2;
    String sprojectID, suserID, sposition;

    Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprojectmember);

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userID = preferences.getString("user_id", "0");
        projectID = preferences.getString("projectID", "");

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("projectID2", "");
        editor.commit();

        bAdd = (Button) findViewById(R.id.bAdd);
        //    etProjectID = (EditText) findViewById(R.id.etProjectID);
        //     etUserID = (EditText) findViewById(R.id.etUserID);
        etPosition = (EditText) findViewById(R.id.etPosition);


        sp = (Spinner) findViewById(R.id.spinner2);
        //sp2 = (Spinner) findViewById(R.id.spinner3);

        new spinnerDownloader(AddProjectMember.this, urlAddress, sp).execute();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                sprojectString = parent.getSelectedItem().toString();

                sprojectString = sprojectString.substring(1, sprojectString.length() - 1);
                String[] choppedSprojectString = sprojectString.split("=");
                sprojectString = choppedSprojectString[2];
                sprojectName = choppedSprojectString[1];

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("projectID2", sprojectString);
                        editor.commit();

                getUser(sprojectString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void getUser(String projectID) {
        String urlAddress2 = "http://192.168.137.1/project6/getuser.php";
        sp2 = (Spinner) findViewById(R.id.spinner3);
        new spinnerDownloader2(AddProjectMember.this, urlAddress2, sp2, projectID).execute();
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                suserID = parent.getSelectedItem().toString();


                suserID = suserID.substring(1, suserID.length() - 1);
                String[] choppedSuserString = suserID.split("=");
                suserID = choppedSuserString[2];
                sname = choppedSuserString[1];



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void AddProjectMember(View view) {

        //    sprojectID = etProjectID.getText().toString();
        //    suserID = etUserID.getText().toString();
        sposition = etPosition.getText().toString();

        if (sprojectString.equals("") || suserID.equals("") || sposition.equals("")) {
            Toast.makeText(AddProjectMember.this, "Please fill in required fields.", Toast.LENGTH_SHORT).show();
        } else {

            BackgroundWorker backgroundWorker = new BackgroundWorker();
            backgroundWorker.execute(sprojectString, suserID, sposition);
        }

    }


    public class BackgroundWorker extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String projectID = params[0];
            String usrID = params[1];
            String position = params[2];

            String result = "";
            int line;
            String register_url = "http://192.168.137.1/project6/addprojectmember.php";

            try {

                URL url = new URL(register_url);
                String urlParams = "projectID=" + projectID + "&usrID=" + usrID + "&position=" + position;

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
                result = "New project member added.";
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                //  Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                //   RegisterActivity.this.startActivity(intent);
            }
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }


    }


}
