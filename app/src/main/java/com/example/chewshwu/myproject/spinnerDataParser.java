package com.example.chewshwu.myproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CHEW SHWU on 11/22/2016.
 */

public class spinnerDataParser extends AsyncTask<Void, Void, Integer> {


    Context c;
    Spinner sp;
    String sprojectID, sprojectName;
    String jsonData;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayAdapter adapter;
    spinnerObjProj s = new spinnerObjProj();
    private SharedPreferences preferences;
    public static final String PREF_NAME = "PrefKey";
    public static final String KEY_USERID = "user_id";

    ProgressDialog pd;

    ArrayList<HashMap<String, String>> spinnerobjpro = new ArrayList<HashMap<String, String>>();

    ArrayList<String> spinnerobjpro2 = new ArrayList<>();

    ArrayList<String> prolist;

    public spinnerDataParser(Context c, Spinner sp, String jsonData) {
        this.c = c;
        this.sp = sp;
        this.jsonData = jsonData;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        pd= new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Parsing...Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result){
        super.onPostExecute(result);

        pd.dismiss();

        if(result==0){
            Toast.makeText(c, "Unable to Parse", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(c, "Parse Successfully", Toast.LENGTH_SHORT).show();
        }


        SimpleAdapter adapter = new SimpleAdapter(c, spinnerobjpro, R.layout.row_layout5, new String[]{"projectID", "projectName"}, new int[] {R.id.tvID, R.id.tvName});
        sp.setAdapter(adapter);


  //     sp.setAdapter(new ArrayAdapter<String>(c, R.layout.row_layout5, prolist));


    /*    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                sprojectID = spinnerobjpro.get(position).get("projectID").toString();
                sprojectName = spinnerobjpro.get(position).get("projectName").toString();

                Intent i=new Intent(c, TaskMain.class);
                i.putExtra("projectID", sprojectID);
                i.putExtra("projectName", sprojectName);
                c.startActivity(i);


          //      s.setProjectID(sprojectID);
           //     s.setProjectName(sprojectName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

  //      adapter = new ArrayAdapter(c, android.R.layout.simple_list_item_1, spinnerobjpro);
  //      sp.setAdapter(adapter);

     /*   sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(c, spinnerobjpro.get(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    private int parseData(){

        prolist = new ArrayList<String>();
        ArrayList<spinnerObjProj> sopro = new ArrayList<spinnerObjProj>();

        try {

            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("result");

        //    spinnerObjProj s=null;

            HashMap<String, String> map;

            for(int i=0; i<jsonArray.length();i++){
                JSONObject JO = jsonArray.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("projectID", JO.getString("projectID"));
                map.put("projectName", JO.getString("projectName"));

                spinnerobjpro.add(map);

                spinnerObjProj sop = new spinnerObjProj();

                sop.setProjectID(JO.getInt("projectID"));
                sop.setProjectName(JO.getString("projectName"));
                sopro.add(sop);
            }

      /*      jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("result");


            for(int i=0; i<jsonArray.length();i++){
                JSONObject JO = jsonArray.getJSONObject(i);

                int projectID = JO.getInt("projectID");
                String projectName = JO.getString("projectName");

                spinnerObjProj sop = new spinnerObjProj();

                sop.setProjectID(projectID);
                sop.setProjectName(projectName);
                sopro.add(sop);

                prolist.add(projectID, projectName);

            }*/






        /*   jsonObject = new JSONObject(jsonData);
           jsonArray = jsonObject.getJSONArray("result");

            int count = 0;
            int projectID;
            String projectName;

            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                projectID = JO.getInt("projectID");
                projectName = JO.getString("projectName");

                spinnerobjpro.add(projectName);
          //      spinnerObjProj spop = new spinnerObjProj(projectID, projectName);
         //       adapter.add(spop);
                count++;


            }*/





       /*     JSONArray ja = new JSONArray(jsonData);
            JSONObject jo=null;

            spinnerobjpro.clear();
            spinnerObjProj s=null;

            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);

                int projectID = jo.getInt("projectID");
                String projectName = jo.getString("projectName");

                s = new spinnerObjProj();
                s.setProjectID(projectID);
                s.setProjectName(projectName);

                spinnerobjpro.add(projectID, projectName);
            }*/

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }





}
