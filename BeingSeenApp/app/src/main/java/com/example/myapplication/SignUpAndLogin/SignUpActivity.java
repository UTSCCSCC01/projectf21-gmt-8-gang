package com.example.myapplication.SignUpAndLogin;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.NavbarActivities.OrgMainNavbarActivity;
import com.example.myapplication.R;


public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SignUpModel signUpModel;
    public static final String REGISTER_TAG = "hyRegister";
    String role;
    final String[] ROLE_TEXTS = {"Youth", "Donor", "Organization", "Merchant"};
    public static final String[] ROLES = {"HOMELESS", "DONOR", "ORGANIZATION", "MERCHANT", "BEING_SEEN"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        role = "HOMELESS";

        // drop down menu
        Spinner spinner;
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignUpActivity.this,
                android.R.layout.simple_spinner_item, ROLE_TEXTS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button button = (Button) findViewById(R.id.HySignUpButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
            }
        });

        Button button2 = (Button) findViewById(R.id.GoToLogIn);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        Log.i(REGISTER_TAG, "hy sign up activity started");
        signUpModel = new SignUpModel(this);

    }

    public void signUp(View view) {

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

        // we start a new activity in here
        signUpModel.signUp(username, password, role);

        Log.i(REGISTER_TAG, "sign up finished");
        return;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(REGISTER_TAG, "spinner tostring: " + adapterView.getItemAtPosition(i).toString());
        if (adapterView.getItemAtPosition(i).toString().equals(ROLE_TEXTS[0])) {
            role = "HOMELESS";
        } else if (adapterView.getItemAtPosition(i).toString().equals(ROLE_TEXTS[1])) {
            role = "DONOR";
        } else if (adapterView.getItemAtPosition(i).toString().equals(ROLE_TEXTS[2])) {
            role = "ORGANIZATION";
        } else if (adapterView.getItemAtPosition(i).toString().equals(ROLE_TEXTS[3])) {
            role = "MERCHANT";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        role = "HOMELESS";
    }


}
