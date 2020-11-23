package com.job.jobapp.CvBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.job.jobapp.CvBuilder.Fragments.EducationFragment;
import com.job.jobapp.CvBuilder.Fragments.HeadingFragment;
import com.job.jobapp.CvBuilder.Fragments.ProjectFragment;
import com.job.jobapp.CvBuilder.Fragments.SkillFragment;
import com.job.jobapp.CvBuilder.Fragments.SummaryFragment;
import com.job.jobapp.CvBuilder.Fragments.WorkFragment;
import com.job.jobapp.R;

public class CvBuilder extends AppCompatActivity implements View.OnClickListener{
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    FrameLayout frameLayoutBack,
    frameLayoutHeading,frameLayoutDegree,frameLayoutJobs,frameLayoutProjects
            ,frameLayoutSkills,frameLayoutSummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_builder);
        initView();
          LoadFragment(new HeadingFragment(),"heading");
    }

    private void initView() {
        frameLayoutBack=findViewById(R.id.frameLayoutBack);
        frameLayoutBack.setOnClickListener(this);
        frameLayoutHeading=findViewById(R.id.frameLayoutHeading);
        frameLayoutHeading.setOnClickListener(this);
        frameLayoutDegree=findViewById(R.id.frameLayoutDegree);
        frameLayoutDegree.setOnClickListener(this);
        frameLayoutJobs=findViewById(R.id.frameLayoutJob);
        frameLayoutJobs.setOnClickListener(this);
        frameLayoutProjects=findViewById(R.id.frameLayoutProject);
        frameLayoutProjects.setOnClickListener(this);
        frameLayoutSkills=findViewById(R.id.frameLayoutSill);
        frameLayoutSkills.setOnClickListener(this);
        frameLayoutSummary=findViewById(R.id.frameLayoutSummary);
        frameLayoutSummary.setOnClickListener(this);
    }

    private boolean LoadFragment(Fragment fragment,String tag) {
        fragmentManager=getSupportFragmentManager();
        if (fragment != null) {
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment,tag);
            fragmentTransaction.commit();
            return true;
        }
        return false;

    }
    @Override
    public void onClick(View v) {
        Fragment fragment=null;
        String tag="";
        int id=v.getId();
ShowToast("onClick working");
        switch (id){
            case R.id.frameLayoutBack:
                finish();
                break;
            case R.id.frameLayoutHeading:
                fragment=new HeadingFragment();
                tag="heading";
                break;
            case R.id.frameLayoutDegree:
                fragment=new EducationFragment();
                tag="education";
                break;
            case  R.id.frameLayoutJob:
                fragment=new WorkFragment();
                tag="work";
                break;
            case R.id.frameLayoutProject:
                fragment=new ProjectFragment();
                tag="project";
                break;
            case R.id.frameLayoutSill:
                fragment=new SkillFragment();
                tag="skill";
                break;
            case R.id.frameLayoutSummary:
                fragment=new SummaryFragment();
                tag="summary";
                break;
        }
        LoadFragment(fragment,tag);

    }
    private void ShowToast(String message){
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG)
                .show();
    }
}