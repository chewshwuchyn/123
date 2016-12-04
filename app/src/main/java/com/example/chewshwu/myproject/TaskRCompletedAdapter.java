package com.example.chewshwu.myproject;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;


/**
 * Created by CHEW SHWU on 12/3/2016.
 */

public class TaskRCompletedAdapter  extends RecyclerView.Adapter<TaskRCompletedAdapter.ViewHolder>  {

    private List<TaskR> list;
    private Context mCtx;
    int taskID=0;
    int taskComplete;
 //   int passPosition=0;

    public TaskRCompletedAdapter(List<TaskR> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TaskRCompletedAdapter.ViewHolder holder, final int position) {
        final TaskR taskR = list.get(position);
        taskID = list.get(position).getTaskID();
        holder.tvrTaskName.setText(taskR.getTaskName());
        holder.tvCreatedBy.setText("Created By: " + taskR.getCreatedBy());
        holder.tvAssignTo.setText("Done By: " + taskR.getAssignTo());

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu_completed);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu4:
                           //     Toast.makeText(mCtx, list.get(position).getTaskName()+ taskID, Toast.LENGTH_SHORT).show();
                                Toast.makeText(mCtx, list.get(position).getTaskID() + " " +list.get(position).getTaskName(), Toast.LENGTH_SHORT).show();
                             //   taskID = list.get(position).getTaskID();
                                taskR.setTaskCompleted(0);
                                taskComplete = list.get(position).getTaskCompleted();
                         //       passPosition = position;
                                confirmUpdateTask();


                                break;

                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvrTaskName;
        public TextView tvCreatedBy;
        public TextView tvAssignTo;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            tvrTaskName = (TextView) itemView.findViewById(R.id.tvrTaskName);
            tvCreatedBy = (TextView) itemView.findViewById(R.id.tvCreatedBy);
            tvAssignTo = (TextView) itemView.findViewById(R.id.tvAssignTo);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
        }
    }



    private void confirmUpdateTask(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mCtx);
        alertDialogBuilder.setMessage("Are you sure you want to change this task to uncomplete?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updateTask(taskID, taskComplete);
                 //       list.notifyAll();
                        Intent intent = new Intent(mCtx, TaskRList.class);
                        ((TaskRCompleted)mCtx).finish();
                        mCtx.startActivity(intent);



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

    public void updateTask(final int taskID, final int taskComplete) {

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
                Toast.makeText(mCtx,s,Toast.LENGTH_SHORT).show();

            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("taskID",String.valueOf(taskID));
                hashMap.put("taskComplete",String.valueOf(taskComplete));

                RequestHandler requestHandler = new RequestHandler();

                String s = requestHandler.sendPostRequest("http://192.168.137.1/project6/updatetaskcomplete.php",hashMap);

                return s;
            }
        }

        BackgroundTask task = new BackgroundTask();
        task.execute();
    }





}
