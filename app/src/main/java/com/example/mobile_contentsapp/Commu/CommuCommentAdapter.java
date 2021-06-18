package com.example.mobile_contentsapp.Commu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentCountClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentDeleteClient;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Comment_List_Get;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Find_Get;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;
import static com.example.mobile_contentsapp.Main.SplashActivity.userId;

public class Commu_Comment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private ArrayList<Commu_Comment_List_Get> items;

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
        if(holder instanceof NormalViewHolder){
            Commu_Comment_List_Get item = items.get(position);
            onBind((NormalViewHolder) holder,item,position);
        }else if (holder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void allRemove(){
        items.clear();
    }

    public void removeItem(int pos){
        items.remove(pos);
        notifyItemRemoved(pos);
        notifyDataSetChanged();
    }

    public void onBind(NormalViewHolder holder , Commu_Comment_List_Get item,int pos){
        holder.delete.setVisibility(View.INVISIBLE);
        if (item.getUser().getId().equals(userId)){
            Log.d(TAG, "onBind: 일치");
            holder.delete.setVisibility(View.VISIBLE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(pos);
                holder.deleteComment(item.getId());
            }
        });
        if (item.getUser().getImage() != null){
            FireBase.firebaseDownlode(holder.itemView.getContext(),item.getUser().getImage(),holder.profile);
        }
        holder.name.setText(item.getUser().getNickname());
        holder.contents.setText(item.getContents());
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {

    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        ImageButton delete;
        TextView name;
        TextView contents;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.commu_comment_profile);
            delete = itemView.findViewById(R.id.commu_comment_delete);
            name = itemView.findViewById(R.id.comment_profile_name);
            contents = itemView.findViewById(R.id.commu_comment_contents);
        }
        public void deleteComment(int id){
            Call<Void> call = CommuCommentDeleteClient.getApiService().commuHeartSelectApiCall(tokenValue,id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(!response.isSuccessful()){
                        Log.d(TAG, "onResponse: 실패"+response.code());
                        return;
                    }
                    Log.d(TAG, "onResponse: 성공");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

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
