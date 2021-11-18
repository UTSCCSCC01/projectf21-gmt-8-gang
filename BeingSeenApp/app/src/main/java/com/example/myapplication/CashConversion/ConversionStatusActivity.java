package com.example.myapplication.CashConversion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AboutUsFrontend.DonationStatusActivity;
import com.example.myapplication.R;
import com.example.myapplication.SignUpAndLogin.SignUpActivity;

public class ConversionStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_conversion_status);
        TextView transactionStatus=(TextView) findViewById(R.id.cash_conversion_status);
        TextView transactionInfo=(TextView) findViewById(R.id.conversion_info);
        Button backHome=(Button) findViewById(R.id.conversion_backHome);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(ConversionStatusActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}
