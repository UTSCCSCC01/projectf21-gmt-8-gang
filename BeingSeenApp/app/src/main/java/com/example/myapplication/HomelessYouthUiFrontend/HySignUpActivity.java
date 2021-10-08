package com.example.myapplication.HomelessYouthUiFrontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;



public class HySignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_login);

        EditText username = findViewById(R.id.HyUsername);
        EditText password = findViewById(R.id.HyPassword);
        postData(username, password);


        //Login Button code
        final Button button = findViewById(R.id.HyLoginButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),HyUserInterfaceActivity.class);
                startActivity(i);

            }
        });
    }

    private void postData(TextView username, TextView password) {
        String url ="localhost:8080/register";
        JSONObject data = new JSONObject();
        try {
            //input your API parameters
            data.put("username",username.getText());
            data.put("password",password.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("JSON", String.valueOf(response));
                            String Error = response.getString("httpStatus");
                            if (Error.equals("OK")){
                                username.setText(response.getString("username"));
                                password.setText(response.getString("password"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(HySignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

        /*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try{
                        //Create a JSON object containing information from the API.
                        JSONObject data = new JSONObject(response);
                        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusLine().getStatusCode());
                        if (httpStatus.is2xxSuccessful()){
                            data.put("username", username.getText());
                            data.put("password", password.getText());
                        }
                    } catch ( JSONException e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> Toast.makeText(HySignUpActivity.this, volleyError.getMessage(),
                        Toast.LENGTH_SHORT).show()
        );
        // Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        */
    }

}