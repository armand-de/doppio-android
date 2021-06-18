package com.example.mobile_contentsapp.Recipe;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeListGet;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Recipe_List_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    ArrayList<RecipeListGet> items;

    public Recipe_List_Adapter(ArrayList<RecipeListGet> items) {
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
            onBind((NormalViewHolder) holder, position);
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

    public void update(){
        notifyDataSetChanged();
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {

    }

    private void onBind(NormalViewHolder holder, int position) {
        RecipeListGet item = items.get(position);
        holder.onBind(item);
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeThumbnail;
        TextView titleText;
        TextView timeText;
        TextView heartText;
        TextView ovenText;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeThumbnail = itemView.findViewById(R.id.recipe_main_img);
            titleText = itemView.findViewById(R.id.title_text);
            timeText = itemView.findViewById(R.id.recipe_list_time);
            heartText = itemView.findViewById(R.id.heart_text);
            ovenText = itemView.findViewById(R.id.oven_text);
        }
        public void onBind(RecipeListGet item){
            titleText.setText(item.getName());
            String img = item.getThumbnail();
            FireBase.firebaseDownlode(itemView.getContext(),img, recipeThumbnail);

            timeText.setText(String.valueOf(item.getTime())+"분");
            heartText.setText(String.valueOf(item.getPreference()));
            if (item.isUseOven()){
                ovenText.setVisibility(View.VISIBLE);
            }else{
                ovenText.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(v.getContext(), RecipeActivity.class);
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
