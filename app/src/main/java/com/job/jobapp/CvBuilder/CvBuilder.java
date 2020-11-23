package com.job.jobapp.CvBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.job.jobapp.CvBuilder.Fragments.HeadingFragment;
import com.job.jobapp.R;

public class CvBuilder extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_builder);
          LoadFragment(new HeadingFragment());
    }

    private boolean LoadFragment(Fragment fragment) {
        fragmentManager=getSupportFragmentManager();
        if (fragment != null) {
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment,"heading");
            fragmentTransaction.commit();
            return true;
        }
        return false;

    }
}