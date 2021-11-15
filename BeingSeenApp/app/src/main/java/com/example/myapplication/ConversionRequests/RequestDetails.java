package com.example.myapplication.ConversionRequests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.NavbarActivities.BsMainNavbarActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

import java.util.HashMap;
import java.util.Map;

public class RequestDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String receiver=getIntent().getStringExtra("receiver");
        String requestId=getIntent().getStringExtra("requestId");
        String amount=getIntent().getStringExtra("amount");
        String status=getIntent().getStringExtra("status");
        String email=getIntent().getStringExtra("email");
        TextView receiverField=findViewById(R.id.rq_detail_receiver_field);
        TextView emailField=findViewById(R.id.rq_detail_email_field);
        TextView amountField=findViewById(R.id.rq_detail_amount_field);
        TextView statusField=findViewById(R.id.rq_detail_status_field);
        receiverField.setText(receiver);
        amountField.setText(amount);
        if (Boolean.valueOf(status)) {
            statusField.setText("Done");
        } else {
            statusField.setText("Pending");
        }
        emailField.setText(email);
        Button changeStatus=findViewById(R.id.rq_detail_change_status);

        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: call db to change status
                //if success go to homepage, if fail pop a message
                getRequestsFromDbAndSetAdapter(requestId, RequestDetails.this,
                        new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                //make toast after success
                                Toast toast = Toast.makeText(getApplicationContext(), "Updated status of request", Toast.LENGTH_LONG);
                                toast.show();
                                startActivity(new Intent(
                                        getApplicationContext(), BsMainNavbarActivity.class
                                ));
                            }

                            @Override
                            public void onFailure() {
                                //make toast after fail
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Update failed", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
            }
        });
    }

    //Back button on top
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("hyyy", ProfileInfo.getUserRole());
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getRequestsFromDbAndSetAdapter(String requestId, AppCompatActivity callingActivity, final VolleyCallBack callBack){
        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // API to get all conversion requests made
        String url = "http://10.0.2.2:8080/conversion-request";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                response -> {
                    callBack.onSuccess();
                }, error -> {
            callBack.onFailure();
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("GET_HEADER", "Made call to getHeaders");
                Map<String, String>  params = new HashMap<String, String>();
                String token = ProfileInfo.getToken();
                params.put("Authorization", token);
                params.put("Content-Type", "application/json");
                return params;
            }

        @Override
        public byte[] getBody() throws AuthFailureError
        {
            Log.d("TN_GET_PARAMS", "Transaction calls getParams");
            Map<String, Object>  params = new HashMap<String, Object>();
            params.put("requestId", requestId);
            return params.toString().getBytes();
        }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}