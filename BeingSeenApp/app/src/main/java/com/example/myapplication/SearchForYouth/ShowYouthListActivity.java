package com.example.myapplication.SearchForYouth;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowYouthListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<JSONObject> result;
    Boolean isLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_youth_list);
        String username=getIntent().getStringExtra("Username");
        TextView inputText=(TextView) findViewById(R.id.ml_ititle);
        inputText.setText("Search Result of   " + username);
        recyclerView = findViewById(R.id.ml_list);

        // loading animation
        getSupportActionBar().hide();
        inputText.setVisibility(View.GONE);
        TextView titleField = findViewById(R.id.ml_ititle);
        titleField.setVisibility(View.GONE);
        TextView resultField = findViewById(R.id.no_result_title);
        resultField.setVisibility(View.GONE);
        TextView resultExplanationField = findViewById(R.id.no_result_explanation);
        resultExplanationField.setVisibility(View.GONE);
        RelativeLayout resultTitleLayout = findViewById(R.id.youth_result_layout);
        resultTitleLayout.setVisibility(View.GONE);
        LottieAnimationView lottieAnimationViewOfSearch = findViewById(R.id.no_search_result_lottie_animation_view);
        lottieAnimationViewOfSearch.setVisibility(View.GONE);
        LottieAnimationView lottieAnimationView = findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.playAnimation();
        isLoading = true;

        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProfileInfo profileInfo=new ProfileInfo();
        profileInfo.setSearchIdName(username);
        profileInfo.searchUser(this, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                result=profileInfo.getSearchResult();
                if (result.isEmpty()) {
                    setNoResult();
                } else {
                    setAdapter();
                }
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

    @Override public void finish() {
        if(isLoading)return;
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void setAdapter() {
        //fetch list of result's username, role, and profile from DB
        List<String> profile = new ArrayList<String>();
        List<String> username = new ArrayList<String>();
        List<String> role = new ArrayList<String>();
        try {
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).getString("role").equals("HOMELESS")) {
                    profile.add(result.get(i).getString("profileInfo"));
                    username.add(result.get(i).getString("userName"));
                    role.add(result.get(i).getString("role"));
                }
            }
            Log.i("here", username.toString());
            if (profile.isEmpty()) {
                setNoResult();
                return;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // disable loading animation
        isLoading = false;
        getSupportActionBar().show();
        RelativeLayout resultTitleLayout = findViewById(R.id.youth_result_layout);
        resultTitleLayout.setVisibility(View.VISIBLE);
        TextView titleField = findViewById(R.id.ml_ititle);
        titleField.setVisibility(View.VISIBLE);
        LottieAnimationView lottieAnimationView = findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.setVisibility(View.GONE);

        // set up adapter
        YouthListRecyclerAdapter adapter = new YouthListRecyclerAdapter(this, username, role, profile);
        // sets the layout, default animator, and adapter of recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutAnimation(recyclerView.getLayoutAnimation());
        recyclerView.setAdapter(adapter);
    }

    private void setNoResult() {
        // disable loading animation
        isLoading = false;
        getSupportActionBar().show();
        RelativeLayout resultTitleLayout = findViewById(R.id.youth_result_layout);
        resultTitleLayout.setVisibility(View.VISIBLE);
        TextView titleField = (TextView)findViewById(R.id.ml_ititle);
        titleField.setVisibility(View.VISIBLE);
        TextView resultField=(TextView) findViewById(R.id.no_result_title);
        resultField.setVisibility(View.VISIBLE);
        TextView resultExplanationField = findViewById(R.id.no_result_explanation);
        resultExplanationField.setVisibility(View.VISIBLE);
        LottieAnimationView lottieAnimationView = findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.setVisibility(View.GONE);
        LottieAnimationView lottieAnimationViewOfSearch = findViewById(R.id.no_search_result_lottie_animation_view);
        lottieAnimationViewOfSearch.setVisibility(View.VISIBLE);
    }
}
