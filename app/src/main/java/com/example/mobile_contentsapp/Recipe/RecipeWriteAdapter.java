package com.example.mobile_contentsapp.Recipe;

import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class RecipeWriteAdapter extends RecyclerView.Adapter<RecipeWriteAdapter.ViewHolder> {

    public interface OnClickListener{
        void OnClick(View view, int pos, ImageButton imageButton);
    }

    public interface OnLongClickListener{
        void OnLongClick(View view, int pos);
    }

    ArrayList<RecipeItem> items;

    OnClickListener mlistener = null;
    OnLongClickListener mLonglistener = null;

    public void setOnClickListener(OnClickListener onClickListener){mlistener = onClickListener;}
    public void setOnLongClickListener(OnLongClickListener onLongClickListener){mLonglistener = onLongClickListener;}
    public RecipeWriteAdapter(ArrayList<RecipeItem> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_process_item,parent,false);
        return new ViewHolder(vh);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeItem item = items.get(position);
        holder.onBind(item,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void changeimg(int position, Bitmap bitmap){
        items.get(position).setBitmap(bitmap);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText recipeEdit;
        ImageButton imgAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeEdit = itemView.findViewById(R.id.recipe_edit);
            imgAdd = itemView.findViewById(R.id.img_add);
        }
        public void onBind(RecipeItem item, int position){
            recipeEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    item.setText(recipeEdit.getText().toString());
                }
            });
            imgAdd.setImageBitmap(item.getBitmap());
            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        int pos = position;
                        if (pos != RecyclerView.NO_POSITION){
                            mlistener.OnClick(v,pos, imgAdd);
                        }

                    }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    items.remove(position);
                    notifyDataSetChanged();
                    int pos = position;
                    if(pos != RecyclerView.NO_POSITION){
                        mLonglistener.OnLongClick(v,pos);
                    }
                    return false;
                }
            });
        }

    }

}
