package com.example.myapplication.MerchantConversionRequest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.myapplication.MerchantConversionRequest.MerRequestHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerRequestHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MerRequestHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DnContentPageFragment.
     */

    RecyclerView recyclerView;
    MerRequestHistoryAdapter adapter;
    MerRequestHistoryAdapter.MerRequestHistoryRecyclerViewClickListener listener;
    ArrayList<MerRequestHistoryModel> models = new ArrayList<>();


    // TODO: Rename and change types and number of parameters
    public static com.example.myapplication.ContentPage.ContentPageFragment newInstance(String param1, String param2) {
        com.example.myapplication.ContentPage.ContentPageFragment fragment = new com.example.myapplication.ContentPage.ContentPageFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mer_request_history, container, false);
        FragmentActivity activity = getActivity();
        String url = "http://10.0.2.2:8080/conversion-requests/merchant";
        recyclerView = view.findViewById(R.id.req_hist_recycler_view);
        getRequestsFromDb((AppCompatActivity) getActivity(), url, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                setAdapter(activity);
            }
        });

        Switch unprocessedSwitch = (Switch) view.findViewById(R.id.unprocessedSwitch);
        unprocessedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    unprocessedSwitch.setText("Pending");
                    // a simple API to test if we can connect to backend
                    String url = "http://10.0.2.2:8080/pending/conversion-requests/merchant";
                    getRequestsFromDb((AppCompatActivity) activity, url, new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            setAdapter(activity);
                        }
                    });

                } else {
                    unprocessedSwitch.setText("All requests");
                    // a simple API to test if we can connect to backend
                    String url = "http://10.0.2.2:8080/conversion-requests/merchant";
                    getRequestsFromDb((AppCompatActivity) activity, url, new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            setAdapter(activity);
                        }
                    });
                }
            }
        });
        return view;
    }

    private void setAdapter(FragmentActivity activity) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        //setOnClickListener();
        adapter = new MerRequestHistoryAdapter(activity, models, listener);
        recyclerView.setAdapter(adapter);
    }

    public void getRequestsFromDb(AppCompatActivity callingActivity, String url, final VolleyCallBack callBack){

        RequestQueue queue = Volley.newRequestQueue(callingActivity);

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
                            MerRequestHistoryModel model = new MerRequestHistoryModel();
                            model.setAmount(jsonObject.getLong("amount"));
                            model.setStatus(jsonObject.getBoolean("isDone"));
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
