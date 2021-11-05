package com.example.myapplication.DonorUiFrontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.BeingSeenUiFrontend.BsMainNavbarActivity;
import com.example.myapplication.HomelessYouthUiFrontend.HyMainNavbarActivity;
import com.example.myapplication.MerchantUiFrontend.MerMainNavbarActivity;
import com.example.myapplication.OrganizationUiFrontend.OrgMainNavbarActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

public class DnDonationGoalActivity extends AppCompatActivity {
    ImageView proPic;
    TextView username, title, description, progress, percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_donation_goal);

        // set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //this.proPic = findViewById(R.id.HyPfPic);
        this.username = findViewById(R.id.HyUsername);
        this.title = findViewById(R.id.HyGoalTitle);
        this.description = findViewById(R.id.HyGoalDescription);
        this.progress = findViewById(R.id.HyAmount);
        this.percentage = findViewById(R.id.HyPercentage);

        Intent intent = getIntent();
        //byte[] mBytes = getIntent().getByteArrayExtra("proPic");
        //Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length);

        String mUsername = intent.getStringExtra("username");
        String mTitle = intent.getStringExtra("title");
        String mDescription = intent.getStringExtra("description");
        String mCurrent = intent.getStringExtra("current");
        String mPercentage = intent.getStringExtra("percentage");

        //proPic.setImageBitmap(bitmap);
        username.setText(mUsername);
        title.setText(mTitle);
        description.setText(mDescription);
        progress.setText("current: " + mCurrent);
        percentage.setText(mPercentage + "%");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}