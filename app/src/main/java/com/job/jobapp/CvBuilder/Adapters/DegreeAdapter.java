package com.job.jobapp.CvBuilder.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.job.jobapp.CvBuilder.Models.Degree;
import com.job.jobapp.R;

import java.util.List;


public class DegreeAdapter extends RecyclerView.Adapter<DegreeAdapter.CustomViewHolder> {

    List<Degree> degree;
    Context context;
    private  onItemClickListener mListener;
      public  interface onItemClickListener{
        void  deleteClick(int position);
        }
     public  void setOnItemClickListener(onItemClickListener listener){
          mListener=listener;
     }
   public static class  CustomViewHolder extends RecyclerView.ViewHolder{
       TextView textViewDegreeName,
               textViewFiledOfStudy,
               textViewInstituteName,
               textViewInstituteAddress;
       ImageView imageViewDelete;
        public CustomViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
           textViewDegreeName=itemView.findViewById(R.id.textViewDegreeName);
           textViewFiledOfStudy=itemView.findViewById(R.id.textViewFiledOfStudy);
           textViewInstituteName=itemView.findViewById(R.id.textViewInstituteName);
           textViewInstituteAddress=itemView.findViewById(R.id.textViewInstituteAddress);
           imageViewDelete=itemView.findViewById(R.id.imageViewDelete);
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
    public DegreeAdapter(List<Degree> degree, Context context) {
        this.degree = degree;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
            return R.layout.recycler_view_education_item;
    }
    @Override
    public int getItemCount() {
        return  degree.size();
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false),mListener);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
          holder.textViewDegreeName.setText(degree.get(position).getDegreeName()
          +" - "+degree.get(position).getDegreeStartDate()
          +" - "+degree.get(position).getDegreeEndDate());
          holder.textViewFiledOfStudy.setText(degree.get(position).getFiledOfStudy());
          holder.textViewInstituteName.setText(degree.get(position).getSchoolName());
          holder.textViewInstituteAddress.setText(degree.get(position).getSchoolLocation());

      }
}
