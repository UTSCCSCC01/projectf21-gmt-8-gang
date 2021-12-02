package com.example.myapplication.SearchForYouth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.AboutUsFrontend.DonationActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

public class ShowYouthInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_youth_info);

        String username=getIntent().getStringExtra("Username");
        String name=getIntent().getStringExtra("name");
        String bio=getIntent().getStringExtra("bio");
        String photo=getIntent().getStringExtra("photo");
        ImageView img=(ImageView) findViewById(R.id.searchPhoto);
        img.setImageBitmap(ProfileInfo.decodeProfilePic(photo));
        TextView displayName=(TextView) findViewById(R.id.searchNameDisplay);
        displayName.setText(name);
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
                intent.setClass(ShowYouthInfoActivity.this, DonationActivity.class);
                //info need db complete
                intent.putExtra("receiverUsername",username);
                intent.putExtra("receiverName", name);
                intent.putExtra("sender","getCurrentUserId");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    // back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}