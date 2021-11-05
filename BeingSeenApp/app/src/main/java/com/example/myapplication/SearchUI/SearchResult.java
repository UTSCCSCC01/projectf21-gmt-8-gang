package com.example.myapplication.SearchUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.AboutUsFrontend.Donation;
import com.example.myapplication.AboutUsFrontend.DonationStatus;
import com.example.myapplication.HomelessYouthUiFrontend.HyUserProfileEditActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

import org.w3c.dom.Text;

public class SearchResult extends AppCompatActivity {
//code base on DnUserProfileViewDonationActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

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
                intent.setClass(SearchResult.this, SearchPage.class);
                startActivity(intent);
            }
        });

        /*ProfileInfo profileInf = new ProfileInfo();
        profileInf.setSearchIdName(username);

        profileInf.searchHomeless(this,
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                        name.setText(profileInf.getSearchUsername());
                        desc.setText(profileInf.getSearchDescription());
                        img.setImageBitmap(ProfileInfo.decodeProfilePic(profileInf.getSearchProfileImage()));

                    }
                });*/

        Button donate=(Button) findViewById(R.id.searchDonate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(SearchResult.this,Donation.class);
                //info need db complete
                intent.putExtra("reciever",username);
                intent.putExtra("sender","getCurrentUserId");
                startActivity(intent);
            }
        });
    }
}