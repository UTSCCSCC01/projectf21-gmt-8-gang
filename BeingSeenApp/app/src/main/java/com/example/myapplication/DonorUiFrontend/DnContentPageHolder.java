package com.example.myapplication.DonorUiFrontend;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import org.w3c.dom.Text;

public class DnContentPageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView proPic;
    TextView username, title, description;

//    private AdapterView.OnItemClickListener listener;
    private DnContentPageAdapter.ContentPageRecyclerViewClickListener listener;

    public DnContentPageHolder(@NonNull View itemView, DnContentPageAdapter.ContentPageRecyclerViewClickListener listener) {
        super(itemView);

        this.proPic = itemView.findViewById(R.id.proPic);
        this.username = itemView.findViewById(R.id.homelessUsername);
        this.title = itemView.findViewById(R.id.goalTitle);
        this.description = itemView.findViewById(R.id.gDescription);
        this.listener = listener;

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition());
    }
}
