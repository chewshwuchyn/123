package com.example.chewshwu.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.List;

import static com.example.chewshwu.myproject.R.id.fab;

/**
 * Created by CHEW SHWU on 12/2/2016.
 */

public class TaskRList extends AppCompatActivity{

    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";
    private String userID = "";

    //recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TaskRAdapter adapter;

    //model object for our list data
    private List<TaskR> list;

    String projectID, projectName;

    String taskName, taskStartDate, taskEndDate, createdDate, createdBy, assignTo ;
    int taskID;
    String toBeDoneBy, created;
    int taskCompleted;

   private TextView tvrPName;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskr);

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userID = preferences.getString("user_id", "0");
     //   projectID = preferences.getString("projectID", "");

        Intent intent = getIntent();
        projectID = intent.getStringExtra("projectID");
        projectName = intent.getStringExtra("projectName");

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("projectIDtask", projectID);
        editor.commit();


        tvrPName = (TextView) findViewById(R.id.tvrPName);
        tvrPName.setText(projectName);

        //initializing views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        adapter = new TaskRAdapter(list, this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        new GetTask().execute();


        fab = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TaskRList.this, TaskRCompleted.class);
                intent.putExtra("projectID", projectID);
                intent.putExtra("projectName", projectName);
                startActivity(intent);

            }
        });
    }

    private class GetTask extends AsyncTask<Void, Void, Void> {

        Context context;
        String project_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            project_url = "http://192.168.137.1/project6/taskr.php";
            String JSON_STRING = getData(project_url);

            if (JSON_STRING != null) {
                try {

                    JSONObject jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    int count = 0;

                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        taskID = JO.getInt("taskID");
                        taskName = JO.getString("taskName");
                        taskStartDate = JO.getString("taskStartDate");
                        taskEndDate = JO.getString("taskEndDate");
                        taskCompleted = JO.getInt("taskComplete");
                        createdDate = JO.getString("createdDate");
                        created = JO.getString("created");
                        createdBy = JO.getString("createdBy");
                        toBeDoneBy = JO.getString("toBeDoneBy");
                        assignTo= JO.getString("assignTo");

                        TaskR taskR = new TaskR(taskName, taskStartDate, taskEndDate, createdDate, createdBy, assignTo, taskID, toBeDoneBy, created, taskCompleted);
                        list.add(taskR);
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();

        }
    }


    public String getData(String urlAddress) {

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userID = preferences.getString("user_id", "0");

        HttpURLConnection httpURLConnection = Connector.connect(urlAddress);

        if (httpURLConnection == null) {
            return null;
        }

        InputStream inputStream = null;
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(userID,"UTF-8")+"&"+URLEncoder.encode("projectID", "UTF-8") + "=" + URLEncoder.encode(projectID, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            StringBuffer stringBuffer = new StringBuffer();

            if (bufferedReader != null) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                bufferedReader.close();

            } else {
                return null;
            }

            return stringBuffer.toString();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
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
