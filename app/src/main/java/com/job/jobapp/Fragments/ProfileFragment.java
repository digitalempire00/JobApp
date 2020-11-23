package com.job.jobapp.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.jobapp.R;
import com.job.jobapp.Utails.BitmapUtility;


public class ProfileFragment extends Fragment {
   View rootView;
ImageView imageViewUserImage;
TextView textViewUserName,textViewEmail,textViewPhoneNumber;
BitmapUtility bitmapUtility=new BitmapUtility();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootView= inflater.inflate(R.layout.fragement_profile, container, false);
       initView();
       LoadUserProfile();
         return rootView;
    }

    private void LoadUserProfile() {


    }

    private void initView() {
        imageViewUserImage=rootView.findViewById(R.id.imageViewUserImage);
        textViewUserName=rootView.findViewById(R.id.textViewUserName);
        textViewEmail=rootView.findViewById(R.id.textViewEmail);
        textViewPhoneNumber=rootView.findViewById(R.id.textViewPhone);
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.user_place_holder);
        bitmap=bitmapUtility.getCircularBitmap(bitmap);
        bitmap=bitmapUtility.addBorderToCircularBitmap(bitmap,20,getResources().getColor(R.color.widget_color));
        imageViewUserImage.setImageBitmap(bitmap);
    }
}