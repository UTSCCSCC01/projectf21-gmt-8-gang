package com.example.myapplication.DonorUiFrontend;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DnContentPageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DnContentPageAdapter adapter;
    private DnContentPageAdapter.ContentPageRecyclerViewClickListener listener;
    ArrayList<DnContentPageModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_content_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        getAllDonationGoalsFromDbAndSetAdapter(this, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                setAdapter();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("hyyy", ProfileInfo.getUserRole());
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        adapter = new DnContentPageAdapter(this, models, listener);
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new DnContentPageAdapter.ContentPageRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Log.i("suppp", "we clicked, position: " + position + ", user: "
                + models.get(position).getName());
            }
        };
    }

    public void getAllDonationGoalsFromDbAndSetAdapter(AppCompatActivity callingActivity, final VolleyCallBack callBack){

        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/allDonationGoals";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    Log.d("RESPONSE_VAR", "Reponse called properly");
                    JSONObject jsonItem;
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        models = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            DnContentPageModel model = new DnContentPageModel();
                            model.setName(jsonObject.getString("username"));
                            model.setTitle(jsonObject.getString("title"));
                            model.setDescription(jsonObject.getString("description"));
                            //model.setImg(R.drawable.profile);
                            models.add(model);
                        }
                        callBack.onSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                        if (error.networkResponse.statusCode == 404) {
                            Log.i("supp", "we dont have any donation goals, handle this later");
                        }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("GET_HEADER", "Made call to getHeaders");
                Map<String, String>  params = new HashMap<String, String>();
                String token = ProfileInfo.getToken();
                params.put("Authorization", token);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}