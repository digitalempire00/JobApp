package com.job.jobapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.job.jobapp.Models.Company;
import com.job.jobapp.Utails.BitmapUtility;
import com.job.jobapp.Utails.MiscHelper;
import com.job.jobapp.Utails.UtilityPermissions;

import java.io.IOException;
import java.util.UUID;

public class CompanyRegistration extends AppCompatActivity {
    ImageView imageViewCover;
EditText editTextEmail,editTextPhNumber,editTextPassword,
        editTextCompanyName,editTextCompanyDescription,
        editTextCountry,editTextCity,editTextZip,
        editTextAddress,editTextTitleWebLink;
Button buttonSubmit;
    private int  SELECT_FILE = 1;
    BitmapUtility bitmapUtility=new BitmapUtility();
    MiscHelper miscHelper=new MiscHelper();
    private Uri filePathCover = null;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration);
        auth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        initView();
    }
    private void initView() {
      imageViewCover=findViewById(R.id.cover);
      editTextEmail=findViewById(R.id.editTextEmail);
      editTextPhNumber=findViewById(R.id.editTextPhNumber);
      editTextPassword=findViewById(R.id.editTextPassword);
      editTextCompanyName=findViewById(R.id.editTextCompanyName);
      editTextCompanyDescription=findViewById(R.id.editTextCompanyDescription);
      editTextCountry=findViewById(R.id.editTextCountry);
      editTextCity=findViewById(R.id.editTextCity);
      editTextZip=findViewById(R.id.editTextZip);
      editTextAddress=findViewById(R.id.editTextAddress);
      editTextTitleWebLink=findViewById(R.id.editTextTitleWebLink);
      buttonSubmit=findViewById(R.id.buttonSubmit);
      buttonSubmit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              validateData();
          }
      });
      imageViewCover.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              getImageFromLibrary();
          }
      });
    }
    private void getImageFromLibrary() {
        selectImage();
    }

    private void validateData() {
       String email= editTextEmail.getText().toString();
       String phNumber= editTextPhNumber.getText().toString();
       String password= editTextPassword.getText().toString();
       String companyName= editTextCompanyName.getText().toString();
       String companyDescription= editTextCompanyDescription.getText().toString();
       String country= editTextCountry.getText().toString();
       String city= editTextCity.getText().toString();
       String zip= editTextZip.getText().toString();
       String address= editTextAddress.getText().toString();
       String webLink= editTextTitleWebLink.getText().toString();

       if(email.equals("") || password.equals("") || companyName.equals("")
       ||companyDescription.equals("") ){
           showToast("Email,password,company name and company description are compulsory ");
       }else {
             if(password.length()<5){
                 showToast("password length must be greater then 5");

           }else if (!miscHelper.isEmailValid(email)){
                 showToast("Please provide correct Email");
             }else {
                 Company company=new Company("",companyName
                 ,email,password,companyDescription,phNumber,city,country,zip,address,webLink);
                 submitApplication(company);
             }
       }

    }

    private void submitApplication(final Company company) {
        //first we need to take auth
      final ProgressDialog  progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Wait...");
        progressDialog.setMessage("please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
auth.createUserWithEmailAndPassword(company.getCompanyEmail(),company.getCompanyPassword())
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    final FirebaseUser user = auth.getCurrentUser();
                    final DatabaseReference reference= FirebaseDatabase.getInstance()
                            .getReference()
                            .child("company");
                    if(filePathCover!=null){
                        String imageUrl="";
                            imageUrl="images/" + UUID.randomUUID().toString();
                            StorageReference ref = storageReference.child(imageUrl);
                            company.setImageURL(imageUrl);
                            ref.putFile(filePathCover).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference.child(user.getUid()).setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                showToast("Successfully created account");
                                                progressDialog.dismiss();
                                                finish();
                                                startActivity(new Intent(CompanyRegistration.this,MainActivity.class));
                                            }else {
                                                showToast("Application Failed to upload your data ");
                                            }
                                        }
                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                 showToast("Failed to upload image");
                                 showToast(e.getMessage());
                                }
                            });

                    }else {
                        showToast("you are submitting your application without company profile image ");
                        reference.child(user.getUid()).setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                              if(task.isSuccessful()){
                                  showToast("Successfully created account");
                                  progressDialog.dismiss();
                                  finish();
                                  startActivity(new Intent(CompanyRegistration.this,MainActivity.class));
                              }else {
                                  showToast("Application Failed to upload your data ");
                              }
                            }
                        });
                    }




                }else {
                    showToast("could not Create Account Please Try again");
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
        @Override
          public void onFailure(@NonNull Exception e) {
            showToast(e.getMessage());
            progressDialog.dismiss();


    }
       });





    }

    private void selectImage() {
        final boolean result = UtilityPermissions.checkPermission(CompanyRegistration.this);
      if(result) {
              Intent intent = new Intent();
              intent.setType("image/*");
              intent.setAction(Intent.ACTION_GET_CONTENT);//
              startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
             }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                onSelectFromGalleryResult(data);
            }
        }
    }
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                if(bm!=null){
                        filePathCover=data.getData();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(bm!=null){
                bm = bitmapUtility.setTopRounded(bm,bm.getWidth(),bm.getHeight());
                imageViewCover.setImageBitmap(bm);
        }else{
            Toast.makeText(getApplicationContext(),"please Try Again",Toast.LENGTH_SHORT).show();
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }


}