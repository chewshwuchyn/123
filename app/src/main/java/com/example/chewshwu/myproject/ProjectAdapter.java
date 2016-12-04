package com.example.chewshwu.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.lang.Math;
import java.util.List;

/**
 * Created by CHEW SHWU on 5/22/2016.
 */
public class ProjectAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public ProjectAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Project object) {
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
        ProjectHolder projectHolder;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            projectHolder = new ProjectHolder();
            projectHolder.tvName = (TextView)row.findViewById(R.id.tvName);
        //   projectHolder.pBrComplete = (ProgressBar)convertView.findViewById(R.id.pBrComplete);
            projectHolder.tvStartDate = (TextView)row.findViewById(R.id.tvStartDateN);
            projectHolder.tvFinishDate = (TextView)row.findViewById(R.id.tvFinishDate);
            projectHolder.tvCompletion = (TextView)row.findViewById(R.id.tvCompletion);

            row.setTag(projectHolder);
        }
        else{
            projectHolder = (ProjectHolder)row.getTag();
        }

        Project project = (Project)this.getItem(position);
        double percent = Math.round(((double)project.getCurMilestone() / (double)project.getNoOfMilestone()) * 100);

        projectHolder.tvName.setText((project.getName()));
     //   projectHolder.pBrComplete.setProgress(0);
      //  projectHolder.pBrComplete.setProgress((project.getCurMilestone() / project.getNoOfMilestone()) * 100);
        projectHolder.tvStartDate.setText("Start Date: " + project.getStartDate());
        projectHolder.tvFinishDate.setText("Finish Date: " + project.getFinishDate());
        projectHolder.tvCompletion.setText("Percentage of completion: " +percent+ "%" );


        return row;
    }

    static class ProjectHolder {
        TextView tvName;
   //   ProgressBar pBrComplete;
        TextView tvStartDate;
        TextView tvFinishDate;
        TextView tvCompletion;

    }


}

