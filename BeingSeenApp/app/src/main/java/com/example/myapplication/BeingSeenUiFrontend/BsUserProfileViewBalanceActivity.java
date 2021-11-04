package com.example.myapplication.BeingSeenUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.AboutUsFrontend.Aboutus;
import com.example.myapplication.DonorUiFrontend.DnContentPageActivity;
import com.example.myapplication.DonorUiFrontend.DnUserDonoActivity;
import com.example.myapplication.DonorUiFrontend.DnUserProfileEditActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.SearchUI.SearchPage;
import com.example.myapplication.VolleyCallBack;

public class BsUserProfileViewBalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bs_user_profile_view_balance);

        //Logout button
        final Button button = (Button) findViewById(R.id.BsPfLogoutButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


        //Button that brings donors to the content page
        final Button contentPage = (Button) findViewById(R.id.BsContentButton);
        contentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DnContentPageActivity.class);
                startActivity(i);
            }
        });

        //donate to someone button
        final Button donateButton = (Button) findViewById(R.id.donateToSomeone);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DnUserDonoActivity.class);
                startActivity(i);
            }
        });

        /// Set photo and string and stuff based on nav from prev
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            String uname = intent.getStringExtra("uname");
            String desc = intent.getStringExtra("desc");
            String base64Pfp = intent.getStringExtra("pfp");

            Bitmap bmp = ProfileInfo.decodeProfilePic(base64Pfp);

            ImageView currentProfilePhoto = (ImageView) findViewById(R.id.imageView);
            TextView usernameTextboxInfo = (TextView) findViewById(R.id.BsPfUnameDisplay);
            TextView descriptionTextboxInfo = (TextView) findViewById(R.id.BsPfUdescDisplay);

            currentProfilePhoto.setImageBitmap(bmp);
            usernameTextboxInfo.setText(uname);
            descriptionTextboxInfo.setText(desc);
        }




        //when db setup
        ProfileInfo profileInf = new ProfileInfo();

        ImageView currentProfilePhoto = (ImageView) findViewById(R.id.imageView);
        TextView usernameTextboxInfo = (TextView) findViewById(R.id.BsPfUnameDisplay);
        TextView descriptionTextboxInfo = (TextView) findViewById(R.id.BsPfUdescDisplay);

        TextView balanceTextbookInfo = (TextView)findViewById(R.id.bsPfBalance);


        profileInf.getInfoFromDb(this,
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.d("RESPONSE_VAR_AFTER", "DN Username received as " + profileInf.getUsername());

                        usernameTextboxInfo.setText(profileInf.getUsername());
                        descriptionTextboxInfo.setText(profileInf.getUserDescription());
                        currentProfilePhoto.setImageBitmap(ProfileInfo.decodeProfilePic(profileInf.getProfileImage()));
                        balanceTextbookInfo.setText(profileInf.getBalance());

                    }
                });



        //Edit profile
        final Button EditPfButton = (Button) findViewById(R.id.BsEditPfButton);

        EditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), DnUserProfileEditActivity.class);
                startActivity(i);
            }
        });



        Switch profileSwitch = (Switch) findViewById(R.id.ProfileSwitch);
        profileSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent i = new Intent(getApplicationContext(), BsUserProfileViewDonationActivity.class);
                    startActivity(i);
                } else {
                    // The toggle is disabled
                    Intent i = new Intent(getApplicationContext(), BsUserProfileViewBalanceActivity.class);
                    startActivity(i);
                }
            }
        });

        //Search Button code
        final ImageButton search = (ImageButton) findViewById(R.id.searchButton);

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(BsUserProfileViewBalanceActivity.this, SearchPage.class);
                startActivity(intent);
            }
        });
    }
}