package com.example.mobile_contentsapp.Commu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class CommuImageAdapter extends RecyclerView.Adapter<CommuImageAdapter.ViewHolder> {

    ArrayList<CommuImageItem> items;

    public CommuImageAdapter(ArrayList<CommuImageItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commu_create_image,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommuImageItem item = items.get(position);
        holder.onBind(item,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton commuCreateImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commuCreateImage = itemView.findViewById(R.id.commu_create_image);
        }
        public void onBind(CommuImageItem item, int position){
            commuCreateImage.setImageBitmap(item.getImage());
            commuCreateImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.remove(position);
                }
            });
        }

    }
}
