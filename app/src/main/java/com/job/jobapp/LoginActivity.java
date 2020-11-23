package com.job.jobapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.job.jobapp.Utails.MiscHelper;

public class LoginActivity extends AppCompatActivity {
TextView textViewLogin;
EditText editTextEmail,editTextPassword;
Button buttonLogin;
MiscHelper miscHelper=new MiscHelper();
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        initView();
    }

    private void initView() {
        editTextEmail=findViewById(R.id.editTextLoginUserEmail);
        editTextPassword=findViewById(R.id.editTextLoginUserPassword);
        textViewLogin=findViewById(R.id.textViewloginSignup);
        buttonLogin=findViewById(R.id.loginconform);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSignIn();
            }
        });
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SelectionActivity.class));
            }
        });

    }

    private void validateSignIn() {
      String email=editTextEmail.getText().toString();
      String password=  editTextPassword.getText().toString();
      if(email.equals("") || password.equals("")){
          showToast("Please provide both email and password ");
      }else if(!miscHelper.isEmailValid(email)) {
          showToast("please provide valid Email");
      }else {
          doLogin(email,password);
      }

    }

    private void doLogin(String email, String password) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Wait...");
        progressDialog.setMessage("please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    showToast("sign in successful");
                    finish();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
           showToast(e.getMessage());
            }
        });

    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }

}