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
import android.widget.TextView;
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
import com.job.jobapp.Models.User;
import com.job.jobapp.Utails.BitmapUtility;
import com.job.jobapp.Utails.MiscHelper;
import com.job.jobapp.Utails.UtilityPermissions;

import java.io.IOException;
import java.util.UUID;

public class UserRegistration extends AppCompatActivity {
EditText editTextName,editTextPhoneNumber,editTextEmail,editTextPassword;
Button buttonRegister;
TextView textViewSignIn;
ImageView imageViewUserImage;
    private int  SELECT_FILE = 1;
    BitmapUtility bitmapUtility=new BitmapUtility();
    MiscHelper miscHelper=new MiscHelper();
    private Uri imagePath = null;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        auth=FirebaseAuth.getInstance();

        auth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        initView();
    }

    private void initView() {
     editTextName=findViewById(R.id.editTextUserRegistrationName);
     editTextPhoneNumber=findViewById(R.id.editTextUserRegistrationPhoneNumber);
     editTextEmail =findViewById(R.id.editTextUserRegistrationEmail);
     editTextPassword=findViewById(R.id.editTextUserRegistrationPassword);
     buttonRegister=findViewById(R.id.buttonUserRegistrationSubmit);
     textViewSignIn =findViewById(R.id.textViewUserRegistrationLogin);
     imageViewUserImage=findViewById(R.id.profileImage);


     imageViewUserImage.setOnClickListener(new View.OnClickListener() {
        @Override
         public void onClick(View v) {
            selectImage();
          }
      });


     textViewSignIn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             finish();
          startActivity(new Intent(UserRegistration.this,LoginActivity.class));
         }
     });


  buttonRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          doRegistration();
                }
          });


    }

    private void doRegistration() {
       String name= editTextName.getText().toString();
       String phoneNumber= editTextPhoneNumber.getText().toString();
       String email= editTextEmail.getText().toString();
       String password= editTextPassword.getText().toString();

       if(name.equals("")||email.equals("") || phoneNumber.equals("") ||password.equals("")){

           showToast("Please Provide all input Data");
       }else if(password.length()<5){
           showToast("Password length must be greater then 5 character");
       }else if(!miscHelper.isEmailValid(email)){
           showToast("Please Provide valid email");
       }else {

           User user=new User(name,phoneNumber,email,password,"");
           createClient(user);
       }
    }

    private void createClient(final User userFirebase) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Wait...");
        progressDialog.setMessage("please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        auth.createUserWithEmailAndPassword(userFirebase.getEmail(),userFirebase.getPassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                showToast("sign in successful");
                final FirebaseUser user = auth.getCurrentUser();
                final DatabaseReference reference= FirebaseDatabase.getInstance()
                        .getReference()
                        .child("users");
                if(imagePath!=null){

                    String imageUrl="";
                    imageUrl="images/" + UUID.randomUUID().toString();
                    StorageReference ref = storageReference.child(imageUrl);
                    userFirebase.setImageURL(imageUrl);
                    ref.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.child(user.getUid()).setValue(userFirebase).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        showToast("Successfully created account");
                                        progressDialog.dismiss();
                                        finish();
                                        startActivity(new Intent(UserRegistration.this,MainActivity.class));
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
                    showToast("you are submitting your application without profile Image ");
                    reference.child(user.getUid()).setValue(userFirebase).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                showToast("Successfully created account");
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(UserRegistration.this,MainActivity.class));
                            }else {
                                showToast("Application Failed to upload your data ");
                            }
                        }
                    });
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
        final boolean result = UtilityPermissions.checkPermission(UserRegistration.this);
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
                    imagePath=data.getData();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(bm!=null){
            bm = bitmapUtility.getCircularBitmap(bm);
            bm=bitmapUtility.addBorderToCircularBitmap(bm,20,getResources().getColor(R.color.app_color));
            imageViewUserImage.setImageBitmap(bm);
        }else{
            Toast.makeText(getApplicationContext(),"please Try Again",Toast.LENGTH_SHORT).show();
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }
}