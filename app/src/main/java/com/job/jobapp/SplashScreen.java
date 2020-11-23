package com.job.jobapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashScreen extends AppCompatActivity {
FirebaseAuth auth;
FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(this);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
          runThreadDelay();
    }
    private void runThreadDelay() {
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                    finish();
                    if(user!=null){
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    }else
                        startActivity(new Intent(SplashScreen.this, SplashScreen2.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}