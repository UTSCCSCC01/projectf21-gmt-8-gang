package com.example.myapplication.MerchantUiFrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MerMainNavbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer_main_navbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.merBottomNavigationView);
//        NavController navController = Navigation.findNavController(this,  R.id.fragmentContainerView);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.merFragmentContainerView);
        NavController navController = navHostFragment.getNavController();
//        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
//        NavigationUI.setupWithNavController(navController, appBarConfiguration);
    }
}