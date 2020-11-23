package com.job.jobapp.CvBuilder.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.job.jobapp.CvBuilder.Adapters.DegreeAdapter;
import com.job.jobapp.CvBuilder.Models.Degree;
import com.job.jobapp.R;

import java.util.ArrayList;
import java.util.List;


public class EducationFragment extends Fragment {
   View rootView;
   Button buttonAddNewDegree;
   RecyclerView recyclerView;
   FirebaseAuth auth;
   FirebaseUser user;
   DatabaseReference reference;
    ScrollView scrollViewMain;
    LinearLayout linearLayoutProgress;
    Button buttonBack;
    Button buttonNext;
    DegreeAdapter degreeAdapter;
    TextView textViewNoItemFound;
  List<Degree> allDegree;
  LinearLayout layoutProgress;
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
                        .child("degree");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView= inflater.inflate(R.layout.fragment_education, container, false);
        initView();
        getAllWork();
         return rootView;
    }

    private void getAllWork() {
        allDegree=new ArrayList<>();

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.getChildrenCount()<=0){
                   textViewNoItemFound.setVisibility(View.VISIBLE);
               }else {
                   allDegree.clear();
                   textViewNoItemFound.setVisibility(View.GONE);
                   for (DataSnapshot data:snapshot.getChildren()){
                       Degree degree=data.getValue(Degree.class);
                       allDegree.add(degree);
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
        degreeAdapter =new DegreeAdapter(allDegree,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(degreeAdapter);
        degreeAdapter.setOnItemClickListener(new DegreeAdapter.onItemClickListener() {
            @Override
            public void deleteClick(int position) {
                ShowToast("you Hitted Delete");
            }
        });


    }

    private void initView() {
        buttonAddNewDegree=rootView.findViewById(R.id.buttonAddNewDegree);
        recyclerView=rootView.findViewById(R.id.recyclerView);
        textViewNoItemFound=rootView.findViewById(R.id.textViewWarning);
        textViewNoItemFound.setVisibility(View.GONE);
        layoutProgress=rootView.findViewById(R.id.layoutProgress);
        layoutProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
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
        buttonAddNewDegree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNewDegreeDialog();
            }
        });
    }
    private void loadNextFragment() {

        Fragment fragment=new WorkFragment();
        FragmentTransaction fr=getActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, fragment, "work");
        fr.addToBackStack(null);
        fr.commit();

    }
    private void openAddNewDegreeDialog() {
        final Dialog dialog=new Dialog(getContext(),android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_add_new_degree);
        dialog.setCancelable(false);
        dialog.show();
        final EditText
                editTextSchoolName,
                editTextSchoolLocation,
                editTextDegreeName,
                editTextFiled,
                editTextDegreeStartDate,
                editTextDegreeEndDate;
        Button
                buttonCancel,
                buttonAdd;

        scrollViewMain=dialog.findViewById(R.id.scrollView);
        linearLayoutProgress=dialog.findViewById(R.id.layoutProgress);
                editTextSchoolName=dialog.findViewById(R.id.editTextSchoolName);
                editTextSchoolLocation=dialog.findViewById(R.id.editTextSchoolLocation);
                editTextDegreeName=dialog.findViewById(R.id.editTextDegreeName);
                editTextFiled=dialog.findViewById(R.id.editTextFiled);
                editTextDegreeStartDate=dialog.findViewById(R.id.editTextDegreeStartDate);
                editTextDegreeEndDate=dialog.findViewById(R.id.editTextDegreeEndDate);

                buttonCancel=dialog.findViewById(R.id.buttonCancel);
                buttonAdd=dialog.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Degree degree=new Degree(editTextSchoolName.getText().toString(),
                editTextSchoolLocation.getText().toString(),
                editTextDegreeName.getText().toString(),
                editTextFiled.getText().toString(),
                editTextDegreeStartDate.getText().toString(),
                editTextDegreeEndDate.getText().toString()
                );
                validateData(degree,dialog);
            }
        });
      buttonCancel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.dismiss();
          }
      });
    }

    private void validateData(Degree degree, final Dialog dialog) {
        if(degree.getSchoolName().equals(""))
           ErrorDialog("Please Provide School name");
        else if(degree.getSchoolLocation().equals(""))
            ErrorDialog("Please provide school address");
        else if(degree.getDegreeName().equals(""))
            ErrorDialog("Please provide Degree name");
        else if(degree.getFiledOfStudy().equals(""))
            ErrorDialog("Please provide filed of study");
        else if(degree.getDegreeStartDate().equals(""))
            ErrorDialog("please provide degree end Date");
        else if(degree.getDegreeEndDate().equals(""))
            ErrorDialog("please provide degree end date");
        else {
            scrollViewMain.setVisibility(View.GONE);
            linearLayoutProgress.setVisibility(View.VISIBLE);
            reference.push().setValue(degree).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    scrollViewMain.setVisibility(View.VISIBLE);
                    linearLayoutProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    ShowToast("Degree Successfully added");


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    scrollViewMain.setVisibility(View.VISIBLE);
                    linearLayoutProgress.setVisibility(View.GONE);
                    ShowToast("Filed to upload your degree please try again"+e.getMessage());
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