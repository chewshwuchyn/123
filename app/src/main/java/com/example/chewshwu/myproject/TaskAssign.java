package com.example.chewshwu.myproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CHEW SHWU on 12/3/2016.
 */

public class TaskAssign extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";
    private String userID = "";
    private String projectID = "";

    String taskName, taskStartDate, taskEndDate, toBeDoneBy, assignTo;
    String taskID;

    String suserID, sname;

    private EditText taskNamet;
    private EditText taskStartDatet;
    private EditText taskEndDatet;
    private EditText toBeDoneByt;
    private Button buttonSave;
    private Spinner sp4;
    private ProgressDialog progressDialog;


    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskassign);

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userID = preferences.getString("user_id", "0");
        projectID = preferences.getString("projectIDtask", "");

        Bundle bundle = getIntent().getExtras();
        taskID = bundle.getString("taskID");
        taskName = bundle.getString("taskName");
        taskStartDate = bundle.getString("taskStartDate");
        taskEndDate = bundle.getString("taskEndDate");
        toBeDoneBy = bundle.getString("toBeDoneBy");
        assignTo = bundle.getString("assignTo");

        taskNamet = (EditText) findViewById(R.id.etNamet);
        taskStartDatet = (EditText) findViewById(R.id.etStartDatet);
        taskEndDatet = (EditText) findViewById(R.id.etEndDatet);

        sp4 = (Spinner) findViewById(R.id.spinner4);

        buttonSave = (Button) findViewById(R.id.bSave);

        buttonSave.setOnClickListener(this);

    /*    CursorAdapter myAdapter = (CursorAdapter) sp4.getAdapter();
        for(int i = 0; i < myAdapter.getCount(); i++)
        {
            if (myAdapter.getItem(i).toString().equals("{name=" + assignTo + ",user_id=" + toBeDoneBy + "}" ) )
            {
                this.sp4.setSelection(i);
                break;
            }
        }*/

     //   selectSpinnerValue(sp4);

    //    sp4.setSelection(1);
        String urlAddress = "http://192.168.137.1/project6/getassigntaskuser.php";

        new spinnerDownloader2(TaskAssign.this, urlAddress, sp4, projectID).execute();
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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






        showProject();


    }

  /*  public void selectSpinnerValue(Spinner spinner)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals("{name=" + assignTo + ",user_id=" + toBeDoneBy + "}")){
                spinner.setSelection(i);
                break;
            }else{
                spinner.setSelection(2);
                break;
            }

        }
    }*/


    public void showProject() {
        taskNamet.setText(taskName);
        taskStartDatet.setText(taskStartDate);
        taskEndDatet.setText(taskEndDate);




    }

    private void sendSinglePush(){
        final String taskNameT = taskNamet.getText().toString().trim();

    //    progressDialog.setMessage("Sending Push");
     //   progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_SINGLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    progressDialog.dismiss();

                        Toast.makeText(TaskAssign.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", "You have a new task.");
                params.put("message", taskNameT);
                params.put("email", suserID);
                return params;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void SaveTask() {

        final String taskNameT = taskNamet.getText().toString().trim();
        final String taskStartDateT = taskStartDatet.getText().toString().trim();
        final String taskEndDateT = taskEndDatet.getText().toString().trim();

        class BackgroundTask extends AsyncTask<Void, Void, String> {


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
                sendSinglePush();
                Intent intent = new Intent(getApplicationContext(), TaskRList.class);
                finish();
                startActivity(intent);

                Toast.makeText(TaskAssign.this, s, Toast.LENGTH_SHORT).show();


            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("taskID", taskID);
                hashMap.put("taskName", taskNameT);
                hashMap.put("taskStartDate", taskStartDateT);
                hashMap.put("taskEndDate", taskEndDateT);
                hashMap.put("toBeDoneBy", suserID);

                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("http://192.168.137.1/project6/updatetask.php", hashMap);

                return s;
            }
        }

        BackgroundTask task = new BackgroundTask();
        task.execute();
    }


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
    public void onClick(View v) {
        if (v == buttonSave) {
            SaveTask();
      //      sendSinglePush();
        }


    }


}


