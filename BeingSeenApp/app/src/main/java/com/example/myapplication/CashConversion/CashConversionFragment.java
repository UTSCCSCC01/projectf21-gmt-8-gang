package com.example.myapplication.CashConversion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CashConversionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CashConversionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CashConversionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CashConversion.
     */
    // TODO: Rename and change types and number of parameters
    public static CashConversionFragment newInstance(String param1, String param2) {
        CashConversionFragment fragment = new CashConversionFragment();
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

    CashConversionRequestModel model;
    Boolean isLoading = false;
    public static final String CONVERT_TAG = "cashConvert";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_conversion, container, false);
        FragmentActivity activity = getActivity();

        model = new CashConversionRequestModel((AppCompatActivity) activity);
        hideLoadingScreen(view);

        Button confirm = view.findViewById(R.id.convertConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                cashConvert(view);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void cashConvert(View view){

        TextInputEditText amountField = getActivity().findViewById(R.id.amount_convert);
        String amountString = amountField.getText().toString();
        TextInputEditText emailField = getActivity().findViewById(R.id.email_convert);
        String emailString = emailField.getText().toString();
        long amount = Long.parseLong(amountString);

        if (amountString.isEmpty()) {
            amountField.setError("please enter an amount");
            amountField.requestFocus();
            return;
        }

        if (emailString.isEmpty()) {
            emailField.setError("please enter an amount");
            emailField.requestFocus();
            return;
        }

        if(amount == 0){
            amountField.setError("amount cannot be 0");
            amountField.requestFocus();
            return;
        }
        showLoadingScreen();
        model.cashConvert(emailString, amount);
        model.updateBalance(amount);

        return;
    }

    private void showLoadingScreen(){
        getActivity().findViewById(R.id.merBottomNavigationView).setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        getActivity().findViewById(R.id.textView).setVisibility(View.GONE);
        getActivity().findViewById(R.id.aboutus_txt).setVisibility(View.GONE);
        getActivity().findViewById(R.id.email_convert_frame).setVisibility(View.GONE);
        getActivity().findViewById(R.id.email_convert).setVisibility(View.GONE);
        getActivity().findViewById(R.id.amount_convert).setVisibility(View.GONE);
        getActivity().findViewById(R.id.amount_convert_frame).setVisibility(View.GONE);
        getActivity().findViewById(R.id.convertConfirm).setVisibility(View.GONE);
        LottieAnimationView lottieAnimationView = getActivity().findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
    }

    private void hideLoadingScreen(View view) {
        view.findViewById(R.id.textView).setVisibility(View.VISIBLE);
        view.findViewById(R.id.aboutus_txt).setVisibility(View.VISIBLE);
        view.findViewById(R.id.email_convert_frame).setVisibility(View.VISIBLE);
        view.findViewById(R.id.email_convert).setVisibility(View.VISIBLE);
        view.findViewById(R.id.amount_convert).setVisibility(View.VISIBLE);
        view.findViewById(R.id.amount_convert_frame).setVisibility(View.VISIBLE);
        view.findViewById(R.id.convertConfirm).setVisibility(View.VISIBLE);
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.loading_lottie_animation_view);
        lottieAnimationView.setVisibility(View.GONE);
        lottieAnimationView.pauseAnimation();
    }

}