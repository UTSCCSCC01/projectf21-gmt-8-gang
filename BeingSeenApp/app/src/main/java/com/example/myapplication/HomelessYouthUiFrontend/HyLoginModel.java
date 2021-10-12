package com.example.myapplication.HomelessYouthUiFrontend;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HyLoginModel {

    HyLoginActivity hyLoginActivity;
    VolleyResponse volleyResponse;
    public static final String LOGIN_TAG = "hyLogin";

    public HyLoginModel(HyLoginActivity hyLoginActivity, VolleyResponse volleyResponse) {
        this.hyLoginActivity = hyLoginActivity;
        this.volleyResponse = volleyResponse;
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
                if (volleyResponse != null) {
                    volleyResponse.onVolleySuccess(response);
                }
                Log.i(LOGIN_TAG, "hy login request succeed yay, response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i(LOGIN_TAG, "hy login request failed qwq, error: " + e.getMessage());
            }
        }) {
            //additional headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authentication", "Bearer");
                return headers;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("username", loginData.getString("username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    params.put("password", loginData.getString("password"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }
}
