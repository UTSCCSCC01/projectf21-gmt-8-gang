package com.example.myapplication.SignUpAndLogin;

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
import com.example.myapplication.NavbarActivities.BsMainNavbarActivity;
import com.example.myapplication.NavbarActivities.DnMainNavbarActivity;
import com.example.myapplication.NavbarActivities.YouthMainNavbarActivity;
import com.example.myapplication.R;
import com.example.myapplication.YouthDonationGoal.VolleyResponse;
import com.example.myapplication.NavbarActivities.MerMainNavbarActivity;
import com.example.myapplication.NavbarActivities.OrgMainNavbarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginModel {

    LoginActivity loginActivity;
    VolleyResponse volleyResponse;
    public static final String LOGIN_TAG = "hyLogin";
    public static final String[] ROLES = SignUpActivity.ROLES;

    public LoginModel(LoginActivity loginActivity, VolleyResponse volleyResponse) {
        this.loginActivity = loginActivity;
        this.volleyResponse = volleyResponse;
        Log.i(LOGIN_TAG, "hy login model started");
    }

    public void logIn(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(loginActivity);
        String url_login = "http://10.0.2.2:8080/auth";

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
                //if (volleyResponse != null) {
                    volleyResponse.onVolleySuccess(response);
                    Log.i(LOGIN_TAG, "hy login request succeed yay, response: " + response);

                    Intent i;

                    try{
                        String urole = response.getString("code");
                        Log.i("UROLE CODE", urole);
                        if (urole.equals("ROLE_" + ROLES[0])){
                            i = new Intent(loginActivity.getApplicationContext(), YouthMainNavbarActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        } else if(urole.equals("ROLE_" + ROLES[1])){
                            i = new Intent(loginActivity.getApplicationContext(), DnMainNavbarActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        } else if(urole.equals("ROLE_" + ROLES[2])){
                            i = new Intent(loginActivity.getApplicationContext(), OrgMainNavbarActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        } else if(urole.equals("ROLE_" + ROLES[3])){
                            i = new Intent(loginActivity.getApplicationContext(), MerMainNavbarActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        } else if(urole.equals("ROLE_" + ROLES[4])){
                            i = new Intent(loginActivity.getApplicationContext(), BsMainNavbarActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        }
                        else{
                            throw new Exception();
                        }
                    }catch(Exception e){
                        Log.i("UROLE FAIL", "Urole get failed");
                        Toast.makeText(loginActivity, "Failed to connect", Toast.LENGTH_SHORT).show();
                        i = new Intent(loginActivity.getApplicationContext(), LoginActivity.class);
                    }

                    loginActivity.startActivity(i);
                    loginActivity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(loginActivity.getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
                Log.i(LOGIN_TAG, "hy login request failed qwq, error: " + e.getMessage());
            }
        }) {
            //additional headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer");
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }
}
