package com.example.myapplication.HomelessYouthUiFrontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class HySetDonationGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_set_donation_goal);

        TextClock textClock = (TextClock) findViewById(R.id.date);
        textClock.setTimeZone("Canada/Eastern");
        textClock.setFormat12Hour("dd-MMM-yyyy");

        //Publish Button code
        final Button publish = (Button) findViewById(R.id.publishGoalButton);
        publish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),HyUserProfileViewBalanceActivity.class);
                startActivity(i);
            }
        });
    }
}