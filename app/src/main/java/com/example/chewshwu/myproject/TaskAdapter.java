package com.example.chewshwu.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHEW SHWU on 10/3/2016.
 */

public class TaskAdapter extends ArrayAdapter {
        List list = new ArrayList();

        public TaskAdapter(Context context, int resource) {
            super(context, resource);
        }


        public void add(Task object) {
            super.add(object);
            list.add(object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row;
            row = convertView;
            TaskHolder taskHolder;
            if(row == null) {
                LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.row_layout2,parent,false);
                taskHolder = new TaskHolder();
                taskHolder.tvPName = (TextView)row.findViewById(R.id.tvPName);
                //   projectHolder.pBrComplete = (ProgressBar)convertView.findViewById(R.id.pBrComplete);
                taskHolder.tvTotalTask = (TextView)row.findViewById(R.id.tvTotalTask);
                taskHolder.tvUncomplete = (TextView)row.findViewById(R.id.tvUncomplete);


                row.setTag(taskHolder);
            }
            else{
                taskHolder = (TaskHolder)row.getTag();
            }

            Task task = (Task)this.getItem(position);

            taskHolder.tvPName.setText((task.getProjectName()));
            //   projectHolder.pBrComplete.setProgress(0);
            //  projectHolder.pBrComplete.setProgress((project.getCurMilestone() / project.getNoOfMilestone()) * 100);
            taskHolder.tvTotalTask.setText("Total Number of Task: " + task.getTotalNoOfTask());
            taskHolder.tvUncomplete.setText("Number of Uncomplete Task: " + task.getNoOfUncompleteTask());


            return row;
        }

        static class TaskHolder {
            TextView tvPName;
            TextView tvTotalTask;
            TextView tvUncomplete;

        }


    }



