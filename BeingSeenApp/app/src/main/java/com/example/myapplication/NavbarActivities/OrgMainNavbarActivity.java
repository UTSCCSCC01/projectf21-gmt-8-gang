package com.example.myapplication.NavbarActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrgMainNavbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_main_navbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.orgBottomNavigationView);
//        NavController navController = Navigation.findNavController(this,  R.id.fragmentContainerView);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.orgFragmentContainerView);
        NavController navController = navHostFragment.getNavController();
//        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
//        NavigationUI.setupWithNavController(navController, appBarConfiguration);
    }
}