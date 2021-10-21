package com.example.myapplication.HomelessYouthUiFrontend;

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

import java.util.List;

public class HyUserProfileViewDonationActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_user_profile_view_donation);
        recyclerView = findViewById(R.id.hy_donations_recycler_view);

        //Logout button
        final Button button = (Button) findViewById(R.id.HyPfLogoutButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        //Button for creating donation goal
        final Button setGoal = (Button) findViewById(R.id.SetGoal);

        setGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HySetDonationGoalActivity.class);
                startActivity(i);
            }
        });


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

        //Retrieving donation info from DB
//        Transaction transactionInfo = new Transaction();
//
//        TextView homelessAmountInfo = (TextView) findViewById(R.id.SetHyAmount1);
//        TextView senderInfo = (TextView) findViewById(R.id.SetSender1);
//
//        transactionInfo.getHyTransactionFromDb(this,
//                new VolleyCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        Log.d("RESPONSE_VAR_AFTER", "Transaction display go");
//                        Log.d("Transaction_DEVVAR",transactionInfo.getSenders().get(0).toString());
//
//                        homelessAmountInfo.setText(String.valueOf(transactionInfo.getAmounts().get(0)) + " credits");
//                        senderInfo.setText("From   " + transactionInfo.getSenders().get(0));
//
//
//                    }
//                });
//      Retrieving donation info from DB
        Transaction transactionInfo = new Transaction();
        transactionInfo.getHyTransactionFromDb(this,
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        setAdapter();
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

    private void setAdapter() {
        //fetch list of receivers and amounts from transaction DB
        List<String> senders = Transaction.getSenders();
        List<Long> amounts = Transaction.getAmounts();

        // if data is null then return??? (not sure)

        TransactionRecyclerAdapter adapter = new TransactionRecyclerAdapter(senders, amounts, "HOMELESS");
        // sets the layout, default animator, and adapter of recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
