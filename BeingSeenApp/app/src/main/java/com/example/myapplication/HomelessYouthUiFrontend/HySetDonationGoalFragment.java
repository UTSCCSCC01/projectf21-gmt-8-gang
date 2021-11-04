package com.example.myapplication.HomelessYouthUiFrontend;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.DonorUiFrontend.DnContentPageAdapter;
import com.example.myapplication.DonorUiFrontend.DnContentPageModel;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.myapplication.HomelessYouthUiFrontend.HySetDonationGoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HySetDonationGoalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HySetDonationGoalFragment() {
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
    DnContentPageAdapter adapter;
    DnContentPageAdapter.ContentPageRecyclerViewClickListener listener;
    ArrayList<DnContentPageModel> models = new ArrayList<>();


    // TODO: Rename and change types and number of parameters
    public static com.example.myapplication.DonorUiFrontend.DnContentPageFragment newInstance(String param1, String param2) {
        com.example.myapplication.DonorUiFrontend.DnContentPageFragment fragment = new com.example.myapplication.DonorUiFrontend.DnContentPageFragment();
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
        View view = inflater.inflate(R.layout.fragment_dn_content_page, container, false);
        FragmentActivity activity = getActivity();



        return view;
    }

//    private void setAdapter(FragmentActivity activity) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
//
//        setOnClickListener();
//        adapter = new DnContentPageAdapter(activity, models, listener);
//        recyclerView.setAdapter(adapter);
//    }
//
//    HySetDonationGoalModel model;
//    String LOGIN_TAG = HyLoginActivity.LOGIN_TAG;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hy_set_donation_goal);
//
//        model = new HySetDonationGoalModel(this);
//
//        TextClock textClock = (TextClock) findViewById(R.id.date);
//        textClock.setTimeZone("Canada/Eastern");
//        textClock.setFormat12Hour("dd-MMM-yyyy");
//
//        //Publish Button code
//        final Button publish = (Button) findViewById(R.id.publishGoalButton);
//        publish.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                createDonationGoal();
//            }
//        });
//    }
//
//    private void createDonationGoal() {
//        EditText titleField = (EditText) findViewById(R.id.goalTitle);
//        EditText descriptionField = (EditText) findViewById(R.id.goalDescription);
//        EditText moneyField = (EditText) findViewById(R.id.moneyNeeded);
//
//        String title = titleField.getText().toString();
//        String description = descriptionField.getText().toString();
//        String moneyString = moneyField.getText().toString();
//
//        if (title.isEmpty()) {
//            titleField.setError("title cannot be blank");
//            titleField.requestFocus();
//            return;
//        }
//
//        if (description.isEmpty()) {
//            descriptionField.setError("description cannot be blank");
//            descriptionField.requestFocus();
//            return;
//        }
//
//        if (moneyString.isEmpty()) {
//            moneyField.setError("your goal cannot be blank");
//            moneyField.requestFocus();
//            return;
//        }
//
//        Long moneyLong;
//        if (Pattern.matches("[\\d]+", moneyString)) {
//            moneyLong = Long.parseLong(moneyString);
//        } else {
//            moneyField.setError("This is not a number");
//            moneyField.requestFocus();
//            return;
//        }
//
//        if (moneyLong == 0L) {
//            moneyField.setError("your goal cannot be zero");
//            moneyField.requestFocus();
//            return;
//        }
//
//        model.createDonationGoal(title, description, moneyLong);
//        return;
//    }
}
