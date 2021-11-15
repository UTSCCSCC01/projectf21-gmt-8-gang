package com.example.myapplication.MerchantConversionRequest;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MerRequestHistoryAdapter extends RecyclerView.Adapter<MerRequestHistoryHolder> {

    Context context;
    ArrayList<MerRequestHistoryModel> models;

    private MerRequestHistoryRecyclerViewClickListener listener;
    public interface MerRequestHistoryRecyclerViewClickListener {
        void onClick(View v, int position);
    }


    public MerRequestHistoryAdapter(Context context, ArrayList<MerRequestHistoryModel> models, MerRequestHistoryAdapter.MerRequestHistoryRecyclerViewClickListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MerRequestHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_history_recycler_item, null);
        return new MerRequestHistoryHolder(view, listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MerRequestHistoryHolder holder, int position) {
        //holder.proPic.setImageResource(models.get(position).getImg());
        holder.username.setText(models.get(position).getUsername());
        holder.amount.setText(models.get(position).getAmount().toString());
        if (models.get(position).getStatus()==true){
            holder.status.setText("Processed");
        }else{
            holder.status.setText("Money not converted. Please wait.");
        }
//        holder.status.setText(models.get(position).getStatus().toString());
//        Long percentage = (models.get(position).getCurrent() * 100 / models.get(position).getGoal());
//        holder.progressBar.setProgress(Math.toIntExact(percentage));
//        holder.progress.setText(percentage.toString()+"%");
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

}


