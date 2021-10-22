package com.example.myapplication.AboutUsFrontend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Donation extends AppCompatActivity {
    public void createDonation(String username, String password, String receiver, long amount, String comment){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation);
        String receiverId = getIntent().getStringExtra("receiver");
        TextView receiver=(TextView) findViewById(R.id.dnt_reciever);
        receiver.setText(receiverId);
        TextInputEditText amountField=(TextInputEditText) findViewById(R.id.dnt_amount);
        TextInputEditText commentField=(TextInputEditText) findViewById(R.id.dnt_comment);
        Button confirm=(Button) findViewById(R.id.dnt_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //read input
                String amountStirng=amountField.getText().toString();
                String comment  = commentField.getText().toString();
                //check if required input empty
                if (amountStirng.isEmpty()) {
                    amountField.setError("please enter an amount");
                    amountField.requestFocus();
                    return;
                }
                long amount=Long.parseLong(amountField.getText().toString());
                if(amount==0){
                    amountField.setError("amount cannot be 0");
                    amountField.requestFocus();
                    return;
                }
                Transaction transaction=new Transaction();
                transaction.makeDnDonationTransaction(receiverId, comment, amount,
                        Donation.this, new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                Intent intent=new Intent();
                                intent.setClass(Donation.this, DonationStatus.class);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
}