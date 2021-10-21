package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaction {
    static List<String> receivers;
    static List<String> senders;
    static List<Long> amounts;

    public static List<String> getReceivers() {return receivers;}

    public static List<String> getSenders() {return senders;}


    public static List<Long> getAmounts() {return amounts;}

    public static void setAmounts(List<Long> amountsLst) {
        amounts = amountsLst;
    }

    public static void setReceivers(List<String> receiversLst) {
        receivers = receiversLst;
    }

    public static void setSenders(List<String> sendersLst) {
        senders = sendersLst;
    }

    public static void addAmounts(Long amount) {
        amounts.add(amount);
    }

    public static void addReceivers(String receiver) {
        receivers.add(receiver);
    }

    public static void addSenders(String sender) {
        senders.add(sender);
    }


    public Transaction() {
        receivers = new ArrayList<>();
        senders = new ArrayList<>();
        amounts = new ArrayList<>();
    }

    public void getDnTransactionFromDb(AppCompatActivity callingActivity, final VolleyCallBack callBack){

        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/transaction/sender?username=";
        String name_sender = ProfileInfo.getAccountName();
        Log.d("TRANS_VAR", name_sender);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + name_sender, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("RESPONSE_VAR", "Transaction DN called properly");
                    try {
                        JSONArray jsonTransactions = new JSONArray(response);
                        Log.d("Transaction_VAR","TRANSACTION DN Worked1");

                        List<String> temp_receivers = new ArrayList<String>();
                        List<Long> temp_amounts = new ArrayList<Long>();
                        for (int i = 0; i <jsonTransactions.length(); i++) {
                            JSONObject jsonTransaction = jsonTransactions.getJSONObject(i);
                            String receiver = jsonTransaction.getString("receiver");
                            Long amount = jsonTransaction.getLong("amount");

                            temp_receivers.add(receiver);
                            setReceivers(temp_receivers);

                            temp_amounts.add(amount);
                            setAmounts(temp_amounts);
                        }
                callBack.onSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("GET_HEADER", "Made call to Transaction getHeaders");
                Map<String, String>  headers = new HashMap<String, String>();
                String token = ProfileInfo.getToken();
                Log.d("RESPONSE_VAR", token);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }


        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getHyTransactionFromDb(AppCompatActivity callingActivity, final VolleyCallBack callBack){

        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/transaction/receiver?username=";
        String name_receiver = ProfileInfo.getAccountName();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + name_receiver, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("RESPONSE_VAR", "Transaction HY called properly");
                try {
                    JSONArray jsonTransactions = new JSONArray(response);
                    Log.d("Transaction_VAR","TRANSACTION HY Worked1");

                    List<String> temp_senders = new ArrayList<String>();
                    List<Long> temp_amounts = new ArrayList<Long>();
                    for (int i = 0; i <jsonTransactions.length(); i++) {
                        JSONObject jsonTransaction = jsonTransactions.getJSONObject(i);
                        String sender = jsonTransaction.getString("sender");
                        Long amount = jsonTransaction.getLong("amount");

                        temp_senders.add(sender);
                        setSenders(temp_senders);

                        temp_amounts.add(amount);
                        setAmounts(temp_amounts);
                    }
                   callBack.onSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("GET_HEADER", "Made call to Transaction getHeaders");
                Map<String, String>  headers = new HashMap<String, String>();
                String token = ProfileInfo.getToken();
                Log.d("RESPONSE_VAR", token);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }


        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void makeDnDonationTransaction(String receiver, String comment, Long amount,
                                          AppCompatActivity callingActivity,
                                          final VolleyCallBack callBack){
        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/transaction";
        String name_sender = ProfileInfo.getAccountName();
        Log.d("TRANS_VAR", name_sender);

        Transaction.addSenders(name_sender);
        Transaction.addReceivers(receiver);
        Transaction.addAmounts(amount);

        //new String req
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                response -> {
                    // if succesful alert calling method
                    Log.d("AddTransactionSuccess", response);
                    callBack.onSuccess();
                }, error -> {
            callBack.onFailure();
            Log.d("sendTransactionError", String.valueOf(error.networkResponse.statusCode));
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("TN_GET_HEADER", "Transaction called getHeaders");
                Map<String, String>  headers = new HashMap<String, String>();
                String token = ProfileInfo.getToken();
                headers.put("Authorization", token);
//                Log.d("gettoken", token);
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
                Log.d("TN_GET_PARAMS", "Transaction calls getParams");
                Map<String, Object>  params = new HashMap<String, Object>();
                params.put("sender", name_sender);
                params.put("receiver", receiver);
                params.put("amount", amount);
                params.put("comment", comment);

                return params.toString().getBytes();
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
