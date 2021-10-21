package com.example.myapplication.AboutUsFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class Aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        TextView abs_title=(TextView)findViewById(R.id.aboutus_title);
        TextView abs_txt=(TextView)findViewById(R.id.aboutus_txt);
        abs_txt.setText("The project aims to create an app that helps homeless youth by" +
                " removing certains stigmas of donating to the homeless, such as the stigma " +
                "that homeless youths might use the money to purchase alcohol rather than paying " +
                "off their loans/mortgages.");
        Button abs_donate=(Button) findViewById(R.id.donateus_btn);
        abs_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(Aboutus.this, Donation.class);
                //info need db complete
                intent.putExtra("receiver","Being Seen");
                startActivity(intent);
            }
        });


    }
}