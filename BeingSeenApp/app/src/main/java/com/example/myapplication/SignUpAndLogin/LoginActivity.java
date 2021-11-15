package com.example.myapplication.SignUpAndLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.YouthDonationGoal.VolleyResponse;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements VolleyResponse {

    private LoginModel loginModel;
    private VolleyResponse volleyResponse;
    public static final String LOGIN_TAG = "hyLogin";
    Boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // don't see loading animation
        findViewById(R.id.loading_lottie_animation_view).setVisibility(View.GONE);

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

        Intent intent = getIntent();
        if (intent.hasExtra("Toast")) {
            Toast.makeText(getApplicationContext(), intent.getStringExtra("Toast"), Toast.LENGTH_SHORT).show();
        }

        Log.i(LOGIN_TAG, "hy login activity started");
        loginModel = new LoginModel(this, this);
    }

    public void showLoadingScreen() {
        isLoading = true;
        getSupportActionBar().hide();
        findViewById(R.id.HyUsername).setVisibility(View.GONE);
        findViewById(R.id.HyUsernameFrame).setVisibility(View.GONE);
        findViewById(R.id.HyPassword).setVisibility(View.GONE);
        findViewById(R.id.HyPasswordFrame).setVisibility(View.GONE);
        findViewById(R.id.HyLoginButton).setVisibility(View.GONE);
        LottieAnimationView lottieAnimationView = findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
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

        showLoadingScreen();
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override public void finish() {
        if (isLoading) return;
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}


