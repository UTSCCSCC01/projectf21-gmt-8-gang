package com.example.myapplication.SearchForYouth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.AboutUsFrontend.Donation;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

public class ShowYouthInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        String username=getIntent().getStringExtra("Username");
        String nickname=getIntent().getStringExtra("name");
        String bio=getIntent().getStringExtra("bio");
        String photo=getIntent().getStringExtra("photo");
//        ImageView img=(ImageView) findViewById(R.id.searchPhoto);
//        img.setImageBitmap(ProfileInfo.decodeProfilePic(photo));
        TextView name=(TextView) findViewById(R.id.searchNameDisplay);
        name.setText(nickname);
        TextView desc=(TextView)findViewById(R.id.searchUdescDisplay);
        desc.setText(bio);


//        // back button
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // donate button
        Button donate=(Button) findViewById(R.id.searchDonate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(ShowYouthInfoActivity.this,Donation.class);
                //info need db complete
                intent.putExtra("receiver",username);
                intent.putExtra("sender","getCurrentUserId");
                startActivity(intent);
            }
        });
    }

    // back button
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
}