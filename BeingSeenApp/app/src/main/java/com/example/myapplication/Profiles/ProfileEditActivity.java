package com.example.myapplication.Profiles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.NavbarActivities.BsMainNavbarActivity;
import com.example.myapplication.NavbarActivities.DnMainNavbarActivity;
import com.example.myapplication.NavbarActivities.YouthMainNavbarActivity;
import com.example.myapplication.NavbarActivities.MerMainNavbarActivity;
import com.example.myapplication.NavbarActivities.OrgMainNavbarActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

public class ProfileEditActivity extends AppCompatActivity {
    public ImageView pick;
    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    String cameraPermission[];
    String storagePermission[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        // Save edited profile
        final Button SaveEditPfButton = (Button) findViewById(R.id.DnSavePfButton);

        SaveEditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // After clicking save all objects on screen into a object
                ImageView currentProfilePhoto = (ImageView)findViewById(R.id.DnPickImage);
                EditText usernameTextboxInfo = (EditText)findViewById(R.id.DnPfEditUsername);
                EditText descriptionTextboxInfo = (EditText)findViewById(R.id.DnPfEditDesc);

                String username = usernameTextboxInfo.getText().toString();
                String desc = descriptionTextboxInfo.getText().toString();
                String profilePic = ProfileInfo.encodeProfilePic(currentProfilePhoto);

                ProfileInfo pfObj = new ProfileInfo(username, desc, profilePic);

//                Intent i = new Intent(getApplicationContext(), DnUserProfileViewBalanceActivity.class);
//
//                /// Only for now, in future need to save as pf object and store it in mongo
//                i.putExtra("uname", username);
//                i.putExtra("desc", desc);
//                i.putExtra("pfp", profilePic);
//                startActivity(i);
  
                pfObj.sendInfoToDb(ProfileEditActivity.this,
                        new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                switch (ProfileInfo.getUserRole()) {
                                    case "ROLE_HOMELESS": startActivity(new Intent(getApplicationContext(), YouthMainNavbarActivity.class).putExtra("toast_profile", "succeed"));break;
                                    case "ROLE_DONOR": startActivity(new Intent(getApplicationContext(), DnMainNavbarActivity.class).putExtra("toast_profile", "succeed"));break;
                                    case "ROLE_ORGANIZATION": startActivity(new Intent(getApplicationContext(), OrgMainNavbarActivity.class).putExtra("toast_profile", "succeed"));break;
                                    case "ROLE_BEING_SEEN": startActivity(new Intent(getApplicationContext(), BsMainNavbarActivity.class).putExtra("toast_profile", "succeed"));break;
                                    case "ROLE_MERCHANT": startActivity(new Intent(getApplicationContext(), MerMainNavbarActivity.class).putExtra("toast_profile", "succeed"));break;
                                }
                                overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
                            }
                        });
            }
        });



        //when db setup
        ProfileInfo profileInf = new ProfileInfo();

        ImageView currentProfilePhoto = (ImageView)findViewById(R.id.DnPickImage);
        EditText usernameTextboxInfo = (EditText)findViewById(R.id.DnPfEditUsername);
        EditText descriptionTextboxInfo = (EditText)findViewById(R.id.DnPfEditDesc);

        profileInf.getInfoFromDb(this,
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.d("RESPONSE_VAR_AFTER", "Username received as " + profileInf.getUsername());

                        usernameTextboxInfo.setText(profileInf.getUsername());
                        descriptionTextboxInfo.setText(profileInf.getUserDescription());
                        currentProfilePhoto.setImageBitmap(ProfileInfo.decodeProfilePic(profileInf.getProfileImage()));
                    }
                });


        // Cancel edited profile
        final Button CancelEditPfButton = (Button) findViewById(R.id.DnPfBackEditButton);

        CancelEditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("TEST", ProfileInfo.getUserRole());
                switch (ProfileInfo.getUserRole()) {
                    case "ROLE_HOMELESS": startActivity(new Intent(getApplicationContext(), YouthMainNavbarActivity.class)); break;
                    case "ROLE_DONOR": startActivity(new Intent(getApplicationContext(), DnMainNavbarActivity.class)); break;
                    case "ROLE_ORGANIZATION": startActivity(new Intent(getApplicationContext(), OrgMainNavbarActivity.class)); break;
                    case "ROLE_BEING_SEEN": startActivity(new Intent(getApplicationContext(), BsMainNavbarActivity.class)); break;
                    case "ROLE_MERCHANT": startActivity(new Intent(getApplicationContext(), MerMainNavbarActivity.class)); break;
                }
                overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
            }
        });


        // Part of image picking code inspired by https://www.youtube.com/watch?v=jOr5kpgFpzA&ab_channel=AtifPervaiz
        // and https://www.youtube.com/watch?v=2tRw6Q2JXGo&ab_channel=coursecode

        // get camera code
        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        pick = (ImageView)findViewById(R.id.DnEditIcon);
        pick.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v){
                int picd = 0;
                if (picd == 0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        pickFromGallery();
                    }
                }
                else if (picd == 1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        pickFromGallery();
                    }
                }
            }
        });
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
    }

    //Back button on top
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(cameraPermission,STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    private boolean checkCameraPermission() {
        boolean result= ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission,CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                Uri resultUri=result.getUri();

                //load into actual image
                ImageView pfpChange = (ImageView)findViewById(R.id.DnPickImage);
                Picasso.with(this).load(resultUri).into(pfpChange);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == (PackageManager.PERMISSION_GRANTED);
                    boolean storage_accepted = grantResults[1] == (PackageManager.PERMISSION_GRANTED);

                    if (camera_accepted && storage_accepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please enable camera and storage permissions", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST:{
                if (grantResults.length > 0) {
                    boolean storage_accepted = grantResults[0] == (PackageManager.PERMISSION_GRANTED);

                    if (storage_accepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please enable storage permissions", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
}
