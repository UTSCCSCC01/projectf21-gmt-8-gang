package com.example.myapplication.AboutUsFrontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DonationActivity extends AppCompatActivity {
    public void createDonation(String username, String password, String receiver, long amount, String comment){

    }
    Boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        LottieAnimationView lottieAnimationView = findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.setVisibility(View.GONE);
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

                showLoadingScreen();

                // process transaction
                Transaction transaction=new Transaction();
                transaction.makeDnDonationTransaction(receiverUsername, comment, amount,
                        DonationActivity.this, new VolleyCallBack() {
                            @Override
                            public void onSuccess() {

                                //make toast after success
                                Toast toast = Toast.makeText(getApplicationContext(), "Transaction successful", Toast.LENGTH_LONG);
                                toast.show();
                                startActivity(new Intent(getApplicationContext(), PaymentOkAnimation.class));

                            }
                        });
            }
        });
    }

    private void showLoadingScreen() {
        isLoading = true;
        getSupportActionBar().hide();
        findViewById(R.id.relativeLayoutOuter).setVisibility(View.GONE);
        findViewById(R.id.payment_layout).setVisibility(View.GONE);
        findViewById(R.id.dnt_reciever).setVisibility(View.GONE);
        findViewById(R.id.dnt_amount_textfield).setVisibility(View.GONE);
        findViewById(R.id.dnt_amount).setVisibility(View.GONE);
        findViewById(R.id.dnt_confirm).setVisibility(View.GONE);
        LottieAnimationView lottieAnimationView = findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
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
    @Override public void finish() {
        if (isLoading) return;
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}