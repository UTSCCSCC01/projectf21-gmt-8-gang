package com.example.myapplication.donorsignup;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DonorSignUpModel {

    DonorSignUpActivity donorSignUpActivity;
    public static final String REGISTER_TAG = "donorRegister";

    public DonorSignUpModel(DonorSignUpActivity donorSignUpActivity) {
        this.donorSignUpActivity = donorSignUpActivity;
        Log.i(REGISTER_TAG, "donor sign up model started");
    }

    public void signUp(String username, String password) {
        // create http request to backend to sign up
        RequestQueue queue = Volley.newRequestQueue(donorSignUpActivity);
        String url_register = "http://10.0.2.2:8080/register";

        JSONObject registerData = new JSONObject();
        try {
            registerData.put("username", username);
            registerData.put("password", password);
        } catch (JSONException e) {
            Log.i(REGISTER_TAG, "error: failed to create json obj for donor sign up");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_register,
                registerData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(REGISTER_TAG, "donor sign up request succeed yay, response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i(REGISTER_TAG, "donor sign up request failed qwq, error: " + e.getMessage());
                }
            }
        );

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }
}
