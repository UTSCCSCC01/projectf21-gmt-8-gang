package com.example.myapplication.DonorUiFrontend;

import android.content.Context;
import android.content.Intent;
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
    ItemClickListener itemClickListener;
    //private AdapterView.OnItemClickListener listener;

//    ContentPageRecyclerViewClickListener listener;
//    public interface ContentPageRecyclerViewClickListener {
//        void onClick(View v, int position);
//    }


    public DnContentPageAdapter(Context context, ArrayList<DnContentPageModel> models, ItemClickListener listener) {
        this.context = context;
        this.models = models;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public DnContentPageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dn_content_page_card, null);
        return new DnContentPageHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DnContentPageHolder holder, int position) {
//        Bitmap bmp = ProfileInfo.decodeProfilePic(models.get(position).getImg());
//        holder.proPic.setImageBitmap(bmp);
        //holder.proPic.setImageResource(models.get(position).getImg());
        holder.username.setText(models.get(position).getName());
        holder.title.setText(models.get(position).getTitle());
        holder.progress.setText(models.get(position).getProgress());
        holder.percentage.setText(models.get(position).getPercentage());
        holder.description.setText(models.get(position).getDescription());
        //holder.progressBar.setProgress(Integer.parseInt(models.get(position).getPercentage()));

        holder.setItemClickListener(new ItemClickListener(){
            @Override
            public void onItemClickListener(View view, int position){
//                BitmapDrawable bitmapDrawable = (BitmapDrawable)DnContentPageHolder.proPic.getDrawable();
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] bytes = stream.toByteArray();


                //String gProPic = models.get(position).getImg();
                String gUsername = models.get(position).getName();
                String gTitle = models.get(position).getTitle();
                String gProgress = models.get(position).getProgress();
                String gPercentage = models.get(position).getPercentage();
                String gDescription = models.get(position).getDescription();

                Intent intent = new Intent(context, DnDonationGoalActivity.class);
                //intent.putExtra("proPic", gProPic);
                intent.putExtra("username", gUsername);
                intent.putExtra("title", gTitle);
                intent.putExtra("progress", gProgress);
                intent.putExtra("percentage", gPercentage);
                intent.putExtra("description", gDescription);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

}
