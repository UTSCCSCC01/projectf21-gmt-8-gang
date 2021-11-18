package com.example.myapplication.ContentPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ViewDonationGoalActivity extends AppCompatActivity {
    ImageView proPic;
    TextView username, title, description, progress, percentage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donation_goal);

        // set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //this.proPic = findViewById(R.id.HyPfPic);
        this.username = findViewById(R.id.HyUsername);
        this.title = findViewById(R.id.HyGoalTitle);
        this.description = findViewById(R.id.HyGoalDescription);
        this.progress = findViewById(R.id.HyAmount);
        this.percentage = findViewById(R.id.HyPercentage);
        this.progressBar = findViewById(R.id.HyProgressBar);

        Intent intent = getIntent();
        //byte[] mBytes = getIntent().getByteArrayExtra("proPic");
        //Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length);

        String mUsername = intent.getStringExtra("username");
        String mTitle = intent.getStringExtra("title");
        String mDescription = intent.getStringExtra("description");
        String mCurrent = intent.getStringExtra("current");
        String mGoal = intent.getStringExtra("goal");
        String mPercentage = intent.getStringExtra("percentage");

        //proPic.setImageBitmap(bitmap);
        username.setText("by: " +  mUsername);
        title.setText(mTitle);
        description.setText(mDescription);
        //progress.setText("current: " + mCurrent);
        progress.setText(mCurrent + " / " + mGoal);
        percentage.setText(mPercentage + "%");
        progressBar.setProgress(Integer.parseInt(mPercentage));

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

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}