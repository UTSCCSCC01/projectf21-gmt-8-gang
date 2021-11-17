package com.example.myapplication.CashConversion;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.SignUpAndLogin.LoginActivity;
import com.example.myapplication.SignUpAndLogin.SignUpActivity;
import com.example.myapplication.YouthDonationGoal.VolleyResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CashConversionRequestModel {
    CashConversionRequestActivity activity;
    VolleyResponse volleyResponse;
    public static final String CONVERT_TAG = "cashConvert";

    public CashConversionRequestModel(CashConversionRequestActivity cashConversionRequestActivity, VolleyResponse response) {
        this.activity = cashConversionRequestActivity;
        this.volleyResponse = response;
        Log.i(CONVERT_TAG, "cash conversion activity started");
    }

    public void cashConvert(String email, Long amount) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url_login = "http://10.0.2.2:8080/conversion-request";

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("email", email);
            requestData.put("amount", amount);
        } catch (JSONException e) {
            Log.i(CONVERT_TAG, "error: failed to create json obj for hy login");
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_login,
                requestData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i(CONVERT_TAG, "cash conversion request succeed, response: " + response);
                    Intent i = new Intent(activity.getApplicationContext(), CashConversionRequestActivity.class);
                    activity.startActivity(i);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                if (e.networkResponse.statusCode == 400) {

                }
            }
        }) {
            //additional headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}
