package com.example.mobile_contentsapp.Commu;

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

import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CommuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    ArrayList<CommuListGet> items;

    public CommuListAdapter(ArrayList<CommuListGet> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commu_list, parent, false);
            return new NormalViewHolder(vh);
        } else {
            View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(vh);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NormalViewHolder){
            onBind((NormalViewHolder) holder,position);
        }else if (holder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    public void onBind(NormalViewHolder holder, int postion){
        CommuListGet item = items.get(postion);
        holder.onBind(item);
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {

    }
    public class NormalViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView name;
        private TextView heartText;
        private ImageView image;
        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.commu_list_title);
            name = itemView.findViewById(R.id.commu_list_profile);
            heartText = itemView.findViewById(R.id.commu_list_heart_text);
            image = itemView.findViewById(R.id.commu_image);

        }
        public void onBind(CommuListGet item){
            title.setText(item.getTitle());
            name.setText(item.getUser().getNickname());
            heartText.setText(String.valueOf(item.getPreference()));
            String[] imagename = item.getImage().split("\\|");
            if (!item.getImage().isEmpty()){
                image.setVisibility(View.VISIBLE);
            }
            FireBase.firebaseDownlode(itemView.getContext(),imagename[0],image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CommuSeeMore.class);
                    intent.putExtra("commuId",item.getId());
                    Log.d(TAG, "onClick: 인텐트");
                    v.getContext().startActivity(intent);
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
