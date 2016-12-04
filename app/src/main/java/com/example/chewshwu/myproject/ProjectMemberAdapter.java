package com.example.chewshwu.myproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.List;
import com.bumptech.glide.Glide;

/**
 * Created by CHEW SHWU on 11/27/2016.
 */

public class ProjectMemberAdapter extends RecyclerView.Adapter<ProjectMemberAdapter.ViewHolder>{

    private Context context;
    private List<ProjectMembers> projectMembersList;

    public ProjectMemberAdapter(Context context, List<ProjectMembers> projectMembersList) {
        this.context = context;
        this.projectMembersList = projectMembersList;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(projectMembersList.get(position).getName());
        holder.email.setText(projectMembersList.get(position).getEmail());
        holder.position.setText(projectMembersList.get(position).getPosition());
        Glide.with(context).load(projectMembersList.get(position).getImageurl()).into(holder.imageView);

     /*   holder.setItemLongClickListener(new ProjectMemberList.ItemLongClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, projectMembersList.get(position).getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return projectMembersList.size();
    }


    public  class ViewHolder extends  RecyclerView.ViewHolder {

        public TextView name;
        public TextView email;
        public TextView position;
        public ImageView imageView;
   //     ProjectMemberList.ItemLongClickListener itemLongClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            position = (TextView) itemView.findViewById(R.id.position);
            imageView = (ImageView) itemView.findViewById(R.id.image);

     //       itemView.setOnClickListener(this);
        }


    /*    public void setItemLongClickListener(ProjectMemberList.ItemLongClickListener ic){
            this.itemLongClickListener = ic;
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemLongClickListener.onLongClick(v, getLayoutPosition());
            return false;
        }

        @Override
        public void onClick(View v) {

        }*/
    }
}

