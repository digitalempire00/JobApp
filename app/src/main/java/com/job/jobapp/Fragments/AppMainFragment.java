package com.job.jobapp.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.job.jobapp.R;

public class AppMainFragment extends Fragment {
   View rootView;
ImageView imageViewShare,imageViewFB,imageViewIN,imageViewTW,imageViewINS;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView= inflater.inflate(R.layout.fragment_main_app, container, false);
       initView();
         return rootView;
    }
    private void initView() {
        imageViewShare=rootView.findViewById(R.id.imageViewShare);
                imageViewFB=rootView.findViewById(R.id.imageViewFacebook);
                imageViewIN=rootView.findViewById(R.id.imageViewIn);
                imageViewTW=rootView.findViewById(R.id.imageViewTwitter);
                imageViewINS=rootView.findViewById(R.id.imageViewInsta);
        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            shareApp();
            }
        });
        imageViewFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Need facebook page URL to process");
            }
        });
        imageViewIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Need Linkein page URL to process");
            }
        });
        imageViewINS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Need Instagram page URL to process");
            }
        });
        imageViewTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Need Twitter page URL to process");
            }
        });
    }
    private void showToast(String s) {
    }
    private void shareApp() {
        String strAppLink = "https://play.google.com/store/apps/details?id=com.job.jobapp";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, strAppLink);
            startActivity(Intent.createChooser(shareIntent, "Share Via..."));
    }
}