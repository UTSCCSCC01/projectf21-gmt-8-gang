package com.example.myapplication.SearchUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;


public class SearchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        TextInputEditText input= (TextInputEditText) findViewById(R.id.searchInput);
        ImageButton search=(ImageButton) findViewById(R.id.SearchUserName);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String target=input.getText().toString();
                if(target.equals("")){
                    new AlertDialog.Builder(SearchPage.this)
                            .setTitle("Invalid Username")
                            .setMessage("Please enter the Username")
                            .setPositiveButton("Confirm",null)
                            .show();
                }
                //else if(Username not found in database) pop an alert like^^^
                else{
                    Intent intent=new Intent();
                    intent.setClass(SearchPage.this, SearchResult.class);
                    intent.putExtra("Username", target);
                    startActivity(intent);
                }
            }
        });
    }
}