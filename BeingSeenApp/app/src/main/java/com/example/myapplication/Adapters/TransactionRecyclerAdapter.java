package com.example.myapplication.Adapters;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
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
    private List<String> people;
    private List<Long> amounts;
    String userRole;

    public TransactionRecyclerAdapter(List<String> people, List<Long> amounts, String userRole) {
        this.people = people;
        this.amounts = amounts;
        this.userRole = userRole;
    }

    // represents a recycler item
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView peopleField;
        private TextView amountField;
        private TextView noTransactionField;

        public RecyclerViewHolder(final View view) {
            super(view);
            peopleField = view.findViewById(R.id.transactions_receiver_recycler_item);
            amountField = view.findViewById(R.id.transactions_amount_recycler_item);
            noTransactionField = view.findViewById(R.id.no_transaction_recycler_tiem);
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
        if (amounts == null || amounts.size() == 0) {
            if (userRole.equals("DONOR"))
                holder.noTransactionField.setText("You have no donations yet, go help homeless youth!");
            else if (userRole.equals("HOMELESS"))
                holder.noTransactionField.setText("You have no donations yet, try setting up profile!");
            else if (userRole.equals("ORGANIZATION"))
                holder.noTransactionField.setText("haven't set up transaction method for large org yet");
            else if (userRole.equals("MERCHANT")) {
                holder.noTransactionField.setText("No youth has bought your stuff before");
                Log.i("hyyy", "hihi");
            }
            else if (userRole.equals("BEING_SEEN"))
                holder.noTransactionField.setText("No donations received for being seen!");
            holder.peopleField.setText("");
            holder.amountField.setText("");
            return;
        }

        String person = "";
        if (userRole.equals("DONOR") || userRole.equals("ORGANIZATION"))
            person = "To " + people.get(position);             // current receiver
        else if (userRole.equals("MERCHANT") || userRole.equals("BEING_SEEN") || userRole.equals("HOMELESS"))
            person = "From " + people.get(position);            // current sender
        String amount = amounts.get(position).toString() + " Credit(s)";
        holder.peopleField.setText(person);
        holder.amountField.setText(amount);
        holder.noTransactionField.setText("");
    }

    @Override
    public int getItemCount() {
        if (amounts == null || amounts.size() == 0)
            return 1;
        return amounts.size();
    }
}
