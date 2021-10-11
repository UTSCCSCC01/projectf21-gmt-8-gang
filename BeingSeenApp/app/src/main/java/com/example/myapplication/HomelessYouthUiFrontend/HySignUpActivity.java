package com.example.myapplication.HomelessYouthUiFrontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;


public class HySignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_sign_up);

        EditText username = findViewById(R.id.HyUsername);
        EditText password = findViewById(R.id.HyPassword);
        postData(username, password);

        //drop-down menu
        Spinner spinner;
        final String[] paths = {"homeless", "donor"};
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HySignUpActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Sign Up Button code
        final Button button = findViewById(R.id.HySignUpButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().length() != 0 &&
                        password.getText().toString().trim().length() != 0) {
                    Intent i = new Intent(getApplicationContext(), HyUserInterfaceActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(HySignUpActivity.this, "400 bad request. Username " +
                            "or password is missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //button leading users to the log in page
        final Button GoToLogIn_button = findViewById(R.id.GoToLogIn);

        GoToLogIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),HyLoginActivity.class);
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
                                Intent i = new Intent(getApplicationContext(),HyLoginActivity.class);
                                startActivity(i);
                                //parse();
                            } else {
                                Toast.makeText(HySignUpActivity.this, "400 bad request", Toast.LENGTH_SHORT).show();
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
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username.getText().toString());
                params.put("password",password.getText().toString());

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);


    }


}