package com.example.mobile_contentsapp.Recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class RecipeSeeMoreOrderAdapter extends RecyclerView.Adapter<RecipeSeeMoreOrderAdapter.ViewHolder> {
    ArrayList<RecipeSeeMoreOrderItem> items;

    public RecipeSeeMoreOrderAdapter(ArrayList<RecipeSeeMoreOrderItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_see_more_order,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeSeeMoreOrderItem item = items.get(position);
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
        public void onBind(RecipeSeeMoreOrderItem item){
            if (item.getImage().isEmpty()){
                imageView.setVisibility(View.GONE);
            }
            if (!item.getText().isEmpty()){
                textView.setText(item.getText());
            }
            FireBase.firebaseDownlode(itemView.getContext(),item.getImage(),imageView);
        }
    }
}
