package com.example.myapplication.DonorUiFrontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class DnContentPageAdapter extends RecyclerView.Adapter<DnContentPageHolder> {

    Context context;
    ArrayList<DnContentPageModel> models;
//    private AdapterView.OnItemClickListener listener;
    private ContentPageRecyclerViewClickListener listener;
    public interface ContentPageRecyclerViewClickListener {
        void onClick(View v, int position);
    }


    public DnContentPageAdapter(Context context, ArrayList<DnContentPageModel> models, ContentPageRecyclerViewClickListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DnContentPageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dn_content_page_card, null);
        return new DnContentPageHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DnContentPageHolder holder, int position) {
        holder.proPic.setImageResource(models.get(position).getImg());
        holder.username.setText(models.get(position).getName());
        holder.title.setText(models.get(position).getTitle());
        holder.description.setText(models.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

}
