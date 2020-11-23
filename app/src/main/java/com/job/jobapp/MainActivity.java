package com.job.jobapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.job.jobapp.CvBuilder.CvBuilder;
import com.job.jobapp.Fragments.AppMainFragment;
import com.job.jobapp.Fragments.ProfileFragment;
import com.job.jobapp.Utails.BitmapUtility;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference userTypeReference;
    TextView textName,textEmail;
    ImageView profileImage;
    NavigationView navigationView;
    String userType;
    BitmapUtility bitmapUtility=new BitmapUtility();
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavigation();
       intiUser();
       LoadFragment(new AppMainFragment());



    }

    private void intiUser() {
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        userTypeReference= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        userTypeReference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String nameUser,emailUser,imageURL;

                          if(snapshot.getChildrenCount()>0){
                             nameUser=snapshot.child("fullName").getValue(String.class);
                             emailUser=snapshot.child("email").getValue(String.class);
                              imageURL=snapshot.child("imageURL").getValue(String.class);
                             userType="user";
                           downloadImage(imageURL);
                              textName.setText(nameUser);
                              textEmail.setText(emailUser);
                              navigationView.getMenu().clear();
                              navigationView.inflateMenu(R.menu.user_options);


                          }else  {

                              DatabaseReference reference=FirebaseDatabase
                                      .getInstance()
                                      .getReference()
                                      .child("company")
                                      .child(user.getUid());

                              userType="company";
                             reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                                              if(snapshot.child("companyDescription").exists()) {
                                                  String nameUser, emailUser, URL;
                                                  nameUser = snapshot.child("companyName").getValue(String.class);
                                                  emailUser = snapshot.child("companyEmail").getValue(String.class);
                                                  URL = snapshot.child("imageURL").getValue(String.class);
                                                 downloadImage(URL);
                                                  textName.setText(nameUser);
                                                  textEmail.setText(emailUser);
                                                  navigationView.getMenu().clear();
                                                  navigationView.inflateMenu(R.menu.company_options);
                                              }else {
                                                  showToast("something going wrung");
                                              }

                                          }
                                          @Override
                                          public void onCancelled(@NonNull DatabaseError error) {
                                              showToast("Error could't inflate UI");
                                          }
                                      });
                          }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showToast("Error could't inflate UI");
                    }
                });


    }

    private void downloadImage(String url) {
     showToast(url);
 if(url.equals("")){
        showToast("no Image");
       return;
     }
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        StorageReference imageReference = storageReference.child(url);
        long MAXBYTS = 1024 * 1024*5;
        imageReference.getBytes(MAXBYTS).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap mbitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ImageViewRounded(mbitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast(e.getMessage());
            }
        });


    }

    private void initNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        textName =  header.findViewById(R.id.textViewHeaderNIK);
        textEmail =  header.findViewById(R.id.textViewHeaderPassword);
        profileImage=header.findViewById(R.id.imageViewImage);
        ImageViewRounded(BitmapFactory.decodeResource(getResources(), R.drawable.user_place_holder));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
           if(userType.equals("user")){
               if(id==R.id.nav_cv){
                  LoadFragment(new ProfileFragment());
               }else if(id==R.id.nav_update_upload_cv){
                   startActivity(new Intent(MainActivity.this, CvBuilder.class));
               }else if(id==R.id.nav_notifications){
                   showToast("notification clicked");
               }else if(id==R.id.nav_Search_job){
                   showToast("Search job clicked ");
               }else if(id==R.id.logout){
                   signOut();
               }
           }else if(userType.equals("company")) {
               if(id==R.id.com_logout){
                   signOut();
               }

           }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private boolean LoadFragment(Fragment fragment) {
        fragmentManager=getSupportFragmentManager();
        if (fragment != null) {
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            return true;
        }
        return false;

    }

    private void signOut() {
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user!=null){
            auth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }
    private  void ImageViewRounded(Bitmap profileBtm){
        profileBtm = bitmapUtility.getCircularBitmap(profileBtm);
        profileImage.setImageBitmap(profileBtm);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}