package com.job.jobapp.CvBuilder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.job.jobapp.R;


public class SummaryFragment extends Fragment {
   View rootView;
   EditText editTextSummary;
   Button buttonNext,buttonMainMenu,submit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       rootView= inflater.inflate(R.layout.fragment_summary, container, false);
         initView();
       return rootView;
    }

    private void initView() {
        //editTextSummary=rootView.findViewsWithText(R.id.);
       // buttonNext=rootView.findViewsWithText(R.id.);
       // buttonMainMenu=rootView.findViewsWithText(R.id.);
       // submit=rootView.findViewsWithText(R.id.);
    }
}