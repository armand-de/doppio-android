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
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_List_Get;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Recipe_List_Adapter extends RecyclerView.Adapter<Recipe_List_Adapter.ViewHolder> {
    ArrayList<Recipe_List_Get> items;

    public Recipe_List_Adapter(ArrayList<Recipe_List_Get> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe_List_Get item = items.get(position);
        holder.onBind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recipe_main_img;
        TextView title_text;
        TextView time_text;
        TextView heart_text;
        TextView oven_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_main_img = itemView.findViewById(R.id.recipe_main_img);
            title_text = itemView.findViewById(R.id.title_text);
            time_text = itemView.findViewById(R.id.time_text);
            heart_text = itemView.findViewById(R.id.heart_text);
            oven_text = itemView.findViewById(R.id.oven_text);
        }
        public void onBind(Recipe_List_Get item){
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://mobile-contents-812ea.appspot.com");
            StorageReference storageReference = storage.getReference();
            storageReference.child(item.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(itemView.getContext()).load(uri).into(recipe_main_img);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
            title_text.setText(item.getName());
            time_text.setText(item.getTime());
            heart_text.setText(item.getPreference());
            if (item.getUseoven()){
                oven_text.setVisibility(View.VISIBLE);
            }else{
                oven_text.setVisibility(View.INVISIBLE);
            }
        }
    }
}
