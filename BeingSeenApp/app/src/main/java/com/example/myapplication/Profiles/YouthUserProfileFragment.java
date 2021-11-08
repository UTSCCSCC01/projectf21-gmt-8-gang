package com.example.myapplication.Profiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.YouthDonationGoal.SetDonationGoalModel;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.SignUpAndLogin.SignUpActivity;
import com.example.myapplication.VolleyCallBack;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YouthUserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YouthUserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public YouthUserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HyUserProfileViewBalanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YouthUserProfileFragment newInstance(String param1, String param2) {
        YouthUserProfileFragment fragment = new YouthUserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dn_user_profile_view_balance);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youth_user_profile, container, false);
        FragmentActivity activity = getActivity();

        // if we returned from HySetDonationGoalModel, create toast message accordingly
        Intent intent = activity.getIntent();
        if (intent.hasExtra("toast")) {
            if (intent.getStringExtra("toast").equals(SetDonationGoalModel.CREATE_GOAL_SUCCESS)) {
                Toast.makeText(activity, "created donation goal", Toast.LENGTH_LONG).show();
            } else if (intent.getStringExtra("toast").equals(SetDonationGoalModel.CREATE_GOAL_ERROR)) {
                Toast.makeText(activity, "Couldn't connect", Toast.LENGTH_LONG).show();
            } else if (intent.getStringExtra("toast").equals(SetDonationGoalModel.CREATE_GOAL_DUPLICATE)) {
                Toast.makeText(activity, "You already have a goal!", Toast.LENGTH_LONG).show();
            }
        }
        //Temporarily comment this out since this toast always shows up as a bug after transitioning nav bar
        //Temporarily comment this out since this toast always shows up as a bug after transitioning nav bar

        //Logout button
        final Button button = (Button) view.findViewById(R.id.HyPfLogoutButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity.getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });


        /// Set photo and string and stuff based on nav from prev
//        Intent intent = activity.getIntent(); // already did this!
        if(intent.getExtras() != null && intent.hasExtra("uname")) {
            String uname = intent.getStringExtra("uname");
            String desc = intent.getStringExtra("desc");
            String base64Pfp = intent.getStringExtra("pfp");

            Bitmap bmp = ProfileInfo.decodeProfilePic(base64Pfp);

            ImageView currentProfilePhoto = (ImageView) view.findViewById(R.id.HyPfPfpDisplay);
            TextView usernameTextboxInfo = (TextView) view.findViewById(R.id.HyPfUnameDisplay);
            TextView descriptionTextboxInfo = (TextView) view.findViewById(R.id.HyPfUdescDisplay);

            currentProfilePhoto.setImageBitmap(bmp);
            usernameTextboxInfo.setText(uname);
            descriptionTextboxInfo.setText(desc);
        }




        //when db setup
        ProfileInfo profileInf = new ProfileInfo();

        ImageView currentProfilePhoto = (ImageView) view.findViewById(R.id.HyPfPfpDisplay);
        TextView usernameTextboxInfo = (TextView) view.findViewById(R.id.HyPfUnameDisplay);
        TextView descriptionTextboxInfo = (TextView) view.findViewById(R.id.HyPfUdescDisplay);

        TextView balanceTextbookInfo = (TextView) view.findViewById(R.id.hyPfBalance);


        profileInf.getInfoFromDb(((AppCompatActivity) activity),
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.d("RESPONSE_VAR_AFTER", "DN Username received as " + profileInf.getUsername());

                        usernameTextboxInfo.setText(profileInf.getUsername());
                        descriptionTextboxInfo.setText(profileInf.getUserDescription());
                        currentProfilePhoto.setImageBitmap(ProfileInfo.decodeProfilePic(profileInf.getProfileImage()));
                        balanceTextbookInfo.setText(profileInf.getBalance());

                    }
                });

        //Edit profile
        final Button EditPfButton = (Button) view.findViewById(R.id.HyEditPfButton);

        EditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity.getApplicationContext(), ProfileEditActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}
