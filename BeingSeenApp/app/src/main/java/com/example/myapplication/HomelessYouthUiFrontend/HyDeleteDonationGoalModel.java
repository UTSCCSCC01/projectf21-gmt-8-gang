package com.example.myapplication.HomelessYouthUiFrontend;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.ProfileInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HyDeleteDonationGoalModel {
    AppCompatActivity activity;
    String LOGIN_TAG = HyLoginActivity.LOGIN_TAG;
    ProfileInfo profileInfo;

    public HyDeleteDonationGoalModel(AppCompatActivity activity /*, ProfileInfo profileInfo*/) {
        this.activity = activity;
//        this.profileInfo = profileInfo;
        profileInfo = new ProfileInfo();
    }

    void deleteDonationGoal() {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url_delete = "http://10.0.2.2:8080/donationGoal";

        JSONObject body = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url_delete,
                body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(LOGIN_TAG, "delete donation goal success");
                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i(LOGIN_TAG, "delete donation goal failed");
            }
        }) {
            //additional headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", profileInfo.getToken());
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }
}
