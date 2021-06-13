package com.example.mobile_contentsapp.Commu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Comment_List_Get;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Find_Get;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class Commu_Comment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    ArrayList<Commu_Comment_List_Get> items;

    public Commu_Comment_Adapter(ArrayList<Commu_Comment_List_Get> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.commu_comment_item, parent, false);
        return new NormalViewHolder(vh);
    } else {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.roding_item, parent, false);
        return new LoadingViewHolder(vh);
    }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    public void onBind(NormalViewHolder holder , Commu_Comment_List_Get item){
        FireBase.firebaseDownlode(holder.itemView.getContext(),item.getUser().getImage(),holder.profile);
        holder.name.setText(item.getUser().getNickname());
        holder.contents.setText(item.getContents());
        holder.createDate.setText(item.getCreatedDate());
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView createDate;
        TextView contents;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.commu_comment_profile);
            name = itemView.findViewById(R.id.comment_profile_name);
            createDate = itemView.findViewById(R.id.commu_comment_create_date);
            contents = itemView.findViewById(R.id.commu_comment_contents);
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
