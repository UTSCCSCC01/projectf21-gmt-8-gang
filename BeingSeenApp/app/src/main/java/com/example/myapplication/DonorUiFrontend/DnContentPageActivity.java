package com.example.myapplication.DonorUiFrontend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DnContentPageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DnContentPageAdapter adapter;
    ItemClickListener itemClickListener;
    //private DnContentPageAdapter.ContentPageRecyclerViewClickListener listener;
    ArrayList<DnContentPageModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_content_page);

        recyclerView = findViewById(R.id.recyclerView);
        getAllDonationGoalsFromDbAndSetAdapter(this, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        adapter = new DnContentPageAdapter(this, models, itemClickListener);
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
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

                            int target = jsonObject.getInt("goal");
                            int curr = jsonObject.getInt("current");
                            model.setProgress(curr + "/" + target);

                            DecimalFormat df = new DecimalFormat("#.#");
                            double percentage = ((double) curr) / ((double) target) * 100;
                            model.setPercentage(df.format(percentage) + "%");

                            model.setDescription(jsonObject.getString("description"));
                            model.setImg(R.drawable.profile);
                            //model.setImg(DnContentPageHolder.proPic);
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