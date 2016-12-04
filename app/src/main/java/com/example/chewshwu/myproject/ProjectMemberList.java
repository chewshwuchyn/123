package com.example.chewshwu.myproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
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
import java.util.HashMap;
import java.util.List;


/**
 * Created by CHEW SHWU on 11/28/2016.
 */

public class ProjectMemberList extends AppCompatActivity {
    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";
    private String userID = "";

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ProjectMemberAdapter adapter;
    private List<ProjectMembers> projectMembersList;
    String projectID;
    int assignIDs;
    String name, email, position, imageurl;
    int user_id, assignID;
    int imagePosition = 0;
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectmembers);

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userID = preferences.getString("user_id", "0");

        Bundle bundle = getIntent().getExtras();
        projectID = String.valueOf(bundle.getInt("projectID"));

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("projectID", projectID);
        editor.commit();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        projectMembersList = new ArrayList<>();

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ProjectMemberList.this, AddProjectMember.class));
            }
        });

        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ProjectMemberAdapter(this, projectMembersList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        new GetProjectMember().execute();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ItemLongClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                ProjectMembers projectMembers = projectMembersList.get(position);
                assignIDs = projectMembers.getAssignID();
                imagePosition = position;
                confirmDeleteMember();
            }
        }));

   /*     recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == projectMembersList.size()-1){
                    load_data_from_server(projectMembersList.get(projectMembersList.size()-1).getUser_id());
                }

            }
        });*/

    }


    public interface ItemLongClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ProjectMemberList.ItemLongClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ProjectMemberList.ItemLongClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildLayoutPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    private class GetProjectMember extends AsyncTask<Void, Void, Void> {

        Context context;
        String project_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            project_url = "http://192.168.137.1/project6/projectmembers.php";
            String JSON_STRING = getData(project_url);

            if (JSON_STRING != null) {
                try {

                    JSONObject jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    int count = 0;

                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        user_id = JO.getInt("user_id");
                        name = JO.getString("name");
                        email = JO.getString("email");
                        position = JO.getString("position");
                        imageurl = JO.getString("imageurl");
                        assignID = JO.getInt("assignID");

                        ProjectMembers projectMembers = new ProjectMembers(user_id, name, email, position, imageurl, assignID);
                        projectMembersList.add(projectMembers);
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

        HttpURLConnection httpURLConnection = Connector.connect(urlAddress);

        if (httpURLConnection == null) {
            return null;
        }

        InputStream inputStream = null;
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("projectID", "UTF-8") + "=" + URLEncoder.encode(projectID, "UTF-8");
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


    private void deleteMember() {
        class BackgroundTask extends AsyncTask<Void, Void, String> {
            //   ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //    loading = ProgressDialog.show(ViewEmployee.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //    loading.dismiss();
                Toast.makeText(ProjectMemberList.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("assignID", String.valueOf(assignIDs));
                String s = requestHandler.sendPostRequest("http://192.168.137.1/project6/deletemember.php", hashMap);
                return s;
            }
        }

        BackgroundTask task = new BackgroundTask();
        task.execute();
    }



    private void confirmDeleteMember() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this project member?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteMember();

                        projectMembersList.remove(imagePosition);
                        adapter.notifyItemRemoved(imagePosition);
                        adapter.notifyItemRangeChanged(imagePosition, projectMembersList.size());
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


}
