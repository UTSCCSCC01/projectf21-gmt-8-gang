package com.example.myapplication.MerchantUiFrontend;

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

import com.example.myapplication.Adapters.TransactionRecyclerAdapter;
import com.example.myapplication.DonorUiFrontend.DnContentPageActivity;
import com.example.myapplication.DonorUiFrontend.DnUserProfileEditActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;

import java.util.List;


public class MerUserProfileViewDonationActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer_user_profile_view_donation);
        recyclerView = findViewById(R.id.mer_donations_recycler_view);


        //Logout button
        final Button button = (Button) findViewById(R.id.MerPfLogoutButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        //Button that brings donors to the content page
        final Button contentPage = (Button) findViewById(R.id.MerContentButton);

        contentPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), DnContentPageActivity.class);
                startActivity(i);
            }
        });

        //when db setup
        ProfileInfo profileInf = new ProfileInfo();

        ImageView currentProfilePhoto = (ImageView) findViewById(R.id.imageView);
        TextView usernameTextboxInfo = (TextView) findViewById(R.id.MerPfUnameDisplay);
        TextView descriptionTextboxInfo = (TextView) findViewById(R.id.MerPfUdescDisplay);

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
        final Button EditPfButton = (Button) findViewById(R.id.MerEditPfButton);

        EditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), DnUserProfileEditActivity.class);
                startActivity(i);
            }
        });

        //Retrieving donation info from DB
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
                    Intent i = new Intent(getApplicationContext(), com.example.myapplication.MerchantUiFrontend.MerUserProfileViewDonationActivity.class);
                    startActivity(i);
                } else {
                    // The toggle is disabled
                    Intent i = new Intent(getApplicationContext(), MerUserProfileViewBalanceActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void setAdapter() {
        //fetch list of receivers and amounts from transaction DB
        List<String> receivers = Transaction.getSenders();
        List<Long> amounts = Transaction.getAmounts();
        TransactionRecyclerAdapter adapter = new TransactionRecyclerAdapter(receivers, amounts, "MERCHANT");
        // sets the layout, default animator, and adapter of recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
