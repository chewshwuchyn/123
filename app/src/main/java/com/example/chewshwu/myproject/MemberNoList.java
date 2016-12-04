package com.example.chewshwu.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
 * Created by CHEW SHWU on 11/27/2016.
 */

public class MemberNoList extends AppCompatActivity {

    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";

    private String userID = "";

    MemberNoAdapter memberNoAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview4);

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userID = preferences.getString("user_id", "0");

        listView = (ListView) findViewById(R.id.view4);
        memberNoAdapter = new MemberNoAdapter(this, R.layout.row_layout6);

        new GetMemberNum().execute();


        listView.setOnItemClickListener(onListClick);

  //      Intent intent = getIntent();
  //      finish();
//        startActivity(intent);

    }

    private class GetMemberNum extends AsyncTask<Void, Void, Void> {

        Context context;
        String project_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler httpHandler = new HttpHandler();
            project_url = "http://192.168.137.1/project6/membercount.php";
            String JSON_STRING = httpHandler.downloadData(MemberNoList.this, project_url);

            if (JSON_STRING != null) {

                try {

                    JSONObject jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    int count = 0;
                    String projectName;
                    int projectID, numOfMembers;


                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        projectID = JO.getInt("projectID");
                        projectName = JO.getString("projectName");
                        numOfMembers = JO.getInt("numOfMembers");

                        MemberNo memberNo = new MemberNo(projectID, projectName, numOfMembers);
                        memberNoAdapter.add(memberNo);
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
       //     memberNoAdapter = new MemberNoAdapter(MemberNoList.this, R.layout.row_layout6);
            listView.setAdapter(memberNoAdapter);

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
        List<Project> list = new ArrayList();

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (list != null) {
                //   String value = (String)projectAdapter.getItem(position);
                MemberNo memberNo = (MemberNo) memberNoAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), ProjectMemberList.class);
                //intent.putExtra("json_data", String.valueOf(position));
                intent.putExtra("projectID", memberNo.getProjectID());
                intent.putExtra("projectName", memberNo.getProjectName());
                intent.putExtra("numOfMembers", memberNo.getNumOfMembers());
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }


        }
    };


}
