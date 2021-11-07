package com.example.myapplication.DonorUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.BeingSeenUiFrontend.BsMainNavbarActivity;
import com.example.myapplication.HomelessYouthUiFrontend.HyMainNavbarActivity;
import com.example.myapplication.MerchantUiFrontend.MerMainNavbarActivity;
import com.example.myapplication.OrganizationUiFrontend.OrgMainNavbarActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;

public class DnUserDonoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_user_dono);


        //For future place code to set to donate to user image and name here
        // <CODE>



        //donate to someone button
        final Button donateButton = (Button) findViewById(R.id.donateConfirm);

        donateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendDono(view);
            }
        });


//        //quit donate screen
//        final Button exitDonate = (Button) findViewById(R.id.DnDonateBackEditButton);
//
//        exitDonate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getApplicationContext(), DnMainNavbarActivity.class);
//                startActivity(i);
//            }
//        });
    }


    public void sendDono(View view) {
        // for future pass in username of receiver (e.g. testuser1)
        // then set as fixed textbox same retreival but is fixed
        EditText sendingTo = (EditText)findViewById(R.id.donateUsername);

        // After clicking save all objects on screen into a object
        EditText donoDesc = (EditText)findViewById(R.id.DnDonoText);
        EditText amount = (EditText)findViewById(R.id.dnDonoAmt);

        Transaction transactionObj = new Transaction();


        String receiver = "";
        receiver = sendingTo.getText().toString();
        String comment = "";
        comment = donoDesc.getText().toString();
        Long amt = 0L;
        if(!amount.getText().toString().equals("")) {
            amt = Long.parseLong(amount.getText().toString());
        }

        if (receiver.equals("")) {
            Log.i("TRYNA", "HI");
            sendingTo.setError("Please enter a username");
            sendingTo.requestFocus();
            return;
        }

        if (amt == null || amt <= 0) {
            amount.setError("Please enter a nonzero amount to donate");
            amount.requestFocus();
            return;
        }

        if (  !( (amt == null || amt <= 0) || (receiver.isEmpty()) )  ) {
            transactionObj.makeDnDonationTransaction(receiver, comment, amt, DnUserDonoActivity.this,
                    new VolleyCallBack() {
                        @Override
                        public void onSuccess() {

                            //make toast after success
                            Toast toast = Toast.makeText(getApplicationContext(), "Donation successful", Toast.LENGTH_LONG);
                            toast.show();

                            switch (ProfileInfo.getUserRole()) {
                                case "ROLE_HOMELESS": startActivity(new Intent(getApplicationContext(), HyMainNavbarActivity.class)); break;
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
                                    "Donation Failed: Make sure the user has a homeless youth account", Toast.LENGTH_LONG);
                            toast.show();

                            switch (ProfileInfo.getUserRole()) {
                                case "ROLE_HOMELESS": startActivity(new Intent(getApplicationContext(), HyMainNavbarActivity.class)); break;
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