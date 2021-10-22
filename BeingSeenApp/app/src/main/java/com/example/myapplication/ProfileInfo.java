package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceControl;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import javax.imageio.ImageIO;

public class ProfileInfo implements Serializable {
    static String token;
    static String accountName;
    static String username;
    static String userDescription;
    static String profileImage;
    static String balance;
    static String userRole;

    public static String getToken() {
        return token;
    }

    public static String getAccountName(){
        return accountName;
    }

    public static void setToken(String tokenIn) {
        token = tokenIn;
    }

    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getUserRole() {
        return userRole;
    }

    public static void setUserRole(String userRole) {
        ProfileInfo.userRole = userRole;
    }

    public static String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public static String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public static String getBalance() {return balance;}

    public void setBalance(String balance) {this.balance = balance;}

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = encodeProfilePic(profileImage);
    }

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

    public ProfileInfo(){
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

    public static Bitmap decodeProfilePic(String encodedImage) {
        byte[] pfpbytes = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(pfpbytes, 0, pfpbytes.length);
        return bmp;
    }

    public void getInfoFromDb(AppCompatActivity callingActivity, final VolleyCallBack callBack){

        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/profile";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    Log.d("RESPONSE_VAR", "Reponse called properly");
                    JSONObject jsonItem;
                    try {
                        jsonItem = new JSONObject(response);
                        this.accountName = jsonItem.getString("userName");
                        String pfString = jsonItem.getString("profileInfo");
                        JSONObject profileDBInf = new JSONObject(pfString);
                        this.username = profileDBInf.getString("name");
                        this.userDescription = profileDBInf.getString("bio");
                        this.profileImage = profileDBInf.getString("photo");
                        String balanceString = jsonItem.getString("balance");
                        this.balance = balanceString;

                        if (this.username.equals("")){
                            this.username = "Username";
                        }
                        if (this.userDescription.equals("")){
                            this.userDescription = "Bio";
                        }
                        if (this.profileImage.equals("")){
                            File root = Environment.getExternalStorageDirectory();
                            File folderInput = new File(root.getAbsolutePath()+"res/drawable/profile.png");
                            try {
//                                Bitmap myBitmap = BitmapFactory.decodeFile(folderInput.getAbsolutePath());
                                Bitmap bmImg = BitmapFactory.decodeFile(folderInput.getAbsolutePath());
                                ImageView imageView = new ImageView(callingActivity);
                                imageView.setImageBitmap(bmImg);
                                this.setProfileImage(imageView);
                            }
                            catch(Exception e){

                            }
                        }

                        Log.d("RESPONSE_VAR", "Username received as "+this.username);
                        callBack.onSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> this.username = "ERROR")
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("GET_HEADER", "Made call to getHeaders");
                Map<String, String>  params = new HashMap<String, String>();
                String token = ProfileInfo.getToken();
                //Log.d("RESPONSE_VAR", token);
                params.put("Authorization", token);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    public static void getUserRoleFromDb(AppCompatActivity callingActivity, final VolleyCallBack callBack){

        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/profile";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    Log.d("RESPONSE_VAR", "Reponse called properly");
                    JSONObject jsonItem;
                    try {
                        jsonItem = new JSONObject(response);
                        String userRole = jsonItem.getString("role");
                        ProfileInfo.userRole = userRole;

                        Log.d("RESPONSE_VAR", "Userrole received as "+ProfileInfo.userRole);
                        callBack.onSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> ProfileInfo.userRole = "ERROR")
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("GET_HEADER", "Made call to getHeaders");
                Map<String, String>  params = new HashMap<String, String>();
                String token = ProfileInfo.getToken();
                Log.d("RESPONSE_VAR", token);
                params.put("Authorization", token);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void sendInfoToDb(AppCompatActivity callingActivity, final VolleyCallBack callBack){
        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/profile";

        //create json object
//        final JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("name", this.username);
//            jsonObject.put("bio", this.userDescription);
//            jsonObject.put("photo", this.profileImage);
//
//            String jsonObjectStr = jsonObject.toString();
//        } catch (JSONException e) {
//            // handle exception
//        }

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                response -> {
                    // Format response json data
//                    this.username = username;
//                    this.desc = desc;
//                    this.pfp = pfp;
                    callBack.onSuccess();
                    Log.d("sendPfInfoSuccess", response);
                }, error -> {
                    Log.d("sendPfInfoError", String.valueOf(error.networkResponse.statusCode));
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("GET_HEADER", "Made call to getHeaders");
                Map<String, String>  headers = new HashMap<String, String>();
                String token = ProfileInfo.getToken();
                headers.put("Authorization", token);
                Log.d("gettoken", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError
            {
                Log.d("GET_PARAMS", "Made call to getParams");
                Map<String, String>  params = new HashMap<String, String>();
//                params.put("name", ProfileInfo.getUsername());
//                params.put("bio", ProfileInfo.getUserDescription());
//                params.put("photo", ProfileInfo.getProfileImage());

                final JSONObject jsonObject = new JSONObject();
                String jsonObjectStr = "";
                try {
                    jsonObject.put("name", ProfileInfo.getUsername());
                    jsonObject.put("bio", ProfileInfo.getUserDescription());
                    jsonObject.put("photo", ProfileInfo.getProfileImage());

                    jsonObjectStr = jsonObject.toString();
                } catch (JSONException e) {
                    //Do nothing
                }

//                String name = ProfileInfo.getUsername();
//                String bio =  ProfileInfo.getUserDescription();
//                String photo = ProfileInfo.getProfileImage();

//                String jsonObjectStr = "{'name': '" + name + "', 'bio':' " + bio + " ', 'photo': ' "+ photo+ "'}";

//                String jsonObjectStr = "short str";

                params.put("profileInfo", jsonObjectStr);

                return params.toString().getBytes();
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
