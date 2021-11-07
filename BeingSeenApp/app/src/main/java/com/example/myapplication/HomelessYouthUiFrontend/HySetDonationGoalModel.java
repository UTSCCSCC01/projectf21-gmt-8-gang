package com.example.myapplication.HomelessYouthUiFrontend;

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
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.SignUpAndLogin.HyLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HySetDonationGoalModel {
    HySetDonationGoalActivity activity;
    String LOGIN_TAG = HyLoginActivity.LOGIN_TAG;
    final static String CREATE_GOAL_ERROR = "CREATE_GOAL_ERROR";
    final static String CREATE_GOAL_DUPLICATE = "CREATE_GOAL_DUPLICATE";
    final static String CREATE_GOAL_SUCCESS = "CREATE_GOAL_SUCCESS";
    private Object HyUserInterfaceActivity;

    public HySetDonationGoalModel(HySetDonationGoalActivity activity) {
        this.activity = activity;
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
                Intent i = new Intent(activity.getApplicationContext(), HyMainNavbarActivity.class);
                Toast.makeText(activity.getApplicationContext(), "Donation goal created!", Toast.LENGTH_LONG).show();
//                i.putExtra("toast", CREATE_GOAL_SUCCESS);
                activity.startActivity(i);
                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i(LOGIN_TAG, "create donation goal failed");
                if (e.networkResponse.statusCode == 404) {
                    Log.i(LOGIN_TAG, "create donation goal failed error 404");
                    Intent i = new Intent(activity.getApplicationContext(), HyMainNavbarActivity.class);
                    i.putExtra("toast", CREATE_GOAL_DUPLICATE);
                    activity.startActivity(i);
                } else {
                    Log.i(LOGIN_TAG, "create donation goal failed error 400, developer you need to do somethinggg");
                    Intent i = new Intent(activity.getApplicationContext(), HyMainNavbarActivity.class);
                    i.putExtra("toast", CREATE_GOAL_ERROR);
                    activity.startActivity(i);
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

        queue.add(jsonObjectRequest);
    }
}