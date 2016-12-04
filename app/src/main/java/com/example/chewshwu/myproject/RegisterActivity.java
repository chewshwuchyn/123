package com.example.chewshwu.myproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {
    EditText name, email, password;
    String str_name, str_email, str_password;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.etName);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
    }

    public void OnRegister(View view) {
        str_name = name.getText().toString();
        str_email = email.getText().toString();
        str_password = password.getText().toString();
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        backgroundWorker.execute(str_name, str_email, str_password);
    }

    public class BackgroundWorker extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String email = params[1];
            String password = params[2];
            String result="";
            int line;
            String register_url = "http://192.168.137.1/project6/register.php";

            try{

                URL url = new URL(register_url);
                String urlParams = "name=" + name + "&password=" + password + "&email=" + email;

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(urlParams.getBytes());
                outputStream.flush();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();



                while((line = inputStream.read())!= -1) {
                    result += (char)line;
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
            if(result.equals("")) {
                result = "Data saved successfully.";
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
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
