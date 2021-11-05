package com.example.myapplication.HomelessYouthUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DonorUiFrontend.DnUserDonoActivity;
import com.example.myapplication.DonorUiFrontend.DnUserProfileViewBalanceActivity;
import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;

public class HySpendMoney extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_spend_money);
        //donate to someone button
        final Button donateButton = (Button) findViewById(R.id.donateConfirm);

        donateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendDono(view);
            }
        });


        //quit donate screen
        final Button exitDonate = (Button) findViewById(R.id.DnDonateBackEditButton);

        exitDonate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HyUserProfileViewBalanceActivity.class);
                startActivity(i);
            }
        });
    }


    public void sendDono(View view) {
        // for future pass in username of receiver (e.g. testuser1)
        // then set as fixed textbox same retreival but is fixed
        EditText sendingTo = (EditText)findViewById(R.id.donateUsername);

        // After clicking save all objects on screen into a object
        EditText amount = (EditText)findViewById(R.id.dnDonoAmt);

        Transaction transactionObj = new Transaction();


        String receiver = "";
        receiver = sendingTo.getText().toString();
        String comment = "";
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
            amount.setError("Please enter a nonzero amount to transfer");
            amount.requestFocus();
            return;
        }

        if (  !( (amt == null || amt <= 0) || (receiver.isEmpty()) )  ) {
            //call transaction in db
            transactionObj.makeDnDonationTransaction(receiver, comment, amt, HySpendMoney.this,
                    new VolleyCallBack() {
                        @Override
                        public void onSuccess() {

                            //make toast after success
                            Toast toast = Toast.makeText(getApplicationContext(), "Transaction successful", Toast.LENGTH_LONG);
                            toast.show();

                            Intent i = new Intent(getApplicationContext(), HyUserProfileViewBalanceActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure() {

                            //make toast after fail
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Transaction Failed: Make sure the user has a merchant account", Toast.LENGTH_LONG);
                            toast.show();

                            Intent i = new Intent(getApplicationContext(), HyUserProfileViewBalanceActivity.class);
                            startActivity(i);
                        }
                    });
        }

        return;
    }
}