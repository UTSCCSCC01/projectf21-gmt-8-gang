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

public class CashConversionRequestActivity extends AppCompatActivity implements VolleyResponse {

    private CashConversionRequestModel model;
    private VolleyResponse response;
    public static final String CONVERT_TAG = "cashConvert";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_conversion);

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
        model = new CashConversionRequestModel(this, this);

    }

    public void cashConvert(View view){

        TextInputEditText amountField = (TextInputEditText) findViewById(R.id.amount_convert);
        String amountString = amountField.getText().toString();
        String merchantEmail = getIntent().getStringExtra("email");
        long amount = Long.parseLong(amountField.getText().toString());

        if (amountString.isEmpty()) {
            amountField.setError("please enter an amount");
            amountField.requestFocus();
            return;
        }

        if(amount == 0){
            amountField.setError("amount cannot be 0");
            amountField.requestFocus();
            return;
        }

        Log.i(CONVERT_TAG, "convert request success");
        CashConversionRequestModel.cashConvert(merchantEmail, amount);

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

    @Override
    public void onVolleySuccess(JSONObject response) {

    }
}
