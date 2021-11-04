package com.example.myapplication.HomelessYouthUiFrontend;
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

import com.example.myapplication.R;


public class HySignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private HySignUpModel hySignUpModel;
    public static final String REGISTER_TAG = "hyRegister";
    String role;
    final String[] ROLE_TEXTS = {"Homeless Youth", "Donor", "Organization", "Merchant", "Being Seen"};
    public static final String[] ROLES = {"HOMELESS", "DONOR", "ORGANIZATION", "MERCHANT", "BEING_SEEN"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_sign_up);
        role = "HOMELESS";

        // drop down menu
        Spinner spinner;
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HySignUpActivity.this,
                android.R.layout.simple_spinner_item, ROLE_TEXTS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // button
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
                Intent i = new Intent(HySignUpActivity.this, HyLoginActivity.class);
                startActivity(i);
            }
        });

        Log.i(REGISTER_TAG, "hy sign up activity started");
        hySignUpModel = new HySignUpModel(this);
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
        hySignUpModel.signUp(username, password, role);

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
        }  else if (adapterView.getItemAtPosition(i).toString().equals(ROLE_TEXTS[4])) {
            role = "BEING_SEEN";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        role = "HOMELESS";
    }
}
