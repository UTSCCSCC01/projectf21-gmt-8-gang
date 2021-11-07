package com.example.myapplication.SearchForMerchant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class TransferToMerchantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_merchant);
        //donate to someone button
        final Button donateButton = (Button) findViewById(R.id.donateConfirm);
        Intent intent = getIntent();
        String receiverName = intent.getStringExtra("receiver");
        TextView receiver=(TextView) findViewById(R.id.merchant_receiver);
        receiver.setText(receiverName);
        donateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendDono(view, receiverName);
            }
        });


        //quit donate screen
        final Button exitDonate = (Button) findViewById(R.id.DnDonateBackEditButton);

        exitDonate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), YouthMainNavbarActivity.class);
                startActivity(i);
            }
        });
    }


    public void sendDono(View view, String rec) {
        // for future pass in username of receiver (e.g. testuser1)
        // then set as fixed textbox same retreival but is fixed

        // After clicking save all objects on screen into a object
        EditText amount = (EditText)findViewById(R.id.dnDonoAmt);

        Transaction transactionObj = new Transaction();


        String receiver = rec;
        String comment = "";
        Long amt = 0L;
        if(!amount.getText().toString().equals("")) {
            amt = Long.parseLong(amount.getText().toString());
        }


        if (amt == null || amt <= 0) {
            amount.setError("Please enter a nonzero amount to transfer");
            amount.requestFocus();
            return;
        }

        if (  !( (amt == null || amt <= 0) || (receiver.isEmpty()) )  ) {
            //call transaction in db
            transactionObj.makeDnDonationTransaction(receiver, comment, amt, TransferToMerchantActivity.this,
                    new VolleyCallBack() {
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

                        @Override
                        public void onFailure() {

                            //make toast after fail
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Transaction Failed: Make sure the user has a merchant account", Toast.LENGTH_LONG);
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

        return;
    }
}