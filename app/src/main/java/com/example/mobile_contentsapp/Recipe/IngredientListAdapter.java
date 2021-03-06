package com.example.mobile_contentsapp.Recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {
    private ArrayList<IngredientListItem> items;

    public IngredientListAdapter(ArrayList<IngredientListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientListItem item = items.get(position);
        holder.onBind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ingreText;
        private ImageView ingreDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingreText = itemView.findViewById(R.id.ingre_item);
            ingreDelete = itemView.findViewById(R.id.delete_mark);
        }
        public void onBind(IngredientListItem list){
            ingreDelete.setVisibility(View.GONE);
            ingreText.setText(list.getText());
        }
    }
}
