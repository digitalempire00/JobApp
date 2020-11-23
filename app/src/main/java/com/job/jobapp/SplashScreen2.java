package com.job.jobapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SplashScreen2 extends AppCompatActivity {
    Button buttonLogin;
    TextView textViewGuestLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        initView();
    }

    private void initView() {
        buttonLogin=findViewById(R.id.buttonLogin);
        textViewGuestLogin=findViewById(R.id.textViewGuestLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SplashScreen2.this,LoginActivity.class));

            }
        });
        textViewGuestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}