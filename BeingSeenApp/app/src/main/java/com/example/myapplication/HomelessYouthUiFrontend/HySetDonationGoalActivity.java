package com.example.myapplication.HomelessYouthUiFrontend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpRetryException;
import java.util.regex.Pattern;

public class HySetDonationGoalActivity extends AppCompatActivity {
    HySetDonationGoalModel model;
    String LOGIN_TAG = HyLoginActivity.LOGIN_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy_set_donation_goal);

        model = new HySetDonationGoalModel(this);

        TextClock textClock = (TextClock) findViewById(R.id.date);
        textClock.setTimeZone("Canada/Eastern");
        textClock.setFormat12Hour("dd-MMM-yyyy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Publish Button code
        final Button publish = (Button) findViewById(R.id.publishGoalButton);
        publish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createDonationGoal();
            }
        });
    }

    //Back button on top
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("hyyy", ProfileInfo.getUserRole());
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDonationGoal() {
        EditText titleField = (EditText) findViewById(R.id.goalTitle);
        EditText descriptionField = (EditText) findViewById(R.id.goalDescription);
        EditText moneyField = (EditText) findViewById(R.id.moneyNeeded);

        String title = titleField.getText().toString();
        String description = descriptionField.getText().toString();
        String moneyString = moneyField.getText().toString();

        if (title.isEmpty()) {
            titleField.setError("title cannot be blank");
            titleField.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            descriptionField.setError("description cannot be blank");
            descriptionField.requestFocus();
            return;
        }

        if (moneyString.isEmpty()) {
            moneyField.setError("your goal cannot be blank");
            moneyField.requestFocus();
            return;
        }

        Long moneyLong;
        if (Pattern.matches("[\\d]+", moneyString)) {
            moneyLong = Long.parseLong(moneyString);
        } else {
            moneyField.setError("This is not a number");
            moneyField.requestFocus();
            return;
        }

        if (moneyLong == 0L) {
            moneyField.setError("your goal cannot be zero");
            moneyField.requestFocus();
            return;
        }

        model.createDonationGoal(title, description, moneyLong);
        return;
    }
}