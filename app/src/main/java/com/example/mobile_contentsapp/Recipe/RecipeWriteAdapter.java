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

    public interface OnClickListener2 {
        void OnClick(View view, int pos);
    }

    ArrayList<RecipeItem> items;

    OnClickListener mlistener = null;
    OnClickListener2 mLonglistener = null;

    public void setOnClickListener(OnClickListener onClickListener){mlistener = onClickListener;}
    public void setOnClickListener2(OnClickListener2 onLongClickListener){mLonglistener = onLongClickListener;}
    public RecipeWriteAdapter(ArrayList<RecipeItem> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_process,parent,false);
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
        ImageButton imgAddBtn;
        ImageButton deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeEdit = itemView.findViewById(R.id.recipe_edit);
            imgAddBtn = itemView.findViewById(R.id.img_add);
            deleteBtn = itemView.findViewById(R.id.delete);
        }
        public void onBind(RecipeItem item, int position){
            recipeEdit.setText(item.getText());
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
            imgAddBtn.setImageBitmap(item.getBitmap());
            imgAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int pos = position;
                        if (pos != RecyclerView.NO_POSITION){
                            mlistener.OnClick(v,pos, imgAddBtn);
                        }

                    }
            });
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,items.size());
                    int pos = position;
                    if(pos != RecyclerView.NO_POSITION){
                        mLonglistener.OnClick(v,pos);
                    }
                }
            });

        }

    }

}
