package com.example.myapplication.Adapters;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.RecyclerViewHolder> {

    // we still need this even if schecule.timeSlots is type ArrayList because otherwise the
    // program will crash if the list is too long and we have booked items out of view (when scrolling)
    private ArrayList<String> receivers;
    private ArrayList<Long> amounts;

    public TransactionRecyclerAdapter(ArrayList<String> receivers, ArrayList<Long> amounts) {
        this.receivers = receivers;
        this.amounts = amounts;
    }

    // represents a recycler item
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView receiverField;
        private TextView amountField;

        public RecyclerViewHolder(final View view) {
            super(view);
            receiverField = view.findViewById(R.id.transactions_receiver_recycler_item);
            amountField = view.findViewById(R.id.transactions_amount_recycler_item);
            // we can set click-related stuff here
        }
    }

    // creates a new RecyclerViewHolder
    @NonNull
    @Override
    public TransactionRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_recycler_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    // when binding the view holder to a timeSlot
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String receiver = "To " + receivers.get(position);             // current receiver
        String amount = amounts.get(position).toString() + " Credit(s)";
        holder.receiverField.setText(receiver);
        holder.amountField.setText(amount);
    }

    @Override
    public int getItemCount() {
        return amounts.size();
    }
}
