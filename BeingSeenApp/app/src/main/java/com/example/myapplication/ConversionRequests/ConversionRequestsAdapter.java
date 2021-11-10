package com.example.myapplication.ConversionRequests;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConversionRequestsAdapter extends RecyclerView.Adapter<ConversionRequestsAdapter.RecyclerViewHolder>{
    //code base on TransactionRecyclerAdapter
    private List<String> amount;
    private List<String> username;
    private List<String> time;
    private List<String> status;

    public ConversionRequestsAdapter(List<String> username, List<String> amount, List<String> time, List<String> status) {
        this.amount = amount;
        this.username=username;
        this.time=time;
        this.status=status;
    }

    // represents a recycler item
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView userField;
        private TextView timeField;
        private TextView amountField;
        private TextView statusField;
        private Button goButton;
        private Context context;

        public RecyclerViewHolder(final View view) {
            super(view);
            context = itemView.getContext();
            userField = view.findViewById(R.id.rq_item_receiver);
            timeField=view.findViewById(R.id.rq__item_time);
            amountField=view.findViewById(R.id.rq_item_amount);
            statusField=view.findViewById(R.id.rq_item_status);
            goButton = view.findViewById(R.id.rq_item_go);

        }

    }

    // creates a new RecyclerViewHolder
    @NonNull
    @Override
    public ConversionRequestsAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_recycler_item, parent, false);
        return new ConversionRequestsAdapter.RecyclerViewHolder(itemView);
    }

    // when binding the view holder to a timeSlot
    @Override
    public void onBindViewHolder(@NonNull ConversionRequestsAdapter.RecyclerViewHolder holder, int position) {
        if (username == null) {
            holder.userField.setText("No more result");
            holder.goButton.setText("X");
            return;
        }
        String user = this.username.get(position);
        holder.userField.setText(user);
        String time=this.time.get(position);
        holder.timeField.setText(time);
        String amount=this.amount.get(position);
        holder.amountField.setText(amount);
        String status=this.status.get(position);
        holder.statusField.setText(status);
        holder.goButton.setText("GO");
        holder.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jump to merchant page with extra "Username"=username
                if(!holder.goButton.getText().equals("X")){
                    Intent i=new Intent(holder.context, RequestDetails.class);
                    try{
                        i.putExtra("receiver",user);
                        i.putExtra("time",time);
                        i.putExtra("amount",amount);
                        i.putExtra("status",status);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    holder.context.startActivity(i);
                }
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

