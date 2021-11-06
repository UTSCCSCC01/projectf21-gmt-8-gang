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

import com.example.myapplication.BeingSeenUiFrontend.BsMainNavbarActivity;
import com.example.myapplication.BeingSeenUiFrontend.BsUserProfileViewBalanceActivity;
import com.example.myapplication.DonorUiFrontend.DnContentPageActivity;
import com.example.myapplication.DonorUiFrontend.DnMainNavbarActivity;
import com.example.myapplication.DonorUiFrontend.DnUserProfileViewBalanceActivity;
import com.example.myapplication.HomelessYouthUiFrontend.HyMainNavbarActivity;
import com.example.myapplication.MerchantUiFrontend.MerMainNavbarActivity;
import com.example.myapplication.MerchantUiFrontend.MerUserProfileViewBalanceActivity;
import com.example.myapplication.OrganizationUiFrontend.OrgMainNavbarActivity;
import com.example.myapplication.OrganizationUiFrontend.OrgUserProfileViewBalanceActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

public class Aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        TextView abs_title=(TextView)findViewById(R.id.aboutus_title);
        TextView abs_txt=(TextView)findViewById(R.id.aboutus_txt);
        abs_txt.setText("The project aims to create an app that helps homeless youth by" +
                " removing certains stigmas of donating to the homeless, such as the stigma " +
                "that homeless youths might use the money to purchase alcohol rather than paying " +
                "off their loans/mortgages.");
        Button abs_donate=(Button) findViewById(R.id.donateus_btn);
        abs_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(Aboutus.this, Donation.class);
                //info need db complete
                intent.putExtra("receiver","seesee");
                startActivity(intent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("hyyy", ProfileInfo.getUserRole());
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}