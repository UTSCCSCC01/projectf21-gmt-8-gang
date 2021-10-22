package com.example.myapplication.AboutUsFrontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.HomelessYouthUiFrontend.HyLoginActivity;
import com.example.myapplication.HomelessYouthUiFrontend.HyLoginModel;
import com.example.myapplication.HomelessYouthUiFrontend.HyUserInterfaceActivity;
import com.example.myapplication.HomelessYouthUiFrontend.VolleyResponse;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DonorVerify implements  VolleyResponse {
    public Donation donation;
    public String jwt="";
    public String role;
    public boolean valid=false;
    public static final String LOGIN_TAG = "donorVerify";
    public DonorVerify(Donation donation) {
        this.donation= donation;
        Log.i(LOGIN_TAG, "donor verify started");
    }

    public void logIn(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(donation);
        String url_login = "http://10.0.2.2:8080/auth";

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("username", username);
            loginData.put("password", password);
        } catch (JSONException e) {
            Log.i(LOGIN_TAG, "error: failed to create json obj for donor verify");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_login,
                loginData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //if (volleyResponse != null) {
                onVolleySuccess(response);
                Log.i(LOGIN_TAG, "donor verify request succeed yay, response: " + response);
                return;
                /*} else {
                    Toast.makeText(hyLoginActivity.getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.i(LOGIN_TAG, "verify donor request failed qwq, error: " + e.getMessage());
            }
        }) {
            //additional headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer");
                return headers;
            }

//            @Override
//            public Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                try {
//                    params.put("username", username);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    params.put("password", password);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return params;
//            }
        };

        queue.add(jsonObjectRequest);
    }
    @Override
    public void onVolleySuccess(JSONObject response) {
        this.valid=true;
        try {
            this.jwt = response.getString("response");
            Log.i(LOGIN_TAG, "jwt token: " +jwt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.role = response.getString("code");
            Log.i(LOGIN_TAG, "role: " + role);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}