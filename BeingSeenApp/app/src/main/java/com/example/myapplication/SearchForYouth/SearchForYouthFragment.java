package com.example.myapplication.SearchForYouth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchForYouthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchForYouthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchForYouthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchForYouthFragment newInstance(String param1, String param2) {
        SearchForYouthFragment fragment = new SearchForYouthFragment();
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

        View view = inflater.inflate(R.layout.fragment_search_for_youth, container, false);
        FragmentActivity activity = getActivity();

        TextInputEditText input= (TextInputEditText) view.findViewById(R.id.searchInput);
        ImageButton search=(ImageButton) view.findViewById(R.id.SearchUserName);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String target=input.getText().toString();
                if(target.equals("")){
                    new AlertDialog.Builder(activity)
                            .setTitle("Invalid Username")
                            .setMessage("Please enter the Username")
                            .setPositiveButton("Confirm",null)
                            .show();
                }
                //else if(Username not found in database) pop an alert like^^^
                else{
                    Intent intent=new Intent();
                    intent.setClass(activity, ShowYouthListActivity.class);
                    intent.putExtra("Username", target);
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }
        });
        return view;
    }
}