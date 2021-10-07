package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void getInfoFromDb(AppCompatActivity callingActivity){

        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/test";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                        // Format response json data
//                    this.username = username;
//                    this.desc = desc;
//                    this.pfp = pfp;
                }, error -> this.username = null);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void sendInfoToDb(AppCompatActivity callingActivity){
        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/test";

        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
//                response -> {
//                    // Format response json data
////                    this.username = username;
////                    this.desc = desc;
////                    this.pfp = pfp;
//                }, error -> this.username = null);

        //create json object
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profileName", this.username);
            jsonObject.put("profileDesc", this.userDescription);
            jsonObject.put("pfpEncrypted", this.profileImage);
        } catch (JSONException e) {
            // handle exception
        }

        //add json object
//        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // response
//                        Log.d("Response", response.toString());
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d("Error.Response", error.toString());
//                    }
//                }
//        ) {
//
//            @Override
//            public Map<String, String> getHeaders()
//            {
//                Map<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                headers.put("Accept", "application/json");
//                return headers;
//            }
//
//            @Override
//            public byte[] getBody() {
//
//                try {
//                    Log.i("json", jsonObject.toString());
//                    return jsonObject.toString().getBytes("UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };

        // Add the request to the RequestQueue.
//        queue.add(putRequest);
    }
}
