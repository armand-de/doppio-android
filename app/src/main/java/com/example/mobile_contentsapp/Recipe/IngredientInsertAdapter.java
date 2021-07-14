package com.example.mobile_contentsapp.Recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class IngredientInsertAdapter extends RecyclerView.Adapter<IngredientInsertAdapter.ViewHolder> {
    private ArrayList<IngredientListItem> items;

    public IngredientInsertAdapter(ArrayList<IngredientListItem> items) {
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
        holder.onBind(item,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingretext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             ingretext = itemView.findViewById(R.id.ingre_item);
        }
        public void onBind(IngredientListItem list, int position){
            ingretext.setText(list.getText());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }
}
