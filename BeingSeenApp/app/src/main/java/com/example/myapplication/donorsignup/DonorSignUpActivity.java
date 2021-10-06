package com.example.myapplication.donorsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.HomeFragment;


/**
 * DONT MERGE THIS BRANCH TO OTHERS BEFORE CHECKING THE MANIFESTS FILE!
 * (I set the main page to be this page)
 */
public class DonorSignUpActivity extends AppCompatActivity {

    private DonorSignUpModel donorSignUpModel;
    public static final String REGISTER_TAG = "donorRegister";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_sign_up);

        Button button = (Button) findViewById(R.id.signup_donor_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
            }
        });

        Log.i(REGISTER_TAG, "donor sign up activity started");
        donorSignUpModel = new DonorSignUpModel(this);
    }

    public void signUp(View view) {
        EditText usernameField = (EditText) findViewById(R.id.signup_donor_username);
        EditText passwordField = (EditText) findViewById(R.id.signup_donor_password);

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

        donorSignUpModel.signUp(username, password);

        //Intent intent = new Intent(this, HomeFragment.class);
        //this.startActivity(intent);

        Log.i(REGISTER_TAG, "sign up successsss");
        return;
    }


}