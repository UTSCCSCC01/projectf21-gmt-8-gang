package com.example.myapplication.DonorUiFrontend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class DnContentPageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DnContentPageAdapter adapter;
    DnContentPageHolder holder;
    private DnContentPageAdapter.ContentPageRecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_content_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        adapter = new DnContentPageAdapter(this, getModels(), listener);
        recyclerView.setAdapter(adapter);

    }

    private void setOnClickListener() {
        listener = new DnContentPageAdapter.ContentPageRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Log.i("suppp", "we clicked, position: " + position + ", user: "
                + getModels().get(position).getName());
            }
        };
    }

    private ArrayList<DnContentPageModel> getModels() {
        ArrayList<DnContentPageModel> models = new ArrayList<>();

        DnContentPageModel model = new DnContentPageModel();
        model.setImg(R.drawable.profile);
        model.setName("homeless youth's name");
        model.setTitle("i wanna pay for job training");
        model.setDescription("how i spend the money");
        models.add(model);

        model = new DnContentPageModel();
        model.setImg(R.drawable.profile);
        model.setName("homeless youth's name 2");
        model.setTitle("i wanna pay for job training");
        model.setDescription("how i spend the money");
        models.add(model);

        model = new DnContentPageModel();
        model.setImg(R.drawable.profile);
        model.setName("homeless youth's name 3");
        model.setTitle("i wanna pay for job training");
        model.setDescription("how i spend the money");
        models.add(model);

        return models;
    }
}