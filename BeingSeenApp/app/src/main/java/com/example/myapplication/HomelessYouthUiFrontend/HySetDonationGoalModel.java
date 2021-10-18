package com.example.myapplication.HomelessYouthUiFrontend;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

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

public class HySetDonationGoalModel {
    HySetDonationGoalActivity activity;
    ProfileInfo profileInfo;
    String LOGIN_TAG = HyLoginActivity.LOGIN_TAG;
    private Object HyUserInterfaceActivity;

    public HySetDonationGoalModel(HySetDonationGoalActivity activity, ProfileInfo profileInfo) {
        this.activity = activity;
        this.profileInfo = profileInfo;
    }

    // Post to donationGoal
    void createDonationGoal(String title, String description, Long goal) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url_create = "http://10.0.2.2:8080/donationGoal";

        JSONObject body = new JSONObject();
        try {
            body.put("title", title);
            body.put("description", description);
            body.put("goal", goal);
            body.put("current", 0L);
        } catch (JSONException e) {
            Log.i(LOGIN_TAG, "error: failed to create json obj for donation goal");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_create,
                body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(LOGIN_TAG, "create donation goal success");
                Intent i = new Intent(activity.getApplicationContext(), HyUserInterfaceActivity.class);
                i.putExtra("profileInfo", profileInfo);
                activity.startActivity(i);
                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i(LOGIN_TAG, "create donation goal failed");
                if (e.networkResponse.statusCode == 404) {
                    Log.i(LOGIN_TAG, "create donation goal failed error 404");
//                    CharSequence text = "You already have a donation goal";
//                    int duration = Toast.LENGTH_SHORT;
//
//                    Toast toast = Toast.makeText((Context) HyUserInterfaceActivity, text, duration);
//                    toast.show();
                } else {
                    Log.i(LOGIN_TAG, "create donation goal failed error 400, developer you need to do somethinggg");
                }
                Intent i = new Intent(activity.getApplicationContext(), HyUserInterfaceActivity.class);
                i.putExtra("profileInfo", profileInfo);
                activity.startActivity(i);
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