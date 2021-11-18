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
import com.example.myapplication.NavbarActivities.MerMainNavbarActivity;
import com.example.myapplication.NavbarActivities.YouthMainNavbarActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.SignUpAndLogin.LoginActivity;
import com.example.myapplication.SignUpAndLogin.SignUpActivity;
import com.example.myapplication.YouthDonationGoal.VolleyResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CashConversionRequestModel {
    CashConversionRequestActivity activity;
    public static final String CONVERT_TAG = "cashConvert";

    public CashConversionRequestModel(CashConversionRequestActivity activity) {
        this.activity = activity;
    }

    public void cashConvert(String email, Long amount) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url_create = "http://10.0.2.2:8080/conversion-request";
        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
            body.put("amount", amount);
        } catch (JSONException e) {
            Log.i(CONVERT_TAG, "error: failed to create json obj for request model");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_create,
                body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                    Intent i = new Intent(activity.getApplicationContext(), MerMainNavbarActivity.class);
//                    Toast.makeText(activity.getApplicationContext(), "Request sent", Toast.LENGTH_LONG).show();
//                    activity.startActivity(i);
                    return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i(CONVERT_TAG, "cash conversion request Failed" + e);

////              This has to be handled better in the future because right now the default response leads to error
//                Intent i = new Intent(activity.getApplicationContext(), MerMainNavbarActivity.class);
//                Toast.makeText(activity.getApplicationContext(), "Request sent", Toast.LENGTH_LONG).show();
//                activity.startActivity(i);
                return;
            }
        }) {
            //additional headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", ProfileInfo.getToken());
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    //decrease balance of merchant
    public void updateBalance(Long amount) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url_balance = "http://10.0.2.2:8080/balance";
        JSONObject body = new JSONObject();
        try {
            body.put("balance", Long.parseLong(ProfileInfo.getBalance()) - amount);
        } catch (JSONException e) {
            Log.i(CONVERT_TAG, "error: failed to create json obj for request model");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url_balance,
                body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent i = new Intent(activity.getApplicationContext(), MerMainNavbarActivity.class);
                Toast.makeText(activity.getApplicationContext(), "Request sent", Toast.LENGTH_LONG).show();
                activity.startActivity(i);
                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i(CONVERT_TAG, "cash conversion request Failed" + e);

//              This has to be handled better in the future because right now the default response leads to error
                Intent i = new Intent(activity.getApplicationContext(), MerMainNavbarActivity.class);
                Toast.makeText(activity.getApplicationContext(), "Request sent", Toast.LENGTH_LONG).show();
                activity.startActivity(i);
                return;
            }
        }) {
            //additional headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", ProfileInfo.getToken());
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}
