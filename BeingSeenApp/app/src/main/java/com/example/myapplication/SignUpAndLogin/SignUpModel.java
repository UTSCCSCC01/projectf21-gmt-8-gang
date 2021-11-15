package com.example.myapplication.SignUpAndLogin;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.LottieAnimations.NotFoundAnimation;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpModel {
    SignUpActivity signUpActivity;
    public static final String REGISTER_TAG = "hyRegister";

    public SignUpModel(SignUpActivity signUpActivity) {
        this.signUpActivity = signUpActivity;
        Log.i(REGISTER_TAG, "hy sign up model started");
    }

    public void signUp(String username, String password, String role) {
        // create http request to backend to sign up
        RequestQueue queue = Volley.newRequestQueue(signUpActivity);
        String url_register = "http://10.0.2.2:8080/register";

        JSONObject registerData = new JSONObject();
        try {
            registerData.put("username", username);
            registerData.put("password", password);
            registerData.put("role", role);
        } catch (JSONException e) {
            Log.i(REGISTER_TAG, "error: failed to create json obj for hy sign up");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_register,
                registerData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i(REGISTER_TAG, "hy sign up request succeed yay, response: " + response);

                    Intent i = new Intent(signUpActivity.getApplicationContext(), LoginActivity.class);
                    i.putExtra("Toast", "Sign up completed");
                    signUpActivity.startActivity(i);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                if (e == null || e.networkResponse == null) {
                    signUpActivity.startActivity(new Intent(signUpActivity.getApplicationContext(), NotFoundAnimation.class)
                            .putExtra("message", "can't connect to server")
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                } else if (e.networkResponse.statusCode == 404) {
                        EditText usernameField = (EditText) signUpActivity.findViewById(R.id.HyUsername);
                        usernameField.setError("someone already used this username");
                        usernameField.requestFocus();

                } else {
                    Log.i(REGISTER_TAG, "hy sign up request failed qwq, error: " + e.getMessage());
                }
            }
        }
        );

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
