package com.example.myapplication.MerchantConversionRequest;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class MerRequestHistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView amount, status;

    //    private AdapterView.OnItemClickListener listener;
    private MerRequestHistoryAdapter.MerRequestHistoryRecyclerViewClickListener listener;

    public MerRequestHistoryHolder(@NonNull View itemView, MerRequestHistoryAdapter.MerRequestHistoryRecyclerViewClickListener listener) {
        super(itemView);

        this.amount = itemView.findViewById(R.id.merAmount);
        this.status = itemView.findViewById(R.id.merStatus);

        this.listener = listener;

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition());
    }
}

