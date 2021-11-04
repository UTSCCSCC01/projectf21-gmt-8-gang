package com.example.myapplication.DonorUiFrontend;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class DnContentPageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    static ImageView proPic;
    TextView username, title, description, progress, percentage;
    ProgressBar progressBar;

    ItemClickListener itemClickListener;
//    private AdapterView.OnItemClickListener listener;
    //DnContentPageAdapter.ContentPageRecyclerViewClickListener itemClickListener;

    public DnContentPageHolder(@NonNull View itemView, ItemClickListener listener) {
        super(itemView);

        //this.proPic = itemView.findViewById(R.id.proPic);
        this.username = itemView.findViewById(R.id.homelessUsername);
        this.title = itemView.findViewById(R.id.goalTitle);
        this.description = itemView.findViewById(R.id.gDescription);
        this.progress = itemView.findViewById(R.id.progress);
        this.percentage = itemView.findViewById(R.id.percentage);
        //this.progressBar = itemView.findViewById(R.id.progressBar);

        this.itemClickListener = listener;

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClickListener(view, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

}
