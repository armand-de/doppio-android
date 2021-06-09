package com.example.mobile_contentsapp.Recipe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import static android.content.ContentValues.TAG;

public class Recipe_List_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    ArrayList<Recipe_List_Get> items;

    public Recipe_List_Adapter(ArrayList<Recipe_List_Get> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
            return new NormalViewHolder(vh);
        } else {
            View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.roding_item, parent, false);
            return new LoadingViewHolder(vh);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NormalViewHolder){
            onBindview((NormalViewHolder) holder, position);
        }else if (holder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {

    }

    private void onBindview(NormalViewHolder holder, int position) {
        Recipe_List_Get item = items.get(position);
        holder.onBind(item);
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        ImageView recipe_main_img;
        TextView title_text;
        TextView time_text;
        TextView heart_text;
        TextView oven_text;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_main_img = itemView.findViewById(R.id.recipe_main_img);
            title_text = itemView.findViewById(R.id.title_text);
            time_text = itemView.findViewById(R.id.recipe_list_time);
            heart_text = itemView.findViewById(R.id.heart_text);
            oven_text = itemView.findViewById(R.id.oven_text);
        }
        public void onBind(Recipe_List_Get item){
            title_text.setText(item.getName());
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://mobile-contents-812ea.appspot.com");
            StorageReference storageReference = storage.getReference();
            String img = item.getThumbnail();
            storageReference.child("images/34af76ed-a6dc-48ad-a93d-1b50bc3deee3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(itemView.getContext()).load(uri).into(recipe_main_img);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });

            time_text.setText(String.valueOf(item.getTime()));
            heart_text.setText(String.valueOf(item.getPreference()));
            if (item.isUseOven()){
                oven_text.setVisibility(View.VISIBLE);
            }else{
                oven_text.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(v.getContext(), Recipe_Activity.class);
                    Log.d(TAG, "onClick: "+item.getId());
                    intent.putExtra("recipeId",item.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
