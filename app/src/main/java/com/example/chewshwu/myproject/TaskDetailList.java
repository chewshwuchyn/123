package com.example.chewshwu.myproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CHEW SHWU on 10/6/2016.
 */

public class TaskDetailList extends AppCompatActivity{
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    Task2Adapter task2Adapter;
    Task2Adapter.Task2Holder task2Holder;
    ListView listView;
    private TextView tvPName;
    int taskIDt, taskCompletet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview3);

        Intent intent = getIntent();
        String result = intent.getStringExtra("projectID");
        tvPName = (TextView) findViewById(R.id.tvPName);
        tvPName.setText(result);

        listView = (ListView) findViewById(R.id.view3);

        task2Adapter = new Task2Adapter(this, R.layout.row_layout3);
        listView.setAdapter(task2Adapter);
        listView.setOnItemClickListener(onListClick);

        json_string = getIntent().getExtras().getString("json_data");

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("result");

            int count = 0;
            String taskName, taskStartDate, taskEndDate;
            int taskID, checked;


            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                taskName = JO.getString("taskName");
                taskStartDate = JO.getString("taskStartDate");
                taskEndDate = JO.getString("taskEndDate");
                taskID = JO.getInt("taskID");
                checked = JO.getInt("taskComplete");


                Task2 task2 = new Task2(taskName, taskStartDate, taskEndDate, taskID, checked);
                task2Adapter.add(task2);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



   /**     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View view,
                                     int position, long id) {
                Task2 task2 = (Task2)task2Adapter.getItem( position );
                Task2Adapter.Task2Holder task2Holder = (Task2Adapter.Task2Holder) view.getTag();

            //    task2Holder.getCheckBox1().setChecked(task2Holder.checkBox1.isChecked());
            //    task2.setChecked(1);
            //    task2Holder.getCheckBox1().setEnabled(false);
            //    confirmUpdateTask();

                if(task2Holder.getCheckBox1().isChecked()){
                    task2.setChecked(1);
                    taskIDt = task2.getTaskID();
                    taskCompletet = task2.getChecked();
                    confirmUpdateTask();
                }


            }
        });**/


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

   public void updateTask() {

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
                Toast.makeText(TaskDetailList.this,s,Toast.LENGTH_LONG).show();



            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("taskID",String.valueOf(taskIDt));
                hashMap.put("taskComplete",String.valueOf(taskCompletet));

                RequestHandler requestHandler = new RequestHandler();

                String s = requestHandler.sendPostRequest("http://192.168.137.1/project6/updatetaskcomplete.php",hashMap);

                return s;
            }
        }

        BackgroundTask task = new BackgroundTask();
        task.execute();
    }

    private void confirmUpdateTask(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to set this task as completed?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updateTask();
                   //     startActivity(new Intent(Detail.this,MyProject.class));
                        Intent intent = new Intent(TaskDetailList.this, TaskMain.class);
                        finish();;
                        startActivity(intent);

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
                        /**
                         * task2Holder.checkBox1.setOnClickListener(new View.OnClickListener(){
                         * public void onClick(View v){
                         * CheckBox cb = (CheckBox) v;
                         * Task2 task2 = (Task2)cb.getTag();
                         * if(cb.isChecked()){
                         * task2.setChecked(1);
                         * }else{
                         * task2.setChecked(0);
                         * }
                         * <p>
                         * }
                         * });
                         **/



    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        List<Task2> list = new ArrayList();

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (list != null) {
                //   String value = (String)projectAdapter.getItem(position);
                Task2 task2 = (Task2) task2Adapter.getItem(position);
                Task2Adapter.Task2Holder task2Holder = (Task2Adapter.Task2Holder) view.getTag();
                task2Holder.getCheckBox1().setChecked(task2Holder.checkBox1.isChecked());
               if(task2Holder.checkBox1.isChecked()){
                   task2.setChecked(1);
                   taskIDt = task2.getTaskID();
                   taskCompletet = task2.getChecked();
                   confirmUpdateTask();
               }






                //   Intent intent = new Intent(getApplicationContext(), TaskDetail.class);
                //  intent.putExtra("json_data", String.valueOf(position));
            //    intent.putExtra("projectID", task.getProjectID());
           //     intent.putExtra("projectName", task.getProjectName());
           //     intent.putExtra("totalNoOfTask", task.getTotalNoOfTask());
                //       intent.putExtra("noOfUncompleteTask", task.getNoOfUncompleteTask());
                //       intent.putExtra("curMilestone", project.getCurMilestone());
                //       intent.putExtra("noOfMilestone", project.getNoOfMilestone());
                //       intent.putExtra("createdOn",project.getCreatedOn());
                //       intent.putExtra("completion", project.getCompletion());
                //       intent.putExtra("id", project.getId());
           //     startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }


        }
    };


 /**   @Override
    public void onClick(View v){
        CheckBox cb = (CheckBox) v;
        Task2 task2 = (Task2) cb.getTag();
        if(v == task2Holder.getCheckBox1()){
            cb.isChecked();
            task2.setChecked(1);
            confirmUpdateTask();
        }

    }**/


}



















