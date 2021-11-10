package com.example.myapplication.AboutUsFrontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.NavbarActivities.BsMainNavbarActivity;
import com.example.myapplication.NavbarActivities.DnMainNavbarActivity;
import com.example.myapplication.NavbarActivities.YouthMainNavbarActivity;
import com.example.myapplication.NavbarActivities.MerMainNavbarActivity;
import com.example.myapplication.NavbarActivities.OrgMainNavbarActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;
import com.google.android.material.textfield.TextInputEditText;

public class DonationActivity extends AppCompatActivity {
    public void createDonation(String username, String password, String receiver, long amount, String comment){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        TextView receiver=(TextView) findViewById(R.id.dnt_reciever);
        String receiverUsername = getIntent().getStringExtra("receiverUsername");
        String receiverName = getIntent().getStringExtra("receiverName");
        receiver.setText(receiverName);
        TextInputEditText amountField=(TextInputEditText) findViewById(R.id.dnt_amount);


        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button confirm=(Button) findViewById(R.id.dnt_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //read input
                String amountStirng=amountField.getText().toString();
               String comment  = "";
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
                transaction.makeDnDonationTransaction(receiverUsername, comment, amount,
                        DonationActivity.this, new VolleyCallBack() {
                            @Override
                            public void onSuccess() {

                                //make toast after success
                                Toast toast = Toast.makeText(getApplicationContext(), "Transaction successful", Toast.LENGTH_LONG);
                                toast.show();
                                switch (ProfileInfo.getUserRole()) {
                                    case "ROLE_HOMELESS": startActivity(new Intent(getApplicationContext(), YouthMainNavbarActivity.class)); break;
                                    case "ROLE_DONOR": startActivity(new Intent(getApplicationContext(), DnMainNavbarActivity.class)); break;
                                    case "ROLE_ORGANIZATION": startActivity(new Intent(getApplicationContext(), OrgMainNavbarActivity.class)); break;
                                    case "ROLE_BEING_SEEN": startActivity(new Intent(getApplicationContext(), BsMainNavbarActivity.class)); break;
                                    case "ROLE_MERCHANT": startActivity(new Intent(getApplicationContext(), MerMainNavbarActivity.class)); break;
                                }
                            }
                        });
            }
        });
    }

    //Back button on top
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