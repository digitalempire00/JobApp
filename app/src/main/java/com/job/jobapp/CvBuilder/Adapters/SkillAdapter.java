package com.job.jobapp.CvBuilder.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.job.jobapp.CvBuilder.Models.Skill;
import com.job.jobapp.R;

import java.util.List;


public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.CustomViewHolder> {

    List<Skill> skills;
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
      TextView textViewSkill;
      ImageView[] imageView=new ImageView[5];
        public CustomViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            imageViewDelete=itemView.findViewById(R.id.imageViewDelete);
            textViewSkill=itemView.findViewById(R.id.textViewSkill);
            imageView[0]=itemView.findViewById(R.id.imageViewStar1);
            imageView[1]=itemView.findViewById(R.id.imageViewStar2);
            imageView[2]=itemView.findViewById(R.id.imageViewStar3);
            imageView[3]=itemView.findViewById(R.id.imageViewStar4);
            imageView[4]=itemView.findViewById(R.id.imageViewStar5);
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
    public SkillAdapter(List<Skill> skills, Context context) {
        this.skills = skills;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
            return R.layout.recycler_view_skill_item;
    }
    @Override
    public int getItemCount() {
        return  skills.size();
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false),mListener);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        int rating=skills.get(position).getSkillRating();


        for(int i=0;i<=rating;i++){
            holder.imageView[i].setImageDrawable(holder.imageViewDelete.getContext().getResources().getDrawable(R.drawable.ic_full_star));
        }
        holder.textViewSkill.setText(skills.get(position).getSkillName());
      }
}
