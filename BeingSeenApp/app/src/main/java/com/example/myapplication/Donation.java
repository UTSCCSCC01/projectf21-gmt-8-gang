package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Donation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation);
        //getting reciever's name should be added after db transaction finish
        TextView reciever=(TextView) findViewById(R.id.dnt_reciever);
        TextInputEditText amount=(TextInputEditText) findViewById(R.id.dnt_amount);
        Button confirm=(Button) findViewById(R.id.dnt_confirm);

    }
}