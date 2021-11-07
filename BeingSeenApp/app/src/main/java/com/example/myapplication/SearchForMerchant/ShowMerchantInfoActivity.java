package com.example.myapplication.SearchForMerchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;

public class ShowMerchantInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_merchant_info);

        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //code from SearchResult
        String username=getIntent().getStringExtra("Username");
        String nickname=getIntent().getStringExtra("name");
        String bio=getIntent().getStringExtra("bio");
        String photo=getIntent().getStringExtra("photo");
        ImageView img=(ImageView) findViewById(R.id.searchPhoto);
        img.setImageBitmap(ProfileInfo.decodeProfilePic(photo));
        TextView name=(TextView) findViewById(R.id.searchNameDisplay);
        name.setText(nickname);
        TextView desc=(TextView)findViewById(R.id.searchUdescDisplay);
        desc.setText(bio);



        Button donate=(Button) findViewById(R.id.searchDonate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(ShowMerchantInfoActivity.this, TransferToMerchantActivity.class);
                //info need db complete
                intent.putExtra("receiver",username);
                intent.putExtra("sender","getCurrentUserId");
                startActivity(intent);
            }
        });
    }

    //Back button on top
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}