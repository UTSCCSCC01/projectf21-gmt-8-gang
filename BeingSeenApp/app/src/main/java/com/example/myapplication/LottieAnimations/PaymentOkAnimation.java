package com.example.myapplication.LottieAnimations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.NavbarActivities.BsMainNavbarActivity;
import com.example.myapplication.NavbarActivities.DnMainNavbarActivity;
import com.example.myapplication.NavbarActivities.MerMainNavbarActivity;
import com.example.myapplication.NavbarActivities.OrgMainNavbarActivity;
import com.example.myapplication.NavbarActivities.YouthMainNavbarActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

public class PaymentOkAnimation extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_ok_animation);

        lottieAnimationView = findViewById(R.id.payment_ok_lottie_animation_view);
        lottieAnimationView.animate();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (ProfileInfo.getUserRole()) {
                    case "ROLE_HOMELESS": startActivity(new Intent(getApplicationContext(), YouthMainNavbarActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)); break;
                    case "ROLE_DONOR": startActivity(new Intent(getApplicationContext(), DnMainNavbarActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)); break;
                    case "ROLE_ORGANIZATION": startActivity(new Intent(getApplicationContext(), OrgMainNavbarActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)); break;
                    case "ROLE_BEING_SEEN": startActivity(new Intent(getApplicationContext(), BsMainNavbarActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)); break;
                    case "ROLE_MERCHANT": startActivity(new Intent(getApplicationContext(), MerMainNavbarActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)); break;
                }
            }
        }, 2000);
    }

    @Override
    public void onBackPressed(){

    }
}