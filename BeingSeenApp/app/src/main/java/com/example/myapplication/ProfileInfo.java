package com.example.myapplication;

import android.widget.ImageView;

public class ProfileInfo {
    String username;
    String userDescription;
    ImageView profileImage;

    public ProfileInfo(String username, String userDescription, ImageView profileImage){
        this.username = username;
        this.userDescription = userDescription;
        this.profileImage = profileImage;
    }

    public ProfileInfo(String username, String userDescription){
        this.username = username;
        this.userDescription = userDescription;
    }
}
