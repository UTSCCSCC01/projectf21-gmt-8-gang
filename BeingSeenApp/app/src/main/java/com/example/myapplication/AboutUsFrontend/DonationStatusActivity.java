package com.example.myapplication.AboutUsFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.SignUpAndLogin.SignUpActivity;
import com.example.myapplication.R;

public class DonationStatusActivity extends AppCompatActivity {

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
                intent.setClass(DonationStatusActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}