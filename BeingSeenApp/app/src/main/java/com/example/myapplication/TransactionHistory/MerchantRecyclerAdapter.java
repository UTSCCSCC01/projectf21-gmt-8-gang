package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.merchantSearch.MerchantInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MerchantRecyclerAdapter extends RecyclerView.Adapter<MerchantRecyclerAdapter.RecyclerViewHolder>{
//code base on TransactionRecyclerAdapter
    private List<String> profile;
    private List<JSONObject> profileJson;//store json object of profile
    private List<String> username;
    private List<String> nickname;
    private List<String> role;

    public MerchantRecyclerAdapter( List<String> username, List<String> role, List<String> profile) {
        this.profile = profile;
        this.profileJson= new ArrayList<JSONObject>();
        this.username=username;
        this.role=role;
        this.nickname=new ArrayList<String>();
        for(int i=0;i<profile.size();i++){
            try {
                JSONObject pf=new JSONObject(profile.get(i));
                profileJson.add(pf);
                nickname.add(pf.getString("name"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // represents a recycler item
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView userField;
        private TextView nickField;
        private Button goButton;
        private Context context;

        public RecyclerViewHolder(final View view) {
            super(view);
            context = itemView.getContext();
            userField = view.findViewById(R.id.merchant_recycler_username);
            nickField=view.findViewById(R.id.merchant_recycler_nickname);
            goButton = view.findViewById(R.id.merchant_recycler_go);
        }

    }

    // creates a new RecyclerViewHolder
    @NonNull
    @Override
    public MerchantRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_list_item, parent, false);
        return new MerchantRecyclerAdapter.RecyclerViewHolder(itemView);
    }

    // when binding the view holder to a timeSlot
    @Override
    public void onBindViewHolder(@NonNull MerchantRecyclerAdapter.RecyclerViewHolder holder, int position) {
        if (username == null) {
            holder.nickField.setText("No more result");
            holder.goButton.setText("X");
            return;
        }
        String user = username.get(position);
        holder.userField.setText(user);
        if(role.get(position).equals("MERCHANT")){
            String nick=nickname.get(position);
            holder.userField.setText(user);
            holder.nickField.setText(nick);
            holder.goButton.setText("GO");
            holder.goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //jump to merchant page with extra "Username"=username
                    if(!holder.goButton.getText().equals("X")){
                        Intent i=new Intent(holder.context, MerchantInfo.class);
                        try{
                            i.putExtra("Username",user);
                            i.putExtra("name",nick);
                            i.putExtra("bio",profileJson.get(holder.getBindingAdapterPosition()).getString("bio"));
                            i.putExtra("photo",profileJson.get(holder.getBindingAdapterPosition()).getString("photo"));
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        holder.context.startActivity(i);
                    }
                }
            });
        }
        else{
            holder.nickField.setText("Not Merchant");
            holder.goButton.setText("X");
        }
    }

    @Override
    public int getItemCount() {
        if (username == null)
            return 1;
        return username.size();
    }
}