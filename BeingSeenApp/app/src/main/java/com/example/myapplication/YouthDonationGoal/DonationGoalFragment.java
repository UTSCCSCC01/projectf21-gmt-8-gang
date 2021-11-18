package com.example.myapplication.YouthDonationGoal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.NavbarActivities.YouthMainNavbarActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DonationGoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonationGoalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DonationGoalFragment() {
        // Required empty public constructor
    }

    ImageView proPic;
    TextView usernameField, titleField, descriptionField, progressField, percentageField;
    ProgressBar progressBarField;
    String username, title, description, progress, percentage;
    Long current, goal;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HyDonationGoalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DonationGoalFragment newInstance(String param1, String param2) {
        DonationGoalFragment fragment = new DonationGoalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_goal, container, false);
        FragmentActivity activity = getActivity();

        //this.proPic = view.findViewById(R.id.HyPfPic);
        this.usernameField = view.findViewById(R.id.HyUsername);
        this.titleField = view.findViewById(R.id.HyGoalTitle);
        this.descriptionField = view.findViewById(R.id.HyGoalDescription);
        this.progressField = view.findViewById(R.id.HyAmount);
        this.percentageField = view.findViewById(R.id.HyPercentage);
        this.progressBarField = view.findViewById(R.id.HyProgressBar);
        progressBarField.setProgress(0);

        getDonationGoalFromDb((AppCompatActivity) getActivity(), new VolleyCallBack() {
            @Override
            public void onSuccess() {}
        });

        //        Button for creating donation goal
        final Button setGoal = view.findViewById(R.id.SetGoal);
        setGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity.getApplicationContext(), SetDonationGoalActivity.class);
                startActivity(i);
            }
        });

        //         button for deleting donation goal
        final Button deleteGoal = view.findViewById(R.id.delete_donation_goal);
        deleteGoal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DeleteDonationGoalModel model = new DeleteDonationGoalModel(DonationGoalFragment.this, (AppCompatActivity)activity);
                model.deleteDonationGoal();
//                Toast.makeText(getBaseContext(), "outside delete", Toast.LENGTH_SHORT).show();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void getDonationGoalFromDb(AppCompatActivity callingActivity, final VolleyCallBack callBack){

        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // a simple API to test if we can connect to backend
        String url = "http://10.0.2.2:8080/donationGoal";

        // Request a string response from the provided URL.
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE_VAR", "Response called properly");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            username = jsonObject.getString("username");
                            title = jsonObject.getString("title");
                            description = jsonObject.getString("description");
                            current = jsonObject.getLong("current");
                            goal = jsonObject.getLong("goal");
                            Long per = current * 100 / goal;

                            usernameField.setText(username);
                            titleField.setText(title);
                            descriptionField.setText(description);
                            progressField.setText(current + " / " + goal);
                            percentageField.setText(per.toString() + "%");
                            progressBarField.setProgress(Math.toIntExact(per));
                            progressBarField.setVisibility(View.VISIBLE);


//                            model.setImg(R.drawable.profile);
                            callBack.onSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 404) {
                    usernameField.setText("You don't have a goal now.\nGo create one!");
                    progressBarField.setVisibility(View.GONE);
                } else {
                    Log.i("uh-oh", "something's wrong with internet or your code!");
                }
            }
        }) {
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
        queue.add(jsonObjectRequest);
    }
}