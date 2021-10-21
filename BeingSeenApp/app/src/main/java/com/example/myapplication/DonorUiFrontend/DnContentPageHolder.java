package com.example.myapplication.DonorUiFrontend;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class DnContentPageHolder extends RecyclerView.ViewHolder {

    ImageView proPic;
    TextView username, title, description;

    private AdapterView.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.listener = listener;
    }


    public DnContentPageHolder(@NonNull View itemView, AdapterView.OnItemClickListener listener) {
        super(itemView);

        this.proPic = itemView.findViewById(R.id.proPic);
        this.username = itemView.findViewById(R.id.homelessUsername);
        this.title = itemView.findViewById(R.id.goalTitle);
        this.description = itemView.findViewById(R.id.gDescription);

//        //makes each card a big button
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener != null){
//                    int position = getAbsoluteAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION){
//                        listener.onItemClick(position);
//                    }
//                }
//            }
//        });

    }
}
