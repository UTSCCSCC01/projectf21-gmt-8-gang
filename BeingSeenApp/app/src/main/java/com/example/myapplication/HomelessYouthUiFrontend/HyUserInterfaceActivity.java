package com.example.myapplication.HomelessYouthUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.DonorUiFrontend.DnUserProfileViewBalanceActivity;
import com.example.myapplication.R;

public class HyUserInterfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_user_interface);

        //View Profile code
        final Button button = (Button) findViewById(R.id.HyPfProfileButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HyUserProfileViewBalanceActivity.class);
                startActivity(i);
            }
        });

        final Button button2 = (Button) findViewById(R.id.DnPfProfileButton);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), DnUserProfileViewBalanceActivity.class);
                startActivity(i);
            }
        });
    }
}