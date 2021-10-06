package com.example.myapplication.DonorUiFrontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class DnUserProfileEditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_user_profile_edit);

        // Save edited profile
        final Button SaveEditPfButton = (Button) findViewById(R.id.DnSavePfButton);

        SaveEditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), DnUserProfileViewBalanceActivity.class);
                startActivity(i);
            }
        });

        // Cancel edited profile
        final Button CancelEditPfButton = (Button) findViewById(R.id.DnPfBackEditButton);

        CancelEditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), DnUserProfileViewBalanceActivity.class);
                startActivity(i);
            }
        });
    }
}
