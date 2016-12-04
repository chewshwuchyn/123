package com.example.chewshwu.myproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CHEW SHWU on 9/30/2016.
 */

public class ToDoList extends AppCompatActivity {

    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";
    Button bAddDo, bDoComplete;
    private EditText etDoName;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    DoAdapter doAdapter;
    ListView listView;
    Context ctx = this;
    int doIDt, doCompletet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        bAddDo = (Button)findViewById(R.id.bAddDo);
        bDoComplete = (Button)findViewById(R.id.bDoComplete);
        etDoName = (EditText)findViewById(R.id.etDoName);

        listView = (ListView) findViewById(R.id.view4);
        doAdapter = new DoAdapter(this, R.layout.row_layout4);
        listView.setAdapter(doAdapter);
     //   listView.setOnItemClickListener(onListClick);


        bAddDo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AddDoItem();;
            }
        });

        bDoComplete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DoItemCompleted();;
            }
        });


        json_string = getIntent().getExtras().getString("json_data");

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("result");

            int count = 0;
            String doName, reminderTime;
            int doID, doComplete;


            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                doID = JO.getInt("doID");
                doName = JO.getString("doName");
                reminderTime = JO.getString("reminderTime");
                doComplete = JO.getInt("doComplete");


                DoItem doItem = new DoItem(doName, reminderTime, doID, doComplete);
                doAdapter.add(doItem);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public void AddDoItem(){
        if(etDoName.getText().toString().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ToDoList.this);
            builder.setTitle("Info Missing");
            builder.setMessage("Please add the to-do item.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int item){

                }
            });

            builder.create().show();
        }else{
            SharedPreferences preference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String userID = preference.getString("user_id", "0");
            String doName = etDoName.getText().toString();
            BackgroundWorker backgroundWorker = new BackgroundWorker();
            backgroundWorker.execute(doName, userID);
            BackgroundTask2 backgroundTask2 = new BackgroundTask2(this);
            backgroundTask2.execute();
            doAdapter.notifyDataSetChanged();
            etDoName.setText("");
            etDoName.requestFocus();

        }
    }

    public void DoItemCompleted(){
        if(doAdapter.getCount()>0){
            for(int i=0; i<doAdapter.getCount(); i++){
                DoItem doItem = (DoItem) doAdapter.getItem(i);
                if(i> doAdapter.getCount()){
                    break;
                }
                if(((DoItem) doAdapter.getItem(i)).isChecked()){
                    doItem.setDoComplete(1);
                    doIDt = doItem.getDoID();
                    doCompletet = doItem.getDoComplete();
                    updateDoItem();
                    BackgroundTask2 backgroundTask2 = new BackgroundTask2(this);
                    backgroundTask2.execute();
                    doAdapter.notifyDataSetChanged();
                    continue;
                }


            }
        }
    }

    public void updateDoItem() {

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
                finish();
                //   loading.dismiss();
                Toast.makeText(ToDoList.this,s,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) {
                SharedPreferences preference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                String userID = preference.getString("user_id", "0");

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("doID",String.valueOf(doIDt));
                hashMap.put("doComplete",String.valueOf(doCompletet));
                hashMap.put("user_id",String.valueOf(userID));

                RequestHandler requestHandler = new RequestHandler();

                String s = requestHandler.sendPostRequest("http://192.168.137.1/project6/updatedocomplete.php",hashMap);

                return s;
            }
        }

        BackgroundTask task = new BackgroundTask();
        task.execute();
    }



        public class BackgroundWorker extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String doName = params[0];
            String userID = params[1];

            String result = "";
            int line;
            String register_url = "http://192.168.137.1/project6/createdoitem.php";

            try {
                URL url = new URL(register_url);
                String urlParams = "doName=" + doName + "&user_id=" + userID;
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
                result = "New to-do task added.";
                Intent intent = getIntent();
                finish();
                //  Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                //   RegisterActivity.this.startActivity(intent);
            }
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

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





    public class BackgroundTask2 extends AsyncTask<Void, Void, String> {

        Context context;
        String project_url;
        String JSON_STRING;

        BackgroundTask2 (Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();;
        }

        @Override
        protected String doInBackground(Void... params) {

            SharedPreferences preference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String userID = preference.getString("user_id", "0");
            project_url = "http://192.168.137.1/project6/todolist.php";


            try {
                URL url = new URL(project_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(userID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            // TextView textView = (TextView)findViewById(R.id.textview);
            // textView.setText(result);
            json_string = result;

            if (json_string == null) {
                Toast.makeText(getApplicationContext(), "No able to get data", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = getIntent();
                intent.putExtra("json_data", json_string);
                finish();
                //   Intent intent = new Intent(MyProject.this, ToDoList.class);
                startActivity(intent);
            }
        }
    }




    /**   private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
           List<Task> list = new ArrayList();

           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (list != null) {
                   //   String value = (String)projectAdapter.getItem(position);
                   Task task = (Task) taskAdapter.getItem(position);
                   Intent intent = new Intent(getApplicationContext(), TaskDetail.class);
                   //  intent.putExtra("json_data", String.valueOf(position));
                   intent.putExtra("projectID", task.getProjectID());
                   intent.putExtra("projectName", task.getProjectName());
                   intent.putExtra("totalNoOfTask", task.getTotalNoOfTask());
                   //       intent.putExtra("noOfUncompleteTask", task.getNoOfUncompleteTask());
                   //       intent.putExtra("curMilestone", project.getCurMilestone());
                   //       intent.putExtra("noOfMilestone", project.getNoOfMilestone());
                   //       intent.putExtra("createdOn",project.getCreatedOn());
                   //       intent.putExtra("completion", project.getCompletion());
                   //       intent.putExtra("id", project.getId());
                   startActivity(intent);

               } else {
                   Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
               }


           }
       };**/


}
























