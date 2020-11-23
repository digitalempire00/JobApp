package com.job.jobapp.CvBuilder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.job.jobapp.R;


public class SummaryFragment extends Fragment {
   View rootView;
   EditText editTextSummary;
   Button buttonBack,buttonMainMenu,submit;
   FirebaseAuth auth;
   FirebaseUser user;
   DatabaseReference reference;
   ScrollView scrollView;
   LinearLayout linearLayoutProgress;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference= FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(user.getUid())
                .child("cv")
                .child("summary");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       rootView= inflater.inflate(R.layout.fragment_summary, container, false);
         initView();
         getSummery();
       return rootView;
    }

    private void getSummery() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String text=  snapshot.child("summary").getValue(String.class);
                  editTextSummary.setText(text);
                  resetView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ErrorDialog("Could't not get connection\n"+ error.getMessage() +"\n"+error.getDetails());
            }
        });

    }

    private void resetView() {
        scrollView.setVisibility(View.VISIBLE);
        linearLayoutProgress.setVisibility(View.GONE);
    }

    private void initView() {
    editTextSummary=rootView.findViewById(R.id.editTextSummary);
    buttonBack=rootView.findViewById(R.id.buttonBack);
    buttonMainMenu=rootView.findViewById(R.id.buttonMainMenu);
    submit=rootView.findViewById(R.id.buttonSubmit);
         scrollView=rootView.findViewById(R.id.scrollView);
         linearLayoutProgress=rootView.findViewById(R.id.layoutProgress);
         scrollView.setVisibility(View.GONE);
         linearLayoutProgress.setVisibility(View.VISIBLE);

        LinearLayout linearLayoutProgress;
    buttonBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    });

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validateAndSubmit();


        }
    });

    buttonMainMenu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    });

    }

    private void validateAndSubmit() {
        String summary=editTextSummary.getText().toString();
        if(summary.equals("")){
            ErrorDialog("Please add summery");
        }else if(summary.length()<30){
            ErrorDialog("summary length must be greater then 30 character");
        }else {
            reference.child("summery").setValue(summary).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    ShowToast("Summary Successfully Added");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ErrorDialog("Could't upload your summery please try again \n"+e.getMessage());
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