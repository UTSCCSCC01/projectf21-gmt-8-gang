package com.example.myapplication.HomelessYouthUiFrontend;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_sign_up);
        role = "HOMELESS";

        // drop down menu
        Spinner spinner;
        final String[] paths = {"youth", "donor"};
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HySignUpActivity.this,
                android.R.layout.simple_spinner_item,paths);
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
        if (adapterView.getItemAtPosition(i).toString().equals("donor")) {
            role = "DONOR";
        } else if (adapterView.getItemAtPosition(i).toString().equals("youth")) {
            role = "HOMELESS";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        role = "HOMELESS";
    }
}
