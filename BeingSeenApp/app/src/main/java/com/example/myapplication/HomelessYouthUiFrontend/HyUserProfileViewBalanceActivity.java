package com.example.myapplication.HomelessYouthUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

public class HyUserProfileViewBalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_user_profile_view_balance);

        //Logout button
        final Button button = (Button) findViewById(R.id.HyPfLogoutButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


        /// Set photo and string and stuff based on nav from prev
//        Intent intent = getIntent();
//        if(intent.getExtras() != null) {
//            String uname = intent.getStringExtra("uname");
//            String desc = intent.getStringExtra("desc");
//            String base64Pfp = intent.getStringExtra("pfp");
//
//            ProfileInfo profileInf = new ProfileInfo(uname, desc, base64Pfp);
//
//
//            //when db setup
//            //ProfileInfo profileInf = new ProfileInfo();
////            profileInf.getInfoFromDb(this);
//
//            ImageView currentProfilePhoto = (ImageView) findViewById(R.id.HyPfPfpDisplay);
//            TextView usernameTextboxInfo = (TextView) findViewById(R.id.HyPfUnameDisplay);
//            TextView descriptionTextboxInfo = (TextView) findViewById(R.id.HyPfUdescDisplay);
//
//            usernameTextboxInfo.setText(profileInf.getUsername());
//            descriptionTextboxInfo.setText(profileInf.getUserDescription());
//            currentProfilePhoto.setImageBitmap(ProfileInfo.decodeProfilePic(profileInf.getProfileImage()));
//        }


        //when db setup
        ProfileInfo profileInf = new ProfileInfo();

        ImageView currentProfilePhoto = (ImageView) findViewById(R.id.HyPfPfpDisplay);
        TextView usernameTextboxInfo = (TextView) findViewById(R.id.HyPfUnameDisplay);
        TextView descriptionTextboxInfo = (TextView) findViewById(R.id.HyPfUdescDisplay);

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








        //Edit profile
        final Button EditPfButton = (Button) findViewById(R.id.HyEditPfButton);

        EditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HyUserProfileEditActivity.class);
                startActivity(i);
            }
        });

        Switch profileSwitch = (Switch) findViewById(R.id.ProfileSwitch);
        profileSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent i = new Intent(getApplicationContext(), HyUserProfileViewDonationActivity.class);
                    startActivity(i);
                } else {
                    // The toggle is disabled
                    Intent i = new Intent(getApplicationContext(), HyUserProfileViewBalanceActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}