package com.example.mobile_contentsapp.Commu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentDeleteClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentListGet;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;
import static com.example.mobile_contentsapp.Main.SplashActivity.userId;

public class CommuCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_NORMAL = 0;
    private final int VIEW_TYPE_MY = 1;
    private final int VIEW_TYPE_LOADING = 2;

    public interface OnLongClickListener{
        void OnClick(View view, int pos);
    }
    private OnLongClickListener mlistener = null;

    private ArrayList<CommuCommentListGet> items;

    public void setOnClickListener(OnLongClickListener onClickListener){mlistener = onClickListener;}
    public CommuCommentAdapter(ArrayList<CommuCommentListGet> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null){
            return VIEW_TYPE_LOADING;
        }
        return items.get(position).getUser().getId().equals(userId) ? VIEW_TYPE_MY :  VIEW_TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commu_comment, parent, false);
        return new NormalViewHolder(vh);
    } else if (viewType == VIEW_TYPE_LOADING){
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
        return new LoadingViewHolder(vh);
        }else{
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_commu_comment, parent, false);
        return new MyViewHolder(vh);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            CommuCommentListGet item = items.get(position);
            onBindMy((MyViewHolder) holder,item,position);
        }else if(holder instanceof  NormalViewHolder){
            CommuCommentListGet item = items.get(position);
            onBindNormal((NormalViewHolder) holder,item);
        }
        else if (holder instanceof LoadingViewHolder){
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

    public void removeItem(View v,int pos){
        items.remove(pos);
        notifyItemRemoved(pos);
        if(pos != RecyclerView.NO_POSITION){
            mlistener.OnClick(v,pos);
        }
    }

    public void onBindNormal(NormalViewHolder holder, CommuCommentListGet item){
        if (item.getUser().getImage() != null){
            FireBase.firebaseDownlode(holder.itemView.getContext(),item.getUser().getImage(),holder.profile);
        }
        holder.name.setText(item.getUser().getNickname());
        holder.contents.setText(item.getContents());
    }

    public void onBindMy(MyViewHolder holder , CommuCommentListGet item, int pos){

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext())
                        .setMessage("삭제하시겠습니까?")
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.deleteComment(item.getId(),pos,v);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
        holder.name.setText(item.getUser().getNickname());
        holder.contents.setText(item.getContents());
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {

    }


    public class NormalViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        CardView profileCard;
        TextView name;
        TextView contents;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            profileCard = itemView.findViewById(R.id.commu_comment_profile_card);
            profile = itemView.findViewById(R.id.commu_comment_profile);
            name = itemView.findViewById(R.id.comment_profile_name);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView contents;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.comment_profile_name);
            contents = itemView.findViewById(R.id.commu_comment_contents);
        }
        public void deleteComment(int id,int pos,View v){
            Call<Void> call = CommuCommentDeleteClient.getApiService().commuHeartSelectCall(tokenValue,id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(!response.isSuccessful()){
                        Log.d(TAG, "onResponse: 실패"+response.code());
                        return;
                    }
                    Log.d(TAG, "onResponse: 성공");
                    removeItem(v,pos);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }
}
