package com.job.jobapp.CvBuilder.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.job.jobapp.CvBuilder.Adapters.SkillAdapter;
import com.job.jobapp.CvBuilder.Models.Skill;
import com.job.jobapp.R;

import java.util.ArrayList;
import java.util.List;



public class SkillFragment extends Fragment {
   View rootView;
   RecyclerView recyclerView;
   FirebaseAuth auth;
   FirebaseUser user;
   DatabaseReference reference;

    Button buttonBack;
    Button buttonNext;
    SkillAdapter skillAdapter;
    TextView textViewNoItemFound;
  List<Skill> allSkills;
  LinearLayout layoutProgress;
  ImageView[] imageViewStar=new ImageView[5];
  ImageView imageViewAdd;
  EditText editTextSkill;
  int skillRating=-1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference=
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("users")
                        .child(user.getUid())
                        .child("cv")
                        .child("skill");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView= inflater.inflate(R.layout.fragment_skill, container, false);
        initView();
     getAllSkill();
         return rootView;
    }

    private void getAllSkill() {
        allSkills=new ArrayList<>();

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.getChildrenCount()<=0){
                   textViewNoItemFound.setVisibility(View.VISIBLE);
               }else {
                   allSkills.clear();
                   textViewNoItemFound.setVisibility(View.GONE);
                   for (DataSnapshot data:snapshot.getChildren()){
                       Skill skill=data.getValue(Skill.class);
                       allSkills.add(skill);
                   }

               }
               recyclerView.setVisibility(View.VISIBLE);
               layoutProgress.setVisibility(View.GONE);
               buildRecyclerView();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               ShowToast(error.getMessage() + error.getDetails());

               recyclerView.setVisibility(View.VISIBLE);
               layoutProgress.setVisibility(View.GONE);
           }
       });

    }

    private void buildRecyclerView() {
        skillAdapter =new SkillAdapter(allSkills,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(skillAdapter);
        skillAdapter.setOnItemClickListener(new SkillAdapter.onItemClickListener() {
            @Override
            public void deleteClick(int position) {
                ShowToast("you hitted deleted button");
            }
        });


    }

    private void initView() {

        recyclerView=rootView.findViewById(R.id.recyclerView);
        textViewNoItemFound=rootView.findViewById(R.id.textViewWarning);
        textViewNoItemFound.setVisibility(View.GONE);
        layoutProgress=rootView.findViewById(R.id.layoutProgress);
        layoutProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        imageViewStar[0]=rootView.findViewById(R.id.imageViewStar1);
        imageViewStar[1]=rootView.findViewById(R.id.imageViewStar2);
        imageViewStar[2]=rootView.findViewById(R.id.imageViewStar3);
        imageViewStar[3]=rootView.findViewById(R.id.imageViewStar4);
        imageViewStar[4]=rootView.findViewById(R.id.imageViewStar5);
        imageViewAdd=rootView.findViewById(R.id.imageViewAdd);
        editTextSkill=rootView.findViewById(R.id.editTextSkill);

        imageViewStar[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrangeStar(0);

            }
        });
        imageViewStar[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrangeStar(1);
            }
        });
        imageViewStar[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrangeStar(2);
            }
        });
        imageViewStar[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrangeStar(3);
            }
        });
        imageViewStar[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrangeStar(4);
            }
        });
        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(skillRating==-1){
                    ErrorDialog("Please Click the Star");
                }else {
                    Skill skill=new Skill(editTextSkill.getText().toString(),
                            skillRating);
                    validateData(skill);
                }
            }
        });
        buttonBack=rootView.findViewById(R.id.buttonBack);
        buttonNext=rootView.findViewById(R.id.buttonNext);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              loadNextFragment();
            }
        });

    }
    private void loadNextFragment() {

        Fragment fragment=new SummaryFragment();
        FragmentTransaction fr=getActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, fragment, "summary");
        fr.addToBackStack(null);
        fr.commit();

    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void arrangeStar(int i) {
       resetStar();
        for(int k=0;k<=i;k++){
            imageViewStar[k].setImageDrawable(getResources().getDrawable(R.drawable.ic_full_star));

        }
        skillRating=i;

    }

    private void resetStar() {
        for(int k=0;k<5;k++){
            imageViewStar[k].setImageDrawable(getResources().getDrawable(R.drawable.ic_empty_star));

        }
    }
    private void resetSkill() {
        resetStar();
        editTextSkill.setText("");
        skillRating=-1;
    }

    private void validateData(Skill skill) {
        if(skill.getSkillName().equals(""))
           ErrorDialog("please provide Skill Name");
        else if(skill.getSkillRating()==-1)
            ErrorDialog("Please click it least one star");
        else {

            reference.push().setValue(skill).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    ShowToast("Skill Successfully added");
                    resetSkill();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ShowToast("Filed to upload your project please try again"+e.getMessage());
                }
            });

        }


    }

    private void ErrorDialog(String message){
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    private void ShowToast(String message){
        Toast.makeText(getContext(),
                message,
                Toast.LENGTH_LONG)
                .show();
    }

}