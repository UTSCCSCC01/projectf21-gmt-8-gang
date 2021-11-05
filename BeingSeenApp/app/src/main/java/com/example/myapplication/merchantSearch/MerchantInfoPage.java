package com.example.myapplication.merchantSearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.AboutUsFrontend.Donation;
import com.example.myapplication.HomelessYouthUiFrontend.HySpendMoney;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.SearchUI.SearchPage;
import com.example.myapplication.SearchUI.SearchResult;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;

public class MerchantInfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_info_page);


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
        Button back=(Button) findViewById(R.id.search_back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MerchantInfoPage.this, SearchMerchant.class);
                startActivity(intent);
            }
        });



        Button donate=(Button) findViewById(R.id.searchDonate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MerchantInfoPage.this, HySpendMoney.class);
                //info need db complete
                intent.putExtra("receiver",username);
                intent.putExtra("sender","getCurrentUserId");
                startActivity(intent);
            }
        });
    }
}