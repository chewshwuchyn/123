package com.example.chewshwu.myproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
 * Created by CHEW SHWU on 11/29/2016.
 */

public class spinnerDataUser extends AsyncTask<Void, Void, Integer>{

        Context c;
        Spinner sp;
        String jsonData;
        JSONObject jsonObject;
        JSONArray jsonArray;

        ProgressDialog pd;

        ArrayList<HashMap<String, String>> spiarray = new ArrayList<HashMap<String, String>>();

        ArrayList<String> prolist;

        public spinnerDataUser(Context c, Spinner sp, String jsonData) {
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


            SimpleAdapter adapter = new SimpleAdapter(c, spiarray, R.layout.row_layout5, new String[]{"user_id", "name"}, new int[] {R.id.tvID, R.id.tvName});
            sp.setAdapter(adapter);

            //     sp.setAdapter(new ArrayAdapter<String>(c, R.layout.row_layout5, prolist));


     /*   sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                sprojectID = spinnerobjpro.get(position).get("projectID").toString();
                sprojectName = spinnerobjpro.get(position).get("projectName").toString();



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

                HashMap<String, String> map;

                for(int i=0; i<jsonArray.length();i++){
                    JSONObject JO = jsonArray.getJSONObject(i);

                    map = new HashMap<String, String>();
                    map.put("user_id", JO.getString("user_id"));
                    map.put("name", JO.getString("name"));

                    spiarray.add(map);

                }

                return 1;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return 0;
        }


    }


