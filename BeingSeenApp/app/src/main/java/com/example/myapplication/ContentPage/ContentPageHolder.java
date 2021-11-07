package com.example.myapplication.ContentPage;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ContentPageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView proPic;
    TextView username, title, description, progress, percentage;
    ProgressBar progressBar;

//    private AdapterView.OnItemClickListener listener;
    private ContentPageAdapter.ContentPageRecyclerViewClickListener listener;

    public ContentPageHolder(@NonNull View itemView, ContentPageAdapter.ContentPageRecyclerViewClickListener listener) {
        super(itemView);

        //this.proPic = itemView.findViewById(R.id.proPic);
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
