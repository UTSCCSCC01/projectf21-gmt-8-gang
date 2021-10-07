package com.example.myapplication.HomelessYouthUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class HyLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_login);

        final TextView username = (TextView) findViewById(R.id.HyUsername);
        final TextView password = (TextView) findViewById(R.id.HyPassword);
        getData(username, password);


        //Login Button code
        final Button button = (Button) findViewById(R.id.HyLoginButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),HyUserInterfaceActivity.class);
                startActivity(i);

            }
        });
    }

    private void getData(TextView username, TextView password) {
        String url ="localhost:8080/register";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try{
                        //Create a JSON object containing information from the API.
                        JSONObject myJsonObject = new JSONObject(response);
                        username.setText(myJsonObject.getString("username"));
                        password.setText(myJsonObject.getString("password"));
                    } catch ( JSONException e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> Toast.makeText(HyLoginActivity.this, volleyError.getMessage(),
                        Toast.LENGTH_SHORT).show()
        );
        // Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}