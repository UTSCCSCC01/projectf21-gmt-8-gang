package com.example.myapplication.ConversionRequests;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.myapplication.ContentPage.ContentPageAdapter;
import com.example.myapplication.ContentPage.ContentPageFragment;
import com.example.myapplication.ContentPage.ContentPageModel;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.SearchForMerchant.MerchantListRecyclerAdapter;
import com.example.myapplication.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConversionRequestHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConversionRequestHistoryFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConversionRequestHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConversionRequestHistoryFragment.
     */
    RecyclerView recyclerView;
    List<String> username;
    List<String> emails;
    List<String> amount;
    List<String> status;
    List<String> requestIds;


    // TODO: Rename and change types and number of parameters
    public static ConversionRequestHistoryFragment newInstance(String param1, String param2) {
        ConversionRequestHistoryFragment fragment = new ConversionRequestHistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_conversion_requests_history, container, false);
        FragmentActivity activity = getActivity();
        recyclerView=view.findViewById(R.id.rq_recycler_view);
        getRequestsFromDbAndSetAdapter((AppCompatActivity) getActivity(), new VolleyCallBack() {
            @Override
            public void onSuccess() {
                setAdapter(activity);
            }
        });

        return view;
    }

//TODO: not connect to db yet. Finish and test this function before calling

    public void getRequestsFromDbAndSetAdapter(AppCompatActivity callingActivity, final VolleyCallBack callBack){
        RequestQueue queue = Volley.newRequestQueue(callingActivity);

        // API to get all conversion requests made
        String url = "http://10.0.2.2:8080/conversion-requests";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    JSONObject jsonItem;
                    List<String> amount=new ArrayList<String>();
                    List<String> username=new ArrayList<String>();
                    List<String> status=new ArrayList<String>();
                    List<String> requestIds=new ArrayList<String>();
                    List<String> emails=new ArrayList<String>();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            username.add(jsonObject.getString("username"));
                            amount.add(jsonObject.getString("amount"));
                            status.add(jsonObject.getString("isDone"));
                            requestIds.add(jsonObject.getString("requestId"));
                            emails.add(jsonObject.getString("email"));
                        }
                        this.amount=amount;
                        this.username=username;
                        this.status=status;
                        this.requestIds=requestIds;
                        this.emails=emails;
                        callBack.onSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            if (error.networkResponse.statusCode == 404) {
                Log.i("supp", "we dont have any request, handle this later");
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

    private void setAdapter(FragmentActivity activity) {
        // if data is null then return?
        ConversionRequestsAdapter adapter = new ConversionRequestsAdapter(username, amount, status, requestIds, emails);
        // sets the layout, default animator, and adapter of recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
