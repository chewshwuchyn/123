package com.example.chewshwu.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHEW SHWU on 10/10/2016.
 */

public class DoAdapter extends ArrayAdapter {
        List list = new ArrayList();

        public DoAdapter(Context context, int resource) {
            super(context, resource);
        }


        public void add(DoItem object) {
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
            DoAdapter.DoHolder doHolder;
            if (row == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.row_layout4, parent, false);
                doHolder = new DoHolder();
                doHolder.cbDone = (CheckBox) row.findViewById(R.id.cbDone);
                //     task2Holder.tvTID = (TextView) row.findViewById(R.id.tvTID);
                doHolder.tvDoName = (TextView) row.findViewById(R.id.tvDoName);
             //   task2Holder.tvTaskStartDate = (TextView) row.findViewById(R.id.tvTaskStartDate);
              //  task2Holder.tvTaskEndDate = (TextView) row.findViewById(R.id.tvTaskEndDate);

                row.setTag(doHolder);

                doHolder.cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        CheckBox cb = (CheckBox) buttonView;
                        DoItem doItem = (DoItem) cb.getTag();
                        doItem.setChecked(isChecked);


                                      }

                                                           });

            /**    task2Holder.checkbox1.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        CheckBox cb = (CheckBox) v;
                        Task2 task2 = (Task2)cb.getTag();
                        if(cb.isChecked()){
                            task2.setChecked(1);
                        }else{
                            task2.setChecked(0);
                        }

                    }
                });**/

            } else {
                doHolder = (DoHolder) row.getTag();
            }


            DoItem doItem = (DoItem) this.getItem(position);

            doHolder.cbDone.setTag(doItem);

          /**  if (doHolder.getChecked() == 1) {
                task2Holder.checkBox1.setChecked(true);
                task2Holder.checkBox1.setEnabled(false);
            } else {
                task2Holder.checkBox1.setChecked(false);
                task2Holder.checkBox1.setEnabled(true);
            }**/
            //    task2Holder.checkBox1.setChecked((task2.getChecked()));
//        task2Holder.tvTID.setText(task2.getTaskID());
            doHolder.tvDoName.setText(doItem.getDoName());
        //    task2Holder.tvTaskStartDate.setText("Start Date: " + task2.getTaskStartDate());
       //     task2Holder.tvTaskEndDate.setText("End Date: " + task2.getTaskEndDate());

            return row;
        }

        static class DoHolder {
            CheckBox cbDone;
            //    TextView tvTID;
            TextView tvDoName;
         //   TextView tvTaskStartDate;
         //   TextView tvTaskEndDate;

            public DoHolder(){

            }

            public DoHolder(TextView tvDoName, CheckBox cbDone){
                this.cbDone = cbDone;
                //   this.tvPID = tvPID;
                this.tvDoName = tvDoName;
          //      this.tvTaskStartDate = tvTaskStartDate;
          //      this.tvTaskEndDate = tvTaskEndDate;
            }

            public CheckBox getCheckBox1(){
                return cbDone;
            }

            public void setCheckBox1(CheckBox cbDone){
                this.cbDone = cbDone;
            }


            public TextView getTvDoName(){
                return tvDoName;
            }

            public void setTvDoName(TextView tvDoName){
                this.tvDoName = tvDoName;
            }





    }






}
