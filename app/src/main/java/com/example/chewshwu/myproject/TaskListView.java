package com.example.chewshwu.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHEW SHWU on 10/3/2016.
 */

public class TaskListView extends AppCompatActivity {
        String json_string;
        JSONObject jsonObject;
        JSONArray jsonArray;
        TaskAdapter taskAdapter;
        ListView listView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listview2);

            listView = (ListView) findViewById(R.id.view2);

            taskAdapter = new TaskAdapter(this, R.layout.row_layout2);
            listView.setAdapter(taskAdapter);
            listView.setOnItemClickListener(onListClick);



            json_string = getIntent().getExtras().getString("json_data");

            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("result");

                int count = 0;
                String projectName;
                int projectID, totalNoOfTask, noOfUncompleteTask;


                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    projectID = JO.getInt("projectID");
                    projectName = JO.getString("projectName");
                    totalNoOfTask = JO.getInt("totalNoOfTask");
                    noOfUncompleteTask = JO.getInt("noOfUncompleteTask");


                    Task task = new Task(projectID, projectName, totalNoOfTask, noOfUncompleteTask);
                    taskAdapter.add(task);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
            List<Task> list = new ArrayList();
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list != null) {
                    //   String value = (String)projectAdapter.getItem(position);
                    Task task = (Task)taskAdapter.getItem(position);
                    Intent intent = new Intent(getApplicationContext(), TaskDetail.class);
                  //  intent.putExtra("json_data", String.valueOf(position));
                    intent.putExtra("projectID",task.getProjectID());
                    intent.putExtra("projectName",task.getProjectName());
                    intent.putExtra("totalNoOfTask", task.getTotalNoOfTask());
             //       intent.putExtra("noOfUncompleteTask", task.getNoOfUncompleteTask());
             //       intent.putExtra("curMilestone", project.getCurMilestone());
             //       intent.putExtra("noOfMilestone", project.getNoOfMilestone());
             //       intent.putExtra("createdOn",project.getCreatedOn());
             //       intent.putExtra("completion", project.getCompletion());
             //       intent.putExtra("id", project.getId());
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
                }


            }
        };




    }
















