package com.example.myapplication.AboutUsFrontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.BeingSeenUiFrontend.BsUserProfileViewBalanceActivity;
import com.example.myapplication.DonorUiFrontend.DnUserProfileViewBalanceActivity;
import com.example.myapplication.HomelessYouthUiFrontend.HySignUpActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MerchantUiFrontend.MerUserProfileViewBalanceActivity;
import com.example.myapplication.OrganizationUiFrontend.OrgUserProfileViewBalanceActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

public class DonationStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_status);
        TextView transactionStatus=(TextView) findViewById(R.id.dns_status);
        TextView transactionInfo=(TextView) findViewById(R.id.dns_info);
        Button backHome=(Button) findViewById(R.id.dns_backHome);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(DonationStatus.this, HySignUpActivity.class);
                startActivity(intent);
            }
        });
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // prob there's a better way to do this
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Log.i("hyyy", ProfileInfo.getUserRole());
//        switch (ProfileInfo.getUserRole()) {
//            case "ROLE_DONOR": startActivity(new Intent(DonationStatus.this, DnUserProfileViewBalanceActivity.class));
//            case "ROLE_ORGANIZATION": startActivity(new Intent(DonationStatus.this, OrgUserProfileViewBalanceActivity.class));
//            case "ROLE_BEING_SEEN": startActivity(new Intent(DonationStatus.this, BsUserProfileViewBalanceActivity.class));
//            case "ROLE_MERCHANT": startActivity(new Intent(DonationStatus.this, MerUserProfileViewBalanceActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }
}