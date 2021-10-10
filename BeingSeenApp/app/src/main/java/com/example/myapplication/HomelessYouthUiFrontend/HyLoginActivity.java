package com.example.myapplication.HomelessYouthUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class HyLoginActivity extends AppCompatActivity implements VolleyResponse {

    private HyLoginModel hyLoginModel;
    private VolleyResponse volleyResponse;
    public static final String LOGIN_TAG = "hyLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_login);


        //Login Button code
        final Button button = (Button) findViewById(R.id.HyLoginButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),HyUserInterfaceActivity.class);
                startActivity(i);

                logIn(view);

            }
        });

        Log.i(LOGIN_TAG, "hy login activity started");
        hyLoginModel = new HyLoginModel(this, this);
    }

    public void logIn(View view) {
        EditText usernameField = (EditText) findViewById(R.id.HyUsername);
        EditText passwordField = (EditText) findViewById(R.id.HyPassword);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if (username.isEmpty()) {
            usernameField.setError("please enter a username");
            usernameField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordField.setError("please enter a password");
            passwordField.requestFocus();
            return;
        }

        hyLoginModel.logIn(username, password);

        Log.i(LOGIN_TAG, "hy login success");
        return;
    }

    @Override
    public void onVolleySuccess(JSONObject response) {
        try {
            String jwtToken = response.getString("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String userRole = response.getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


