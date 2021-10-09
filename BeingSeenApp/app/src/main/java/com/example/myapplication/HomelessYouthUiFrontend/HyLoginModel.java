package com.example.myapplication.HomelessYouthUiFrontend;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HyLoginModel {

    HyLoginActivity hyLoginActivity;
    public static final String LOGIN_TAG = "hyLogin";

    public HyLoginModel(HyLoginActivity hyLoginActivity) {
        this.hyLoginActivity = hyLoginActivity;
        Log.i(LOGIN_TAG, "hy login model started");
    }

    public void logIn(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(hyLoginActivity);
        String url_login = "http://10.0.2.2:8080/login";

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("username", username);
            loginData.put("password", password);
        } catch (JSONException e) {
            Log.i(LOGIN_TAG, "error: failed to create json obj for hy login");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_login,
                loginData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(LOGIN_TAG, "hy login request succeed yay, response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i(LOGIN_TAG, "hy login request failed qwq, error: " + e.getMessage());
            }
        }
        );

        queue.add(jsonObjectRequest);
    }
}
