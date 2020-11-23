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
import com.job.jobapp.CvBuilder.Adapters.ProjectAdapter;
import com.job.jobapp.CvBuilder.Adapters.WorkAdapter;
import com.job.jobapp.CvBuilder.Models.Project;
import com.job.jobapp.CvBuilder.Models.Work;
import com.job.jobapp.R;

import java.util.ArrayList;
import java.util.List;


public class ProjectFragment extends Fragment {
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
    ProjectAdapter projectAdapter;
    TextView textViewNoItemFound;
  List<Project> allProjects;
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
                        .child("project");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView= inflater.inflate(R.layout.fragment_project, container, false);
        initView();
        getAllProjects();
         return rootView;
    }

    private void getAllProjects() {
        allProjects=new ArrayList<>();

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.getChildrenCount()<=0){
                   textViewNoItemFound.setVisibility(View.VISIBLE);
               }else {
                   allProjects.clear();
                   textViewNoItemFound.setVisibility(View.GONE);
                   for (DataSnapshot data:snapshot.getChildren()){
                       Project project=data.getValue(Project.class);
                       allProjects.add(project);
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
        projectAdapter =new ProjectAdapter(allProjects,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(projectAdapter);
        projectAdapter.setOnItemClickListener(new ProjectAdapter.onItemClickListener() {
            @Override
            public void deleteClick(int position) {
                ShowToast("you hit delete button");
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

    private void openAddNewDegreeDialog() {
        final Dialog dialog=new Dialog(getContext(),android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_add_projects);
        dialog.setCancelable(false);
        dialog.show();
        final EditText
                editTextProjectName,
                editTextProjectDescription,
                editTextURL,
                editTextStartDate,
                editTextEndDate;
        Button
                buttonCancel,
                buttonAdd;


        scrollViewMain=dialog.findViewById(R.id.scrollView);
        linearLayoutProgress=dialog.findViewById(R.id.layoutProgress);
                editTextProjectName=dialog.findViewById(R.id.editTextProjectName);
                editTextProjectDescription=dialog.findViewById(R.id.editTextProjectDescription);
                editTextURL=dialog.findViewById(R.id.editTextURL);
                editTextStartDate=dialog.findViewById(R.id.editTextStartDate);
                editTextEndDate=dialog.findViewById(R.id.editTextEndDate);


                buttonCancel=dialog.findViewById(R.id.buttonCancel);
                buttonAdd=dialog.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Project project=new Project(editTextProjectName.getText().toString(),
                        editTextProjectDescription.getText().toString(),
                        editTextURL.getText().toString(),
                        editTextStartDate.getText().toString(),
                        editTextEndDate.getText().toString()
                );
                validateData(project,dialog);
            }
        });
      buttonCancel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.dismiss();
          }
      });
    }

    private void validateData(Project project, final Dialog dialog) {
        if(project.getProjectName().equals(""))
           ErrorDialog("please provide project Name");
        else if(project.getProjectDescriptions().equals(""))
            ErrorDialog("Please provide project description");
        else if(project.getProjectStartDate().equals(""))
            ErrorDialog("Please provide project start date");

        else {
            scrollViewMain.setVisibility(View.GONE);
            linearLayoutProgress.setVisibility(View.VISIBLE);
            reference.push().setValue(project).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    scrollViewMain.setVisibility(View.VISIBLE);
                    linearLayoutProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    ShowToast("project Successfully added");


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    scrollViewMain.setVisibility(View.VISIBLE);
                    linearLayoutProgress.setVisibility(View.GONE);
                    ShowToast("Filed to upload your project please try again"+e.getMessage());
                }
            });

        }


    }
    private void loadNextFragment() {

        Fragment fragment=new SkillFragment();
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