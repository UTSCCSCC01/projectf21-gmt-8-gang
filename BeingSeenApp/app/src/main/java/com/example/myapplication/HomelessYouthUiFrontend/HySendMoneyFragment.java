package com.example.myapplication.HomelessYouthUiFrontend;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.VolleyCallBack;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HySendMoneyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HySendMoneyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HySendMoneyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HySendMoneyFragment.
     */


    // TODO: Rename and change types and number of parameters
    public static HySendMoneyFragment newInstance(String param1, String param2) {
        HySendMoneyFragment fragment = new HySendMoneyFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_merchant_page, container, false);
        FragmentActivity activity = getActivity();

        final Button donateButton = view.findViewById(R.id.donateConfirm);

        donateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText sendingTo = (EditText) getActivity().findViewById(R.id.donateUsername);

                // After clicking save all objects on screen into a object
                EditText amount = (EditText) getActivity().findViewById(R.id.dnDonoAmt);

                Transaction transactionObj = new Transaction();


                String receiver = "";
                receiver = sendingTo.getText().toString();
                String comment = "";
                Long amt = 0L;
                if(!amount.getText().toString().equals("")) {
                    amt = Long.parseLong(amount.getText().toString());
                }

                if (receiver.equals("")) {
                    Log.i("TRYNA", "HI");
                    sendingTo.setError("Please enter a username");
                    sendingTo.requestFocus();
                }

                if (amt == null || amt <= 0) {
                    amount.setError("Please enter a nonzero amount to transfer");
                    amount.requestFocus();
                }

                if (  !( (amt == null || amt <= 0) || (receiver.isEmpty()) )  ) {
                    //call transaction in db
                    transactionObj.makeDnDonationTransaction(receiver, comment, amt, (AppCompatActivity) activity,
                            new VolleyCallBack() {
                                @Override
                                public void onSuccess() {

                                    //make toast after success
                            Toast toast = Toast.makeText(activity.getApplicationContext(), "Transaction successful", Toast.LENGTH_LONG);
                            toast.show();

                                    Intent i = new Intent(activity.getApplicationContext(), HyMainNavbarActivity.class);
                                    startActivity(i);
                                }

                                @Override
                                public void onFailure() {

                                    //make toast after fail
                            Toast toast = Toast.makeText(activity.getApplicationContext(),
                                    "Transaction Failed: Make sure the user has a merchant account", Toast.LENGTH_LONG);
                            toast.show();

                                    Intent i = new Intent(activity.getApplicationContext(), HyMainNavbarActivity.class);
                                    startActivity(i);
                                }
                            });
                }
            }
        });

        //quit donate screen
        final Button exitDonate = view.findViewById(R.id.DnDonateBackEditButton);

        exitDonate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity.getApplicationContext(), HyMainNavbarActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}
