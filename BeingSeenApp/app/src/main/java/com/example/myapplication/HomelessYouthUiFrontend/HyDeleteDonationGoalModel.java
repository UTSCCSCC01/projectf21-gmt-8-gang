package com.example.myapplication.HomelessYouthUiFrontend;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.ProfileInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HyDeleteDonationGoalModel {
    AppCompatActivity activity;
    String LOGIN_TAG = HyLoginActivity.LOGIN_TAG;

    public HyDeleteDonationGoalModel(AppCompatActivity activity) {
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
                Log.i(LOGIN_TAG, "delete donation goal failed");
                Toast.makeText(activity.getApplicationContext(), "failed to delete donation goal", Toast.LENGTH_LONG).show();
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
