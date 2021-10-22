package com.example.myapplication.AboutUsFrontend;

import android.util.Log;
import android.view.SurfaceControl;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransactionInfo {
    static String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjgiLCJleHAiOjE2MzQwNTk2NDcsImlhdCI6MTYzNDAyMzY0N30.WRVtI8TIWtnRGMu0e0SmUu1sgEAeiNMlaDvi_xLxLbc";
    static String sender;
    static String receiver;
    static long amount;
    static String comment;

    public static String getToken() {
        return token;
    }

    public static void setToken(String tokenIn) {
        token = tokenIn;
    }

    public static String getSender() {
        return sender;
    }

    public void setSender(String username) {
        this.sender = username;
    }

    public static String getReceiver() {
        return receiver;
    }

    public void setReceiver(String username) {
        this.receiver = username;
    }

    public static long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public static String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TransactionInfo(String sender, String receiver, long amount, String comment){
        this.sender=sender;
        this.receiver=receiver;
        this.amount=amount;
        this.comment=comment;
    }
    public TransactionInfo(){

    }

    //send a Transation info to database, modified from sendInfoToDb from profile info
    public void sendInfoToDb(AppCompatActivity callingActivity, final VolleyCallBack callBack){
        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/transaction";

        //create json object
//        final JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("name", this.username);
//            jsonObject.put("bio", this.userDescription);
//            jsonObject.put("photo", this.profileImage);
//
//            String jsonObjectStr = jsonObject.toString();
//        } catch (JSONException e) {
//            // handle exception
//        }

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                response -> {
                    // Format response json data
//                    this.username = username;
//                    this.desc = desc;
//                    this.pfp = pfp;
                    callBack.onSuccess();
                    Log.d("sendPfInfoSuccess", response);
                }, error -> {
            Log.d("sendPfInfoError", String.valueOf(error.networkResponse.statusCode));
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("GET_HEADER", "Made call to getHeaders");
                Map<String, String>  headers = new HashMap<String, String>();
                String token = ProfileInfo.getToken();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError
            {
                Log.d("GET_PARAMS", "Made call to getParams");
                Map<String, String>  params = new HashMap<String, String>();
//                params.put("name", ProfileInfo.getUsername());
//                params.put("bio", ProfileInfo.getUserDescription());
//                params.put("photo", ProfileInfo.getProfileImage());

                final JSONObject jsonObject = new JSONObject();
                String jsonObjectStr = "";
                try {
                    jsonObject.put("sender", TransactionInfo.getSender());
                    jsonObject.put("receiver", TransactionInfo.getReceiver());
                    jsonObject.put("amount",TransactionInfo.getAmount());
                    jsonObject.put("Comment", TransactionInfo.getComment());

                    jsonObjectStr = jsonObject.toString();
                } catch (JSONException e) {
                    //Do nothing
                }

//                String name = ProfileInfo.getUsername();
//                String bio =  ProfileInfo.getUserDescription();
//                String photo = ProfileInfo.getProfileImage();

//                String jsonObjectStr = "{'name': '" + name + "', 'bio':' " + bio + " ', 'photo': ' "+ photo+ "'}";

//                String jsonObjectStr = "short str";

                params.put("transactionInfo", jsonObjectStr);

                return params.toString().getBytes();
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}