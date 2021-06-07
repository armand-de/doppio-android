package com.example.mobile_contentsapp.Recipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class Recipe_adapter extends RecyclerView.Adapter<Recipe_adapter.ViewHolder> {

    public interface OnClickListener{
        void OnClick(View view, int pos, ImageButton imageButton);
    }

    ArrayList<Recipe_Item> items;

    OnClickListener mlistener = null;

    public void setOnClickListener(OnClickListener onClickListener){mlistener = onClickListener;}
    public Recipe_adapter(ArrayList<Recipe_Item> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe_Item item  = items.get(position);
        holder.onBind(item,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void additem(Recipe_Item item){
        items.add(item);
        notifyDataSetChanged();
    }
    public void changeimg(int position, Bitmap bitmap){
        items.get(position).setBitmap(bitmap);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText recipe_edit;
        ImageButton imgadd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_edit = itemView.findViewById(R.id.recipe_edit);
            imgadd = itemView.findViewById(R.id.img_add);
        }
        public void onBind(Recipe_Item item, int position){
            recipe_edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    item.setText(recipe_edit.getText().toString());
                }
            });
            imgadd.setImageBitmap(item.getBitmap());
            imgadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        int pos = position;
                        if (pos != RecyclerView.NO_POSITION){
                            mlistener.OnClick(v,pos,imgadd);
                        }

                    }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    items.remove(position);
                    notifyDataSetChanged();
                    return false;
                }
            });
        }

    }
}
