package com.job.jobapp.CvBuilder.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.job.jobapp.CvBuilder.Adapters.WorkAdapter;
import com.job.jobapp.CvBuilder.Models.Degree;
import com.job.jobapp.CvBuilder.Models.Work;
import com.job.jobapp.R;

import java.util.ArrayList;
import java.util.List;


public class WorkFragment extends Fragment {
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
    WorkAdapter workAdapter;
    TextView textViewNoItemFound;
  List<Work> allWork;
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
                        .child("work");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView= inflater.inflate(R.layout.fragment_work, container, false);
        initView();
        getAllDegree();
         return rootView;
    }

    private void getAllDegree() {
        allWork=new ArrayList<>();

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.getChildrenCount()<=0){
                   textViewNoItemFound.setVisibility(View.VISIBLE);
               }else {
                   allWork.clear();
                   textViewNoItemFound.setVisibility(View.GONE);
                   for (DataSnapshot data:snapshot.getChildren()){
                       Work work=data.getValue(Work.class);
                       allWork.add(work);
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
        workAdapter =new WorkAdapter(allWork,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(workAdapter);
       workAdapter.setOnItemClickListener(new WorkAdapter.onItemClickListener() {
           @Override
           public void deleteClick(int position) {

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
                ShowToast("you Pressed Next button");
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

    private void openAddNewDegreeDialog() {
        final Dialog dialog=new Dialog(getContext(),android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_add_new_work);
        dialog.setCancelable(false);
        dialog.show();
        final EditText
                editTextJobTitle,
                editTextCompanyName,
                editTextEmployer,
                editTextAddress,
                editTextStartDate,
                editTextEndDate;
        Button
                buttonCancel,
                buttonAdd;

        final CheckBox checkBoxCurrentlyWorkHere;
        scrollViewMain=dialog.findViewById(R.id.scrollView);
        linearLayoutProgress=dialog.findViewById(R.id.layoutProgress);
                editTextJobTitle=dialog.findViewById(R.id.editTextJobTitle);
                editTextCompanyName=dialog.findViewById(R.id.editTextCompanyName);
                editTextEmployer=dialog.findViewById(R.id.editTextEmployer);
                editTextAddress=dialog.findViewById(R.id.editTextAddress);
                editTextStartDate=dialog.findViewById(R.id.editTextStartDate);
                editTextEndDate=dialog.findViewById(R.id.editTextEndDate);
                checkBoxCurrentlyWorkHere=dialog.findViewById(R.id.checkBoxCurrentlyWorkHere);

                buttonCancel=dialog.findViewById(R.id.buttonCancel);
                buttonAdd=dialog.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentlyWorkHere="no";
                if(checkBoxCurrentlyWorkHere.isChecked())
                    currentlyWorkHere="yes";

                Work work=new Work(editTextJobTitle.getText().toString(),
                        editTextCompanyName.getText().toString(),
                        editTextEmployer.getText().toString(),
                        editTextAddress.getText().toString(),
                        editTextStartDate.getText().toString(),
                        editTextEndDate.getText().toString(),
                        currentlyWorkHere
                );
                validateData(work,dialog);
            }
        });
      buttonCancel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.dismiss();
          }
      });
    }

    private void validateData(Work work, final Dialog dialog) {
        if(work.getJobTitle().equals(""))
           ErrorDialog("Please provide job title");
        else if(work.getCompanyName().equals(""))
            ErrorDialog("Please Provide company Name");
        else if(work.getEmployer().equals(""))
            ErrorDialog("please provide your position in your company");
        else if(work.getAddress().equals(""))
            ErrorDialog("please provide job address");
        else if(work.getStartDate().equals(""))
            ErrorDialog("Please Provide start address");
        else if(!work.getCurrentlyWorkHere().equals("yes") && work.getEndDate().equals(""))
            ErrorDialog("please provide End date or check box currently work here ");
        else {
            scrollViewMain.setVisibility(View.GONE);
            linearLayoutProgress.setVisibility(View.VISIBLE);
            reference.push().setValue(work).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    scrollViewMain.setVisibility(View.VISIBLE);
                    linearLayoutProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    ShowToast("work Successfully added");


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    scrollViewMain.setVisibility(View.VISIBLE);
                    linearLayoutProgress.setVisibility(View.GONE);
                    ShowToast("Filed to upload your work please try again"+e.getMessage());
                }
            });

        }


    }
    private void loadNextFragment() {

        Fragment fragment=new ProjectFragment();
        FragmentTransaction fr=getActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, fragment, "Projects");
        fr.addToBackStack(null);
        fr.commit();

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