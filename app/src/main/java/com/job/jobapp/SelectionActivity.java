package com.job.jobapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectionActivity extends AppCompatActivity {

    Button buttonCompanyRegistration;
    Button buttonUserRegistration;
    TextView textViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        initView();
    }

    private void initView() {
     buttonCompanyRegistration=findViewById(R.id.companyRegistrationBtn);
     buttonUserRegistration=findViewById(R.id.userRegistrationBtn);
     textViewLogin=findViewById(R.id.textViewSignIn);


     buttonCompanyRegistration.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(SelectionActivity.this,CompanyRegistration.class));
         }
     });

     buttonUserRegistration.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(SelectionActivity.this,UserRegistration.class));
         }
     });

     textViewLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(SelectionActivity.this,LoginActivity.class));
         }
     });


    }
}