package com.example.mobile_contentsapp.Recipe;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class IngreList_adapter extends RecyclerView.Adapter<IngreList_adapter.ViewHolder> {
    private ArrayList<ingreList_Item> items;

    public IngreList_adapter(ArrayList<ingreList_Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingre_item,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ingreList_Item item = items.get(position);
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
        public void onBind(ingreList_Item list, int position){
            ingretext.setText(list.getText());
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    items.remove(position);
                    return false;
                }
            });
        }
    }
}
