package com.job.jobapp.CvBuilder.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.job.jobapp.CvBuilder.Models.Project;
import com.job.jobapp.R;

import java.util.List;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.CustomViewHolder> {

    List<Project> projects;
    Context context;
    private  onItemClickListener mListener;
      public  interface onItemClickListener{
        void  deleteClick(int position);
        }
     public  void setOnItemClickListener(onItemClickListener listener){
          mListener=listener;
     }
   public static class  CustomViewHolder extends RecyclerView.ViewHolder{
      ImageView imageViewDelete;
      TextView textViewProjectName,
              textViewDescriptions,
              textViewProjectURL;

        public CustomViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            imageViewDelete=itemView.findViewById(R.id.imageViewDelete);
            textViewProjectName=itemView.findViewById(R.id.textViewProjectName);
            textViewDescriptions=itemView.findViewById(R.id.textViewDescriptions);
            textViewProjectURL=itemView.findViewById(R.id.textViewProjectURL);
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position=getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.deleteClick(position);
                        }
                    }
                }
            });
        }
    }
    public ProjectAdapter(List<Project> projects, Context context) {
        this.projects = projects;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
            return R.layout.recycler_view_project_item;
    }
    @Override
    public int getItemCount() {
        return  projects.size();
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false),mListener);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
          holder.textViewProjectName.setText(projects.get(position).getProjectName()
                  +" |  "+projects.get(position).getProjectStartDate()
                  +" - "+projects.get(position).getProjectEndDate());

          holder.textViewDescriptions.setText(projects.get(position).getProjectDescriptions());
          holder.textViewProjectURL.setText(projects.get(position).getProjectURL());



      }
}
