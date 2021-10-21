package com.example.myapplication.DonorUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.myapplication.DonorUiFrontend.DnUserProfileViewBalanceActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;

public class DnUserDonoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_user_dono);

        //donate to someone button
        final Button donateButton = (Button) findViewById(R.id.donateConfirm);

        donateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // After clicking save all objects on screen into a object
                EditText sendingTo = (EditText)findViewById(R.id.donateUsername);
                EditText donoDesc = (EditText)findViewById(R.id.DnDonoText);
                EditText amount = (EditText)findViewById(R.id.dnDonoAmt);

                Transaction transactionObj = new Transaction();

                String receiver = sendingTo.getText().toString();
                String comment = donoDesc.getText().toString();
                Long amt = Long.parseLong(amount.getText().toString());

                transactionObj.makeDnDonationTransaction(receiver,comment,amt,DnUserDonoActivity.this,
                        new VolleyCallBack() {
                            @Override
                            public void onSuccess() {

                                //make toast after success
                                Toast toast = Toast.makeText(getApplicationContext(), "Donation successful", Toast.LENGTH_LONG);
                                toast.show();

                                Intent i = new Intent(getApplicationContext(), DnUserProfileViewBalanceActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure() {

                                //make toast after fail
                                Toast toast = Toast.makeText(getApplicationContext(), "Donation Failed", Toast.LENGTH_LONG);
                                toast.show();

                                Intent i = new Intent(getApplicationContext(), DnUserProfileViewBalanceActivity.class);
                                startActivity(i);
                            }
                        });


//                //make toast after fail
//                Toast toast = Toast.makeText(getApplicationContext(), "Donation Failed", Toast.LENGTH_LONG);
//                toast.show();
//
//                Intent i = new Intent(getApplicationContext(), DnUserProfileViewBalanceActivity.class);
//                startActivity(i);
            }
        });


        //quit donate screen
        final Button exitDonate = (Button) findViewById(R.id.DnDonateBackEditButton);

        exitDonate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), DnUserProfileViewBalanceActivity.class);
                startActivity(i);
            }
        });
    }
}