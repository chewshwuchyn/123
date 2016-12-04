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
 * Created by CHEW SHWU on 12/2/2016.
 */

public class TaskRAdapter extends RecyclerView.Adapter<TaskRAdapter.ViewHolder> {

    private List<TaskR> list;
    private Context mCtx;
    String taskName, taskStartDate, taskEndDate, toBeDoneBy, assignTo;
    int taskID=0;
    int taskComplete;
 //   int passPosition=0;

    public TaskRAdapter(List<TaskR> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TaskRAdapter.ViewHolder holder, final int position) {
        final TaskR taskR = list.get(position);
        taskID = list.get(position).getTaskID();
        taskName = taskR.getTaskName();
        taskStartDate = taskR.getTaskStartDate();
        taskEndDate = taskR.getTaskEndDate();
        toBeDoneBy = taskR.getToBeDoneBy();
        assignTo = taskR.getAssignTo();
        holder.tvrTaskName.setText(taskR.getTaskName());
        holder.tvCreatedBy.setText("Created By: " + taskR.getCreatedBy());
        holder.tvAssignTo.setText("To Be Done By: " + taskR.getAssignTo());

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                Toast.makeText(mCtx, list.get(position).getTaskID() + " " + list.get(position).getTaskName(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mCtx, TaskAssign.class);
                                intent.putExtra("taskID",String.valueOf(list.get(position).getTaskID()));
                                intent.putExtra("taskName",list.get(position).getTaskName());
                                intent.putExtra("taskStartDate",list.get(position).getTaskStartDate());
                                intent.putExtra("taskEndDate",list.get(position).getTaskEndDate());
                                intent.putExtra("toBeDoneBy",list.get(position).getToBeDoneBy());
                                intent.putExtra("assignTo",list.get(position).getAssignTo());
                                mCtx.startActivity(intent);


                                break;
                            case R.id.menu2:

                                Toast.makeText(mCtx, list.get(position).getTaskID() + " " +list.get(position).getTaskName(), Toast.LENGTH_SHORT).show();
                                taskR.setTaskCompleted(1);
                                taskComplete = list.get(position).getTaskCompleted();
                                //       passPosition = position;
                                confirmUpdateTask();


                                break;
                            case R.id.menu3:
                                Toast.makeText(mCtx, list.get(position).getTaskID() + " " +list.get(position).getTaskName(), Toast.LENGTH_SHORT).show();
                                confirmDeleteTask();



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
        alertDialogBuilder.setMessage("Are you sure you want to set this task as completed?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updateTask(taskID, taskComplete);
                        //       list.notifyAll();
                        Intent intent = new Intent(mCtx, TaskMain.class);
                        ((TaskRList)mCtx).finish();
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



    private void deleteTask(final int taskID) {
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
                Toast.makeText(mCtx, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("taskID", String.valueOf(taskID));
                String s = requestHandler.sendPostRequest("http://192.168.137.1/project6/deletetask.php", hashMap);
                return s;
            }
        }

        BackgroundTask task = new BackgroundTask();
        task.execute();
    }



    private void confirmDeleteTask() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mCtx);
        alertDialogBuilder.setMessage("Are you sure you want to delete this task?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteTask(taskID);

                        Intent intent = new Intent(mCtx, TaskMain.class);
                        ((TaskRList)mCtx).finish();
                        mCtx.startActivity(intent);


                   //     list.remove(new TaskR());
                    //    list.notifyAll();
                     //   adapter.notifyItemRemoved(imagePosition);
                     //   adapter.notifyItemRangeChanged(imagePosition, projectMembersList.size());
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
