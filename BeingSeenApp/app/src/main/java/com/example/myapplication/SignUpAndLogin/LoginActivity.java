package com.example.myapplication.SignUpAndLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.YouthDonationGoal.VolleyResponse;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements VolleyResponse {

    private LoginModel loginModel;
    private VolleyResponse volleyResponse;
    public static final String LOGIN_TAG = "hyLogin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Login Button code
        final Button button = (Button) findViewById(R.id.HyLoginButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                /*Intent i = new Intent(getApplicationContext(),HyUserInterfaceActivity.class);
                startActivity(i);*/

                logIn(view);

            }
        });

        Log.i(LOGIN_TAG, "hy login activity started");
        loginModel = new LoginModel(this, this);
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

        Log.i(LOGIN_TAG, "hy login success");
        loginModel.logIn(username, password);

        return;
    }

    @Override
    public void onVolleySuccess(JSONObject response) {
        try {
            String jwtToken = response.getString("response");
            String userType = response.getString("code");
            Log.i(LOGIN_TAG, "jwt token: " +jwtToken);
            ProfileInfo.setToken(jwtToken);
            ProfileInfo.setUserRole(userType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String userRole = response.getString("code");
            Log.i(LOGIN_TAG, "role: " + userRole);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, SignUpActivity.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        return super.onOptionsItemSelected(item);
    }

}


