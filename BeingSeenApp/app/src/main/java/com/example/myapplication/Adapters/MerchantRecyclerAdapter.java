package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class MerchantRecyclerAdapter extends RecyclerView.Adapter<MerchantRecyclerAdapter.RecyclerViewHolder>{
//code base on TransactionRecyclerAdapter
    private List<String> nickName;
    private List<String> username;

    public MerchantRecyclerAdapter(List<String> nickName, List<String> username) {
        this.nickName = nickName;
        this.username = username;
    }

    // represents a recycler item
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView nickField;
        private TextView userField;
        private Button goButton;

        public RecyclerViewHolder(final View view) {
            super(view);
            nickField = view.findViewById(R.id.merchant_recycler_nickname);
            userField = view.findViewById(R.id.merchant_recycler_username);
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
            holder.userField.setText("");
            holder.goButton.setText("X");
            return;
        }
        String nick=nickName.get(position);
        String user = username.get(position);
        holder.nickField.setText(nick);
        holder.userField.setText(user);
        holder.goButton.setText("GO");
        holder.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jump to merchant page with extra "Username"=username
            }
        });
    }

    @Override
    public int getItemCount() {
        if (username == null)
            return 1;
        return username.size();
    }
}