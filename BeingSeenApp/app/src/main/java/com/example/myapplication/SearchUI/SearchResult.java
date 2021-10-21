package com.example.myapplication.SearchUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.AboutUsFrontend.Donation;
import com.example.myapplication.AboutUsFrontend.DonationStatus;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import org.w3c.dom.Text;

public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
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
                intent.setClass(SearchResult.this, MainActivity.class);
                startActivity(intent);
            }
        });
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