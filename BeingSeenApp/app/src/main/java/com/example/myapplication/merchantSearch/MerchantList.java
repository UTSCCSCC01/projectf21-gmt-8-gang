package com.example.myapplication.merchantSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Adapters.MerchantRecyclerAdapter;
import com.example.myapplication.Adapters.TransactionRecyclerAdapter;
import com.example.myapplication.R;
import com.example.myapplication.Transaction;

import org.w3c.dom.Text;

import java.util.List;

public class MerchantList extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_list);
        String username=getIntent().getStringExtra("Username");
        TextView inputText=(TextView) findViewById(R.id.ml_input);
        inputText.setText(username);
        recyclerView = findViewById(R.id.dn_donations_recycler_view);
    }

    private void setAdapter() {
        //fetch list of result's username and nickname from transaction DB
        List<String> nickname=null;
        List<String> username=null;

        // if data is null then return?
        MerchantRecyclerAdapter adapter = new MerchantRecyclerAdapter(nickname, username);
        // sets the layout, default animator, and adapter of recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}