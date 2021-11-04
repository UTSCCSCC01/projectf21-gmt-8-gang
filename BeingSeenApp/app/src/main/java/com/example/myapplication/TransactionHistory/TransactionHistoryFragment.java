package com.example.myapplication.TransactionHistory;

import android.content.Intent;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.DonorUiFrontend.DnContentPageActivity;
import com.example.myapplication.DonorUiFrontend.DnUserProfileEditActivity;
import com.example.myapplication.DonorUiFrontend.DnUserProfileViewBalanceActivity;
import com.example.myapplication.DonorUiFrontend.DnUserProfileViewDonationActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionHistoryFragment newInstance(String param1, String param2) {
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
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
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dn_user_profile_view_donation);

        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        FragmentActivity activity = getActivity();

        recyclerView = view.findViewById(R.id.transaction_history_recycler_view);

        if (recyclerView == null) {
            Log.i("hyyy", "recycler view is null");
        } else {
            Log.i("hyyy", "recycler view is not null");
        }
        //Retrieving donation info from DB
        Transaction transactionInfo = new Transaction();
        transactionInfo.getDnTransactionFromDb((AppCompatActivity) getActivity(),
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        setAdapter(activity);
                    }
                });

        // Inflate the layout for this fragment
        return view;
    }

    private void setAdapter(FragmentActivity activity) {
        //fetch list of receivers and amounts from transaction DB
        List<String> receivers = Transaction.getReceivers();
        List<Long> amounts = Transaction.getAmounts();

        String userRole = ProfileInfo.getUserRole().substring(5);
        Log.i("hyyy", userRole);
        TransactionRecyclerAdapter adapter = new TransactionRecyclerAdapter(receivers, amounts, userRole);
        // sets the layout, default animator, and adapter of recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}