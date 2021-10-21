package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.AboutUsFrontend.Aboutus;
import com.example.myapplication.HomelessYouthUiFrontend.HyLoginActivity;
import com.example.myapplication.HomelessYouthUiFrontend.HySignUpActivity;
import com.example.myapplication.SearchUI.SearchPage;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Part of API request code was inspired from source code here: https://developer.android.com/training/volley/simple
        // Title: Send a simple request
        // Author: Android developer documentation
        // Date: Sep 26th, 2021

        final TextView textView = (TextView) findViewById(R.id.text);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/test";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    textView.setText("Response is: " + response);
                }, error -> textView.setText("Error"));

        // Add the request to the RequestQueue.
        queue.add(stringRequest);



        //botton for about us:
        Button btn_aboutus= binding.aboutusBtn;
        btn_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, Aboutus.class);
                startActivity(intent);
            }
        });


        //Login Button code
        final Button button = (Button) findViewById(R.id.LoginButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HyLoginActivity.class);
                startActivity(i);

            }
        });

        //SignUp Button code
        final Button signup_button = (Button) findViewById(R.id.SignUpButton);

        signup_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HySignUpActivity.class);
                startActivity(i);

            }
        });

        //Search Button code
        final ImageButton search = (ImageButton) findViewById(R.id.searchButton);

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, SearchPage.class);
                startActivity(intent);
            }
        });

    }



}





