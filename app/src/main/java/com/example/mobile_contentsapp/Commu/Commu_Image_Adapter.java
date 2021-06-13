package com.example.mobile_contentsapp.Commu;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_contentsapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Commu_Image_Adapter extends RecyclerView.Adapter<Commu_Image_Adapter.ViewHolder> {

    ArrayList<Commu_Image_Item> items;

    public Commu_Image_Adapter(ArrayList<Commu_Image_Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.commu_image_item,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Commu_Image_Item item = items.get(position);
        holder.onBind(item);
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
        public void onBind(Commu_Image_Item item){
            commuCreateImage.setImageBitmap(item.getImage());
        }

    }
}
