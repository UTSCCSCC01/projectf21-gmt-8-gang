package com.example.myapplication.OrganizationUiFrontend;

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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.AboutUsFrontend.Aboutus;
import com.example.myapplication.DonorUiFrontend.DnUserProfileEditActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrgUserProfileViewBalanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrgUserProfileViewBalanceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrgUserProfileViewBalanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrgUserProfileViewBalanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrgUserProfileViewBalanceFragment newInstance(String param1, String param2) {
        OrgUserProfileViewBalanceFragment fragment = new OrgUserProfileViewBalanceFragment();
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
        // Inflate the layout for this fragment
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_org_user_profile_view_balance);
        View view = inflater.inflate(R.layout.fragment_org_user_profile_view_balance, container, false);
        FragmentActivity activity = getActivity();

        //Logout button
        final Button button = (Button) view.findViewById(R.id.OrgPfLogoutButtonFrag);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        //botton for about us:
        Button btn_aboutus= (Button) view.findViewById(R.id.orgAboutusBtnFrag);
        btn_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity.getApplicationContext(), Aboutus.class);
                startActivity(i);
            }
        });

        /// Set photo and string and stuff based on nav from prev
        Intent intent = activity.getIntent();
        if(intent.getExtras() != null) {
            String uname = intent.getStringExtra("uname");
            String desc = intent.getStringExtra("desc");
            String base64Pfp = intent.getStringExtra("pfp");

            Bitmap bmp = ProfileInfo.decodeProfilePic(base64Pfp);

            ImageView currentProfilePhoto = (ImageView) view.findViewById(R.id.imageView);
            TextView usernameTextboxInfo = (TextView) view.findViewById(R.id.DnPfUnameDisplay);
            TextView descriptionTextboxInfo = (TextView) view.findViewById(R.id.DnPfUdescDisplay);

            currentProfilePhoto.setImageBitmap(bmp);
            usernameTextboxInfo.setText(uname);
            descriptionTextboxInfo.setText(desc);
        }

        //when db setup
        ProfileInfo profileInf = new ProfileInfo();

        ImageView currentProfilePhoto = (ImageView) view.findViewById(R.id.imageView);
        TextView usernameTextboxInfo = (TextView) view.findViewById(R.id.OrgPfUnameDisplayFrag);
        TextView descriptionTextboxInfo = (TextView) view.findViewById(R.id.OrgPfUdescDisplayFrag);

        TextView balanceTextbookInfo = (TextView)view.findViewById(R.id.OrgPfBalanceFrag);


        profileInf.getInfoFromDb((AppCompatActivity) activity,
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
        final Button EditPfButton = (Button) view.findViewById(R.id.OrgEditPfButtonFrag);

        EditPfButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("hyyyy", "in org we clicked edit button");
                Intent i = new Intent(activity.getApplicationContext(), DnUserProfileEditActivity.class);
                startActivity(i);
            }
        });

//        return inflater.inflate(R.layout.fragment_org_user_profile_view_balance, container, false);
        return view;
    }
}