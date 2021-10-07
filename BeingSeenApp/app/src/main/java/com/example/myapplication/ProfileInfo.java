package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ProfileInfo {
    String username;
    String userDescription;
    String profileImage;

    public ProfileInfo(String username, String userDescription, String profileImage){
        this.username = username;
        this.userDescription = userDescription;
        this.profileImage = profileImage;
    }

    public ProfileInfo(String username, String userDescription, ImageView profileImageView){
        this.username = username;
        this.userDescription = userDescription;
        this.profileImage = encodeProfilePic(profileImageView);
    }

    public ProfileInfo(String username, String userDescription){
        this.username = username;
        this.userDescription = userDescription;
    }

    public static String encodeProfilePic(ImageView image){
        image.buildDrawingCache();
        Bitmap profilePicBitmap= image.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profilePicBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();
        String profilePi64Encoded = Base64.encodeToString(b, Base64.DEFAULT);
        return profilePi64Encoded;
    }

    public static Bitmap decodeProfilePic(String encodedImage){
        byte[] pfpbytes = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(pfpbytes, 0, pfpbytes.length);
        return bmp;
    }
}
