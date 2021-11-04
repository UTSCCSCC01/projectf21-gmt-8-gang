package com.example.myapplication.BeingSeenUiFrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.TransactionHistory.TransactionRecyclerAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

import java.util.List;

public class BsUserProfileViewDonationActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bs_user_profile_view_donation);
        recyclerView = findViewById(R.id.bs_donations_recycler_view);

        // we'll delete this after transaction set up
        setAdapter();

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
//        final Button contentPage = (Button) findViewById(R.id.BsContentButton);
//        contentPage.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getApplicationContext(), BsContentPageActivity.class);
//                startActivity(i);
//            }
//        });

        //when db setup
        ProfileInfo profileInf = new ProfileInfo();

        ImageView currentProfilePhoto = (ImageView) findViewById(R.id.imageView);
        TextView usernameTextboxInfo = (TextView) findViewById(R.id.BsPfUnameDisplay);
        TextView descriptionTextboxInfo = (TextView) findViewById(R.id.BsPfUdescDisplay);

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
//        final Button EditPfButton = (Button) findViewById(R.id.BsEditPfButton);
//
//        EditPfButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getApplicationContext(), BsUserProfileEditActivity.class);
//                startActivity(i);
//            }
//        });

        //Retrieving donation info from DB
//        Transaction transactionInfo = new Transaction();
//        transactionInfo.getBsTransactionFromDb(this,
//                new VolleyCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        setAdapter();
//                    }
//                });


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
    }

    private void setAdapter() {
        //fetch list of receivers and amounts from transaction DB
//        List<String> receivers = Transaction.getReceivers();
//        List<Long> amounts = Transaction.getAmounts();
        // we'll delete this after being seen can use transactions
        List<String> receivers = null;
        List<Long> amounts = null;
        // if data is null then return?

        TransactionRecyclerAdapter adapter = new TransactionRecyclerAdapter(receivers, amounts, "DONOR");
        // sets the layout, default animator, and adapter of recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}