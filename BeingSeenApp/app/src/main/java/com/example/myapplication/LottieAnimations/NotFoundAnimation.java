package com.example.myapplication.LottieAnimations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.R;

public class NotFoundAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_found_animation);

        Intent intent = getIntent();
        if (intent.hasExtra("message")) {
            TextView messageField = findViewById(R.id.error_message);
            messageField.setText(intent.getStringExtra("message"));
        }

        getSupportActionBar().hide();
        LottieAnimationView lottieAnimationView = findViewById(R.id.not_found_animation_view);
        lottieAnimationView.playAnimation();
    }

    Boolean hasPressedBack = false;
    @Override
    public void onBackPressed(){
        if(hasPressedBack){
            hasPressedBack = false;
            finish();
        }
        Toast.makeText(getApplicationContext(),"Press back button again to leave", Toast.LENGTH_SHORT).show();
        hasPressedBack = true;
    }
}