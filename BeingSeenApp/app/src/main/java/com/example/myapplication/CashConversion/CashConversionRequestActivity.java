package com.example.myapplication.CashConversion;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.SignUpAndLogin.LoginModel;
import com.example.myapplication.YouthDonationGoal.VolleyResponse;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class CashConversionRequestActivity extends AppCompatActivity {

    CashConversionRequestModel model;
    public static final String CONVERT_TAG = "cashConvert";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_conversion);
        model = new CashConversionRequestModel(this);

        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button confirm=(Button) findViewById(R.id.convertConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                cashConvert(view);
            }
        });
        Log.i(CONVERT_TAG, "cash conversion activity started");

    }

    public void cashConvert(View view){

        TextInputEditText amountField = (TextInputEditText) findViewById(R.id.amount_convert);
        String amountString = amountField.getText().toString();
        TextInputEditText emailField = (TextInputEditText) findViewById(R.id.email_convert);
        String emailString = emailField.getText().toString();
        long amount = Long.parseLong(amountString);

        if (amountString.isEmpty()) {
            amountField.setError("please enter an amount");
            amountField.requestFocus();
            return;
        }

        if (emailString.isEmpty()) {
            emailField.setError("please enter an amount");
            emailField.requestFocus();
            return;
        }

        if(amount == 0){
            amountField.setError("amount cannot be 0");
            amountField.requestFocus();
            return;
        }
        model.cashConvert(emailString, amount);
        model.updateBalance(amount);

        return;
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
