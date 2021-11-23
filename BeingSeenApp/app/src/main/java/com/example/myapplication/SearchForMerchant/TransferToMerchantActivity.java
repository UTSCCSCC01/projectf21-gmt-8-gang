package com.example.myapplication.SearchForMerchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.LottieAnimations.PaymentOkAnimation;
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
        String receiverUsername = intent.getStringExtra("receiverUsername");
        String receiverName = intent.getStringExtra("receiverName");
        TextView receiver=(TextView) findViewById(R.id.merchant_receiver);
        receiver.setText(receiverName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        donateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendDono(view, receiverUsername);
            }
        });

        LottieAnimationView lottieAnimationView = findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.setVisibility(View.GONE);
        lottieAnimationView.pauseAnimation();

    }

    Boolean isLoading = false;
    private void showLoadingScreen() {
        isLoading = true;
        getSupportActionBar().hide();
        findViewById(R.id.relativeLayoutOuter).setVisibility(View.GONE);
        findViewById(R.id.relativeLayoutInner).setVisibility(View.GONE);
        findViewById(R.id.merchant_receiver).setVisibility(View.GONE);
        findViewById(R.id.dnDonoAmtFrame).setVisibility(View.GONE);
        findViewById(R.id.dnDonoAmt).setVisibility(View.GONE);
        findViewById(R.id.donateConfirm).setVisibility(View.GONE);
        LottieAnimationView lottieAnimationView = findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void finish() {
        if (isLoading) return;
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
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

        showLoadingScreen();

        if (  !( (amt == null || amt <= 0) || (receiver.isEmpty()) )  ) {
            //call transaction in db
            transactionObj.makeDnDonationTransaction(receiver, comment, amt, TransferToMerchantActivity.this,
                    new VolleyCallBack() {
                        @Override
                        public void onSuccess() {

                            //make toast after success
                            Toast toast = Toast.makeText(getApplicationContext(), "Transaction successful", Toast.LENGTH_LONG);
                            toast.show();

                            startActivity(new Intent(getApplicationContext(), PaymentOkAnimation.class));
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