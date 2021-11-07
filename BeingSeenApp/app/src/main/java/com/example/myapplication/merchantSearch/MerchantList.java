package com.example.myapplication.merchantSearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MerchantList extends AppCompatActivity {
    RecyclerView recyclerView;
    List<JSONObject> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_list);
        String username=getIntent().getStringExtra("Username");
        TextView inputText=(TextView) findViewById(R.id.ml_input);
        inputText.setText(username);
        recyclerView = findViewById(R.id.ml_list);

        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProfileInfo profileInfo=new ProfileInfo();
        profileInfo.setSearchIdName(username);
        profileInfo.searchUser(this, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                result=profileInfo.getSearchResult();
                setAdapter();
            }
        });
        Button back=(Button) findViewById(R.id.ml_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                i.setClass(MerchantList.this,SearchMerchant.class);
                startActivity(i);
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

    private void setAdapter() {
        //fetch list of result's username, role, and profile from DB
        List<String> profile=new ArrayList<String>();
        List<String> username=new ArrayList<String>();
        List<String> role=new ArrayList<String>();
        if(result!=null){
            try {
                for (int i = 0; i < result.size(); i++) {

                    profile.add(result.get(i).getString("profileInfo"));
                    username.add(result.get(i).getString("userName"));
                    role.add(result.get(i).getString("role"));
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        // if data is null then return?
        MerchantRecyclerAdapter adapter = new MerchantRecyclerAdapter(username, role, profile);
        // sets the layout, default animator, and adapter of recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}