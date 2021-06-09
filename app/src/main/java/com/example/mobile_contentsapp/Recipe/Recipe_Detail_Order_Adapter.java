package com.example.mobile_contentsapp.Recipe;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_contentsapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Recipe_Detail_Order_Adapter extends RecyclerView.Adapter<Recipe_Detail_Order_Adapter.ViewHolder> {
    ArrayList<Recipe_detail_order_item> items;

    public Recipe_Detail_Order_Adapter(ArrayList<Recipe_detail_order_item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_order_item,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe_detail_order_item item = items.get(position);
        holder.onBind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recipe_detail_img);
            textView = itemView.findViewById(R.id.recipe_detail_text);
        }
        public void onBind(Recipe_detail_order_item item){
            if (item.getImg().isEmpty()){
                imageView.setVisibility(View.GONE);
            }
            if (!item.getText().isEmpty()){
                textView.setText(item.getText());
            }
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://mobile-contents-812ea.appspot.com");
            StorageReference storageReference = storage.getReference();
            if (!item.getImg().isEmpty()){
                storageReference.child(item.getImg()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(itemView.getContext()).load(uri).into(imageView);
                    }
                });
            }

        }
    }
}
