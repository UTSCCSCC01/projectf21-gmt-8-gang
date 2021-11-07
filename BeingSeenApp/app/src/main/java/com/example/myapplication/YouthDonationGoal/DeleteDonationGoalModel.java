package com.example.myapplication.YouthDonationGoal;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.SignUpAndLogin.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class DeleteDonationGoalModel {
    AppCompatActivity activity;
    String LOGIN_TAG = LoginActivity.LOGIN_TAG;

    public DeleteDonationGoalModel(AppCompatActivity activity) {
        this.activity = activity;
    }

    void deleteDonationGoal() {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url_delete = "http://10.0.2.2:8080/donationGoal";

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url_delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(LOGIN_TAG, "delete donation goal success");
                Toast.makeText(activity.getApplicationContext(), "successfully deleted donation goal", Toast.LENGTH_LONG).show();
                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 404) {
                    Log.i(LOGIN_TAG, "delete donation goal failed");
                    Toast.makeText(activity.getApplicationContext(), "You don't have a donation goal!", Toast.LENGTH_LONG).show();
                } else {
                    Log.i(LOGIN_TAG, "delete donation goal failed");
                    Toast.makeText(activity.getApplicationContext(), "network issues, can't delete", Toast.LENGTH_LONG).show();
                }

                return;
            }
        }) {
            //additional headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", ProfileInfo.getToken());
                return headers;
            }
        };

        queue.add(stringRequest);
    }
}
