package com.example.mobile_contentsapp.Profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.Profile.Retrofit.RecipeDeleteClient;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.RecipeSeeMore;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeFindClient;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeFindGet;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeListGet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;
import static com.example.mobile_contentsapp.Main.SplashActivity.userId;

public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.ViewHolder> {

    ArrayList<RecipeListGet> items;

    public interface OnLongClickListener{
        void OnClick(View view, int pos, int id);
    }
    OnLongClickListener mlistener = null;


    public void setOnClickListener(OnLongClickListener onLongClickListener){mlistener = onLongClickListener;}

    public MyRecipeAdapter(ArrayList<RecipeListGet> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeListGet item = items.get(position);
        holder.onBind(item,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        String id;
        ImageView recipe_main_img;
        TextView title_text;
        TextView time_text;
        TextView heart_text;
        TextView oven_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_main_img = itemView.findViewById(R.id.recipe_main_img);
            title_text = itemView.findViewById(R.id.title_text);
            time_text = itemView.findViewById(R.id.recipe_list_time);
            heart_text = itemView.findViewById(R.id.heart_text);
            oven_text = itemView.findViewById(R.id.oven_text);
        }
        public void onBind(RecipeListGet item, int position){
            title_text.setText(item.getName());
            FireBase.firebaseDownlode(itemView.getContext(),item.getThumbnail(),recipe_main_img);
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
                    Intent intent = new Intent(v.getContext(), RecipeSeeMore.class);
                    intent.putExtra("recipeId",item.getId());
                    context.startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = position;
                    if (pos != RecyclerView.NO_POSITION){
                        mlistener.OnClick(v,pos,item.getId());
                    }
                    return false;
                }
            });
        }

    }
}
