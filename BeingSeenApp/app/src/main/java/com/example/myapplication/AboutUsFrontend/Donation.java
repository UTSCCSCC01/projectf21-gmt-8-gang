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
        TextInputEditText usernameField=(TextInputEditText) findViewById(R.id.dnt_username);
        TextInputEditText passwordField=(TextInputEditText) findViewById(R.id.dnt_password);
        TextInputEditText commentField=(TextInputEditText) findViewById(R.id.dnt_comment);
        Button confirm=(Button) findViewById(R.id.dnt_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //read input
                String amountStirng=amountField.getText().toString();
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
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
                if (username.isEmpty()) {
                    usernameField.setError("please enter a username");
                    usernameField.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordField.setError("please enter a password");
                    passwordField.requestFocus();
                    return;
                }
                //verify username&password
                DonorVerify verification=new DonorVerify(Donation.this);
                verification.logIn(username,password);
                //case1: not a valid combination
                if(verification.valid){
                    new AlertDialog.Builder(Donation.this)
                            .setTitle("Invalid Username or Password")
                            .setMessage("Invalid username password pair")
                            .setPositiveButton("Confirm",null)
                            .show();
                }
                //case2: is a donor, do transaction
                else if(verification.role.equals("ROLE_DONOR")){
                    //old method using transactionInfo in comment:
                    //TransactionInfo transaction=new TransactionInfo(username,receiverId,amount,comment);
                    //transaction.setToken(token);
                    //transaction.sendInfoToDb(Donation.this, new VolleyCallBack() {
                        //@Override
                        //only go to donation status if success
                        //public void onSuccess() {
                            //Intent intent=new Intent();
                           // intent.setClass(Donation.this, DonationStatus.class);
                           // startActivity(intent);
                        //}
                    //});
                    //new method using Transaction:
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
                //case3: not a donor
                else{
                    new AlertDialog.Builder(Donation.this)
                            .setTitle("Invalid role")
                            .setMessage("You are not a donor")
                            .setPositiveButton("Confirm",null)
                            .show();
                }
            }
        });
    }
}