package com.example.myapplication.OrganizationUiFrontend;

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
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;

import java.util.List;

public class OrgUserProfileViewDonationActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_user_profile_view_donation);
        recyclerView = findViewById(R.id.org_donations_recycler_view);

        //Logout button
        final Button button = (Button) findViewById(R.id.OrgPfLogoutButton);

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
        TextView usernameTextboxInfo = (TextView) findViewById(R.id.OrgPfUnameDisplay);
        TextView descriptionTextboxInfo = (TextView) findViewById(R.id.OrgPfUdescDisplay);

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
//        final Button EditPfButton = (Button) findViewById(R.id.OrgEditPfButton);
//
//        EditPfButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getApplicationContext(), OrgUserProfileEditActivity.class);
//                startActivity(i);
//            }
//        });
//
        // Retrieving donation info from DB
        Transaction transactionInfo = new Transaction();

        transactionInfo.getDnTransactionFromDb(this,
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        setAdapter();
                    }
                });


        Switch profileSwitch = (Switch) findViewById(R.id.ProfileSwitch);
        profileSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent i;
                if (isChecked) {
                    // The toggle is enabled
                    i = new Intent(getApplicationContext(), OrgUserProfileViewDonationActivity.class);
                } else {
                    // The toggle is disabled
                    i = new Intent(getApplicationContext(), OrgUserProfileViewBalanceActivity.class);
                }
                startActivity(i);
            }
        });
    }

    private void setAdapter() {
        //fetch list of receivers and amounts from transaction DB
        List<String> senders = Transaction.getSenders();
        List<Long> amounts = Transaction.getAmounts();

        TransactionRecyclerAdapter adapter = new TransactionRecyclerAdapter(senders, amounts, "ORGANIZATION", "to");
        // sets the layout, default animator, and adapter of recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}