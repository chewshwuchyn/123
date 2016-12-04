package com.example.chewshwu.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by CHEW SHWU on 10/7/2016.
 */


public class Task2Adapter extends ArrayAdapter {
    List list = new ArrayList();

    public Task2Adapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Task2 object) {
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
        Task2Holder task2Holder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout3, parent, false);
            task2Holder = new Task2Holder();
            task2Holder.checkBox1 = (CheckBox) row.findViewById(R.id.checkBox1);
       //     task2Holder.tvTID = (TextView) row.findViewById(R.id.tvTID);
            task2Holder.tvTName = (TextView) row.findViewById(R.id.tvTName);
            task2Holder.tvTaskStartDate = (TextView) row.findViewById(R.id.tvTaskStartDate);
            task2Holder.tvTaskEndDate = (TextView) row.findViewById(R.id.tvTaskEndDate);

            row.setTag(task2Holder);

            task2Holder.checkBox1.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    CheckBox cb = (CheckBox) v;
                    Task2 task2 = (Task2)cb.getTag();
                    if(cb.isChecked()){
                        task2.setChecked(1);
                    }else{
                        task2.setChecked(0);
                    }

                }
            });


        } else {
            task2Holder = (Task2Holder) row.getTag();
        }


        Task2 task2 = (Task2) this.getItem(position);

        task2Holder.checkBox1.setTag(task2);

        if (task2.getChecked() == 1) {
            task2Holder.checkBox1.setChecked(true);
            task2Holder.checkBox1.setEnabled(false);
        } else {
            task2Holder.checkBox1.setChecked(false);
            task2Holder.checkBox1.setEnabled(true);
        }
        //    task2Holder.checkBox1.setChecked((task2.getChecked()));
//        task2Holder.tvTID.setText(task2.getTaskID());
        task2Holder.tvTName.setText(task2.getTaskName());
        task2Holder.tvTaskStartDate.setText("Start Date: " + task2.getTaskStartDate());
        task2Holder.tvTaskEndDate.setText("End Date: " + task2.getTaskEndDate());

        return row;
    }

    static class Task2Holder {
        CheckBox checkBox1;
    //    TextView tvTID;
        TextView tvTName;
        TextView tvTaskStartDate;
        TextView tvTaskEndDate;

        public Task2Holder(){

        }

        public Task2Holder(TextView tvTName, TextView tvTaskStartDate, TextView tvTaskEndDate, CheckBox checkBox1){
            this.checkBox1 = checkBox1;
         //   this.tvPID = tvPID;
            this.tvTName = tvTName;
            this.tvTaskStartDate = tvTaskStartDate;
            this.tvTaskEndDate = tvTaskEndDate;
        }

        public CheckBox getCheckBox1(){
            return checkBox1;
        }

        public void setCheckBox1(CheckBox checkBox1){
            this.checkBox1 = checkBox1;
        }


        public TextView getTvTName(){
            return tvTName;
        }

        public void setTvTName(TextView tvTName){
            this.tvTName = tvTName;
        }

        public TextView getTvTaskStartDate(TextView tvTaskStartDate){
            return tvTaskStartDate;
        }

        public void setTvTaskStartDate(TextView tvTaskStartDate){
            this.tvTaskStartDate = tvTaskStartDate;
        }

        public TextView getTvTaskEndDate(){
            return tvTaskEndDate;
        }

        public void setTvTaskEndDate(TextView tvTaskEndDate){
            this.tvTaskEndDate = tvTaskEndDate;
        }


    }


}





