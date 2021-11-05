package com.example.myapplication.DonorUiFrontend;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class DnContentPageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView proPic;
    TextView username, title, description, progress;
    ProgressBar progressBar;

//    private AdapterView.OnItemClickListener listener;
    private DnContentPageAdapter.ContentPageRecyclerViewClickListener listener;

    public DnContentPageHolder(@NonNull View itemView, DnContentPageAdapter.ContentPageRecyclerViewClickListener listener) {
        super(itemView);

        this.proPic = itemView.findViewById(R.id.proPic);
        this.username = itemView.findViewById(R.id.homelessUsername);
        this.title = itemView.findViewById(R.id.goalTitle);
        this.description = itemView.findViewById(R.id.gDescription);
        this.progressBar = itemView.findViewById(R.id.progressBar);
        this.progress = itemView.findViewById(R.id.progress);

        this.listener = listener;

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition());
    }
}
