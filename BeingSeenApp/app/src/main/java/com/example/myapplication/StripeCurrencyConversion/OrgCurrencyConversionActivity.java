package com.example.myapplication.StripeCurrencyConversion;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import android.app.ActionBar;
import android.os.Bundle;

public class OrgCurrencyConversionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_currency_conversion);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}