package com.job.jobapp.CvBuilder.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.job.jobapp.CvBuilder.Models.Work;
import com.job.jobapp.R;

import java.util.List;


public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.CustomViewHolder> {

    List<Work> work;
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
      TextView textViewJobTitle,textViewCompanyName,textViewJobAddress;
        public CustomViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            imageViewDelete=itemView.findViewById(R.id.imageViewDelete);
            textViewJobTitle=itemView.findViewById(R.id.textViewJobTitle);
            textViewCompanyName=itemView.findViewById(R.id.textViewCompanyName);
            textViewJobAddress=itemView.findViewById(R.id.textViewJobAddress);
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
    public WorkAdapter(List<Work> work, Context context) {
        this.work = work;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
            return R.layout.recycler_view_work_item;
    }
    @Override
    public int getItemCount() {
        return  work.size();
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false),mListener);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

          String current=work.get(position).getCurrentlyWorkHere();
          if(current.equals("yes"))
              holder.textViewJobTitle.setText(work.get(position).getJobTitle() +" | "+work.get(position).getStartDate() +" -"+" Currently work here");
          else
              holder.textViewJobTitle.setText(work.get(position).getJobTitle() +" | "+work.get(position).getStartDate() +" -"+work.get(position).getEndDate());
          holder.textViewCompanyName.setText(work.get(position).getCompanyName() +" | "+work.get(position).getEmployer());
          holder.textViewJobAddress.setText(work.get(position).getAddress());

      }
}
