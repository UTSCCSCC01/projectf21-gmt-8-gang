package com.example.myapplication.HomelessYouthUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class HyUserProfileEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_user_profile_edit);

        // Save edited profile
        final Button SaveEditPfButton = (Button) findViewById(R.id.HySavePfButton);

        SaveEditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HyUserProfileViewActivity.class);
                startActivity(i);
            }
        });

        // Cancel edited profile
        final Button CancelEditPfButton = (Button) findViewById(R.id.HyPfExitEditButton);

        CancelEditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HyUserProfileViewActivity.class);
                startActivity(i);
            }
        });
    }
}