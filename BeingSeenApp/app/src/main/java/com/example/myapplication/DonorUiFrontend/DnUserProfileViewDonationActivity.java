package com.example.myapplication.DonorUiFrontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.TransactionRecyclerAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;

import java.util.ArrayList;
import java.util.List;

public class DnUserProfileViewDonationActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_user_profile_view_donation);
        recyclerView = findViewById(R.id.dn_donations_recycler_view);

        setAdapter();

        //Logout button
        final Button button = (Button) findViewById(R.id.DnPfLogoutButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


        //when db setup
        ProfileInfo profileInf = new ProfileInfo();

        ImageView currentProfilePhoto = (ImageView) findViewById(R.id.imageView);
        TextView usernameTextboxInfo = (TextView) findViewById(R.id.DnPfUnameDisplay);
        TextView descriptionTextboxInfo = (TextView) findViewById(R.id.DnPfUdescDisplay);

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
        final Button EditPfButton = (Button) findViewById(R.id.DnEditPfButton);

        EditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), DnUserProfileEditActivity.class);
                startActivity(i);
            }
        });


        //Retrieving donation info from DB
//        Transaction transactionInfo = new Transaction();
//
//        TextView donorAmountInfo = (TextView) findViewById(R.id.SetDnAmount1);
//        TextView receiverInfo = (TextView) findViewById(R.id.SetReceiver1);
//
//        transactionInfo.getDnTransactionFromDb(this,
//                new VolleyCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        Log.d("RESPONSE_VAR_AFTER", "Transaction display go");
//
//
//                        donorAmountInfo.setText(String.valueOf(transactionInfo.getAmounts().get(0)) + " credits");
//                        receiverInfo.setText("To   " + transactionInfo.getReceivers().get(0));
//
//
//                    }
//                });




        Switch profileSwitch = (Switch) findViewById(R.id.ProfileSwitch);
        profileSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent i = new Intent(getApplicationContext(), DnUserProfileViewDonationActivity.class);
                    startActivity(i);
                } else {
                    // The toggle is disabled
                    Intent i = new Intent(getApplicationContext(), DnUserProfileViewBalanceActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void setAdapter() {

        // creating some fake data for testing: (we should get data from db instead)
        ArrayList<String> receivers = new ArrayList<String>();
        receivers.add("angus");
        receivers.add("humuhumu");
        ArrayList<Long> amounts = new ArrayList<Long>();
        amounts.add(100L);
        amounts.add(200L);

//        Retrieving donation info from DB
//        Transaction transactionInfo = new Transaction();
//        transactionInfo.getDnTransactionFromDb(this,
//                new VolleyCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        Log.d("RESPONSE_VAR_AFTER", "Transaction display go");
//                    }
//                });
//        List<String> receivers = transactionInfo.getReceivers();
//        List<Long> amounts = transactionInfo.getAmounts();

        // if data is null then return
        TransactionRecyclerAdapter adapter = new TransactionRecyclerAdapter(receivers, amounts);
        // sets the layout, default animator, and adapter of recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
