package com.example.myapplication.ContentPage;

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

public class ContentPageAdapter extends RecyclerView.Adapter<ContentPageHolder> {

    Context context;
    ArrayList<ContentPageModel> models;
//    private AdapterView.OnItemClickListener listener;
    private ContentPageRecyclerViewClickListener listener;
    public interface ContentPageRecyclerViewClickListener {
        void onClick(View v, int position);
    }


    public ContentPageAdapter(Context context, ArrayList<ContentPageModel> models, ContentPageRecyclerViewClickListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContentPageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dn_content_page_card, null);
        return new ContentPageHolder(view, listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ContentPageHolder holder, int position) {
        //holder.proPic.setImageResource(models.get(position).getImg());
        holder.username.setText(models.get(position).getName());
        holder.title.setText(models.get(position).getTitle());
        holder.description.setText(models.get(position).getDescription());
        Long percentage = (models.get(position).getCurrent() * 100 / models.get(position).getGoal());
        holder.progressBar.setProgress(Math.toIntExact(percentage));
        holder.progress.setText(percentage.toString()+"%");
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

}
