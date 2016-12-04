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
 * Created by CHEW SHWU on 11/27/2016.
 */

public class MemberNoAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public MemberNoAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(MemberNo object) {
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
        MemberNoHolder memberNoHolder;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout6,parent,false);
            memberNoHolder = new MemberNoHolder();
            memberNoHolder.tvPName = (TextView)row.findViewById(R.id.tvPName);
            memberNoHolder.tvProMemberNo = (TextView)row.findViewById(R.id.tvProMemberNo);


            row.setTag(memberNoHolder);
        }
        else{
            memberNoHolder = (MemberNoHolder) row.getTag();
        }

        MemberNo memberNo = (MemberNo)this.getItem(position);


        memberNoHolder.tvPName.setText((memberNo.getProjectName()));
        memberNoHolder.tvProMemberNo.setText(memberNo.getNumOfMembers() + " project members");

        return row;
    }

    static class MemberNoHolder {
        TextView tvPName;
        TextView tvProMemberNo;


    }
}
