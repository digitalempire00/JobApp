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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.job.jobapp.CvBuilder.Models.Heading;
import com.job.jobapp.R;
import com.job.jobapp.Utails.MiscHelper;

public class HeadingFragment extends Fragment {
   View rootView;
EditText editTextFirstName,
        editTextLastName,
        editTextCountry,
        editTextCity,
        editTextZip,
        editTextPhoneNumber,
        editTextEmail,
        editTextFacebookURL,
        editTextInURL,
        editTextGitHubURL,
        editTextTwitterURL;
Button buttonSubmit,
        buttonNext;
MiscHelper miscHelper=new MiscHelper();
FirebaseAuth auth;
FirebaseUser user;
DatabaseReference reference;
ScrollView scrollViewMain;
LinearLayout linearLayoutProgress;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

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
                        .child("heading");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView= inflater.inflate(R.layout.fragment_heading, container, false);
        initView();
        getHeadingInfo();
         return rootView;
    }

    private void getHeadingInfo() {
     reference.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.getChildrenCount()<=0){
                   ShowToast("No heading found please create New Heading");
               }else {
                   Heading heading=snapshot.getValue(Heading.class);
                   buttonNext.setVisibility(View.VISIBLE);
                   assert heading != null;
                   UpdateUI(heading);
               }
             VisibleUI();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
           VisibleUI();
         }
     });
    }
    private void VisibleUI(){
        scrollViewMain.setVisibility(View.VISIBLE);
        linearLayoutProgress.setVisibility(View.GONE);
       }
       private void invisibleUI(){
           scrollViewMain.setVisibility(View.GONE);
           linearLayoutProgress.setVisibility(View.VISIBLE);
       }
    private void UpdateUI(Heading heading) {
        editTextFirstName.setText(heading.getFirstName());
        editTextLastName.setText(heading.getLastName());
        editTextCountry.setText(heading.getCountry());
        editTextCity.setText(heading.getCity());
        editTextZip.setText(heading.getZip());
        editTextPhoneNumber.setText(heading.getPhoneNumber());
        editTextEmail.setText(heading.getEmail());
        editTextFacebookURL.setText(heading.getFacebookURL());
        editTextInURL.setText(heading.getLinkedinURL());
        editTextGitHubURL.setText(heading.getGitHubURL());
        editTextTwitterURL.setText(heading.getTwitterURL());
    }

    private void initView() {
          editTextFirstName=rootView.findViewById(R.id.editTextFirstName);
          editTextLastName=rootView.findViewById(R.id.editTextLastName);
          editTextCountry=rootView.findViewById(R.id.editTextCountry);
          editTextCity=rootView.findViewById(R.id.editTextCity);
          editTextZip=rootView.findViewById(R.id.editTextZip);
          editTextPhoneNumber=rootView.findViewById(R.id.editTextPhoneNumber);
          editTextEmail=rootView.findViewById(R.id.editTextEmail);
          editTextFacebookURL=rootView.findViewById(R.id.editTextFacebookURL);
          editTextInURL=rootView.findViewById(R.id.editTextInURL);
          editTextGitHubURL=rootView.findViewById(R.id.editTextGitHubURL);
          editTextTwitterURL=rootView.findViewById(R.id.editTextTwitterURL);
          buttonSubmit=rootView.findViewById(R.id.buttonSubmit);
          buttonNext=rootView.findViewById(R.id.buttonNext);
          linearLayoutProgress=rootView.findViewById(R.id.layoutProgress);
          scrollViewMain=rootView.findViewById(R.id.scrollViewMain);
          scrollViewMain.setVisibility(View.GONE);
         invisibleUI();
          buttonSubmit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  validateData();
              }
          });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextFragment();
                ShowToast("Next Button Clicked");
            }
        });
    }

    private void loadNextFragment() {

        Fragment fragment=new EducationFragment();
        FragmentTransaction fr=getActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, fragment, "Education");
        fr.addToBackStack(null);
        fr.commit();

    }

    private void validateData() {
        Heading heading;
        heading=new Heading(editTextFirstName.getText().toString(),
        editTextLastName.getText().toString(),
        editTextCountry.getText().toString(),
        editTextCity.getText().toString(),
        editTextZip.getText().toString(),
        editTextPhoneNumber.getText().toString(),
        editTextEmail.getText().toString(),
        editTextFacebookURL.getText().toString(),
        editTextInURL.getText().toString(),
        editTextGitHubURL.getText().toString(),
        editTextTwitterURL.getText().toString());
        if(heading.getFirstName().equals("")){
            ErrorDialog("First Name is empty please provide first Name");
        }else if(heading.getLastName().equals("")){
            ErrorDialog("Please Provide Last Name");
        }else if(heading.getCountry().equals("")){
            ErrorDialog("Please provide country Name");
        }else if(heading.getCity().equals("")){
            ErrorDialog("Please Provide City Name");
        }else if(heading.getZip().equals("")){
            ErrorDialog("Please provide Zip code of your Location");
        }else if(heading.getEmail().equals("")){
            ErrorDialog("Please Provide Email");
        }else if(heading.getPhoneNumber().equals("")){
            ErrorDialog("Please Provide Phone Number");
        }else if(!miscHelper.isEmailValid(heading.getEmail()))
        {
            ErrorDialog("Your Email in invalid please Provide valid Email");
        }else {
            uploadHeading(heading);
        }
    }

    private void uploadHeading(Heading heading) {
         invisibleUI();
        reference.setValue(heading).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ShowToast("Heading Successfully Updated");
                buttonNext.setVisibility(View.VISIBLE);
                VisibleUI();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ErrorDialog("Please try again my be your internet issues "+e.getMessage());
                VisibleUI();
            }
        });
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