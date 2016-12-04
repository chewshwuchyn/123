package com.example.chewshwu.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayListView extends AppCompatActivity {

    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";

    private String userID = "";

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ProjectAdapter projectAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_view);

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userID = preferences.getString("user_id", "0");

        listView = (ListView) findViewById(R.id.view);

        projectAdapter = new ProjectAdapter(this, R.layout.row_layout);
        listView.setAdapter(projectAdapter);
        listView.setOnItemClickListener(onListClick);



        json_string = getIntent().getExtras().getString("json_data");

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("result");

            int count = 0;
            String name, startDate, finishDate, type, createdOn;
            int curMilestone, noOfMilestone,id;


            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("name");
                startDate = JO.getString("startDate");
                finishDate = JO.getString("finishDate");
                curMilestone = JO.getInt("curMilestone");
                noOfMilestone = JO.getInt("noOfMilestone");
                type = JO.getString("type");
                createdOn = JO.getString("createdDate");
                id = JO.getInt("id");


                Project project = new Project(name, startDate, finishDate, curMilestone,noOfMilestone,type,createdOn, id);
                projectAdapter.add(project);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void CreateNewProject(View view){
        startActivity(new Intent(this, CreateProject.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


 private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        List<Project> list = new ArrayList();
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          if(list != null) {
           //   String value = (String)projectAdapter.getItem(position);
              Project project = (Project)projectAdapter.getItem(position);
              Intent intent = new Intent(getApplicationContext(), Detail.class);
              //intent.putExtra("json_data", String.valueOf(position));
              intent.putExtra("name",project.getName());
              intent.putExtra("type",project.getType());
              intent.putExtra("startDate", project.getStartDate());
              intent.putExtra("finishDate", project.getFinishDate());
              intent.putExtra("curMilestone", project.getCurMilestone());
              intent.putExtra("noOfMilestone", project.getNoOfMilestone());
              intent.putExtra("createdOn",project.getCreatedOn());
              intent.putExtra("completion", project.getCompletion());
              intent.putExtra("id", project.getId());
              startActivity(intent);

          }
            else{
              Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
         }


        }
    };




    }













