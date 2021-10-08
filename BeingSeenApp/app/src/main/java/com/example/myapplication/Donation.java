package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Donation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation);
        String recieverId = getIntent().getStringExtra("reciever");
        String senderId=getIntent().getStringExtra("sender");
        TextView reciever=(TextView) findViewById(R.id.dnt_reciever);
        //reciever.setText(db.getName(recieverId));
        TextInputEditText amount=(TextInputEditText) findViewById(R.id.dnt_amount);
        Button confirm=(Button) findViewById(R.id.dnt_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a transaction id in
                Intent intent=new Intent();
                intent.setClass(Donation.this, DonationStatus.class);
                intent.putExtra("transactionId","transactionId by db");
                startActivity(intent);
            }
        });
    }
}