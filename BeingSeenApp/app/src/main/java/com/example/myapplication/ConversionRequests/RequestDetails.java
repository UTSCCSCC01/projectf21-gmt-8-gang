package com.example.myapplication.ConversionRequests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.NavbarActivities.OrgMainNavbarActivity;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

public class RequestDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        String receiver=getIntent().getStringExtra("receiver");
        String time=getIntent().getStringExtra("time");
        String amount=getIntent().getStringExtra("amount");
        String status=getIntent().getStringExtra("status");
        TextView receiverField=findViewById(R.id.rq_detail_receiver_field);
        TextView timeField=findViewById(R.id.rq_detail_time_field);
        TextView amountField=findViewById(R.id.rq_detail_amount_field);
        TextView statusField=findViewById(R.id.rq_detail_status_field);
        Button back=findViewById(R.id.rq_detail_back);
        Button changeStatus=findViewById(R.id.rq_detail_change_status);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setClass(RequestDetails.this, OrgMainNavbarActivity.class);
                startActivity(i);
            }
        });
        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: call db to change status
                //if success go to homepage, if fail pop a message
            }
        });
    }
}