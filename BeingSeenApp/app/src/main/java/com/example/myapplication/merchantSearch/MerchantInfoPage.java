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
        ImageView img=(ImageView) findViewById(R.id.searchPhoto);
        TextView name=(TextView) findViewById(R.id.searchNameDisplay);
        //name.setText(db.nickName(Username));
        TextView desc=(TextView)findViewById(R.id.searchUdescDisplay);
        //desc.setText(db.desc(Username));
        Button back=(Button) findViewById(R.id.search_back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MerchantInfoPage.this, SearchMerchant.class);
                startActivity(intent);
            }
        });

        ProfileInfo profileInf = new ProfileInfo();
        profileInf.setSearchIdName(username);

        profileInf.searchHomeless(this,
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                        name.setText(profileInf.getSearchUsername());
                        desc.setText(profileInf.getSearchDescription());
                        img.setImageBitmap(ProfileInfo.decodeProfilePic(profileInf.getSearchProfileImage()));

                    }
                });

        Button donate=(Button) findViewById(R.id.searchDonate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MerchantInfoPage.this, HySpendMoney.class);
                //info need db complete
                intent.putExtra("reciever",username);
                intent.putExtra("sender","getCurrentUserId");
                startActivity(intent);
            }
        });
    }
}