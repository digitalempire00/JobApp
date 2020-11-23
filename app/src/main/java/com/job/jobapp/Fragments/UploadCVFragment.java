package com.job.jobapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.job.jobapp.R;


public class UploadCVFragment extends Fragment {
   View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       rootView= inflater.inflate(R.layout.fragment_create_cv, container, false);
         return rootView;
    }
}