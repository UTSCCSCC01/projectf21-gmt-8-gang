package com.example.myapplication.YouthDonationGoal;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.SignUpAndLogin.LoginActivity;

import java.util.regex.Pattern;

public class SetDonationGoalActivity extends AppCompatActivity {
    SetDonationGoalModel model;
    String LOGIN_TAG = LoginActivity.LOGIN_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_donation_goal);

        model = new SetDonationGoalModel(this);

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