package com.example.myapplication.HomelessYouthUiFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class HyLoginActivity extends AppCompatActivity {

    private HyLoginModel hyLoginModel;
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
        hyLoginModel = new HyLoginModel(this);
    }

    public void logIn(View view) {
        EditText usernameField = (EditText) findViewById(R.id.HyUsername);
        EditText passwordField = (EditText) findViewById(R.id.HyPassword);

        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

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
}


