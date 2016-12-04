package com.example.chewshwu.myproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
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
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText EmailEt, PasswordEt;
    String email, password;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailEt = (EditText)findViewById(R.id.etEmail);
        PasswordEt = (EditText) findViewById(R.id.etPassword);
        preferences = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);


    }

    public void OnLogin(View view){
        email = EmailEt.getText().toString();
        password = PasswordEt.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, email, password);
      //  sendTokenToServer();


    }


    public void OpenRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }


    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        final String email = EmailEt.getText().toString();

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    public class BackgroundWorker extends AsyncTask<String, Void, String> {
        Context context;
        ProgressDialog pd;
        AlertDialog alertDialog;
        BackgroundWorker (Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://192.168.137.1/project6/login.php";
            if(type.equals("login")){
                try{
                    String email = params[1];
                    String password = params[2];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result="";
                    String line;
                    while((line = bufferedReader.readLine())!= null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();


                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();;

            pd= new ProgressDialog(context);
            pd.setTitle("Login status");
            pd.setMessage("Loading...Please wait");
            pd.show();
            //   progressDialog = ProgressDialog.show(LoginActivity.this, "Loading...", null, true, true);
            //    alertDialog = new AlertDialog.Builder(context).create();
            //    alertDialog.setTitle("Login Status");


        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();

            if(result.equals("")) {
                result = "Login not success.";
                Toast.makeText(context, result,Toast.LENGTH_SHORT).show();

                //    alertDialog.setMessage(result);
                //     alertDialog.show();

            }else{

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user_id", result);
                editor.commit();
                Toast.makeText(context, "Login success!!! Welcome", Toast.LENGTH_SHORT).show();
                sendTokenToServer();
                //    alertDialog.setMessage(result);
                //    alertDialog.show();
                Intent intent = new Intent(LoginActivity.this, MyProject.class);
                intent.putExtra("result", result);
              //  intent.putExtra("email", email);
               // intent.putExtra("password", password);
                LoginActivity.this.startActivity(intent);
                finish();

            }


            //   Intent intent = new Intent(LoginActivity.this, MyProject.class);
            //   intent.putExtra("email", email);
            //  intent.putExtra("password", password);
            //  LoginActivity.this.startActivity(intent);


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        /**    if (success) {
         String name = jsonResponse.getString("name");
         String email = jsonResponse.getString("email");

         Intent intent = new Intent(LoginActivity.this, MyProject.class);
         intent.putExtra("name", name);
         intent.putExtra("email", email);
         LoginActivity.this.startActivity(intent);
         } else {
         AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
         builder.setMessage("Login Failed")
         .setNegativeButton("Retry", null)
         .create()
         .show();
         }**/
    }
}
