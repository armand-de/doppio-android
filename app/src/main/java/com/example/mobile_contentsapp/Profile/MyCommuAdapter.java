package com.example.mobile_contentsapp.Profile;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Commu.CommuSeeMore;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuFindClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuFindGet;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.Profile.Retrofit.CommuDeleteApi;
import com.example.mobile_contentsapp.Profile.Retrofit.CommuDeleteClient;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.RecipeWriteAdapter;
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

public class MyCommuAdapter extends RecyclerView.Adapter<MyCommuAdapter.ViewHolder> {

    ArrayList<CommuListGet> items;

    public interface OnLongClickListener{
        void OnClick(View view, int pos, int id);
    }
    OnLongClickListener mlistener = null;


    public void setOnClickListener(OnLongClickListener onLongClickListener){mlistener = onLongClickListener;}
    public MyCommuAdapter(ArrayList<CommuListGet> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commu_list,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommuListGet item = items.get(position);
        holder.onBind(item,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private String id;
        private TextView title;
        private TextView name;
        private TextView heartText;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.commu_list_title);
            name = itemView.findViewById(R.id.commu_list_profile);
            heartText = itemView.findViewById(R.id.commu_list_heart_text);
            image = itemView.findViewById(R.id.commu_image);
        }
        public void onBind(CommuListGet item, int position){
            title.setText(item.getTitle());
            name.setVisibility(View.GONE);
            heartText.setText(String.valueOf(item.getPreference()));
            String[] imagename = item.getImage().split("\\|");
            if (!item.getImage().isEmpty()){
                FireBase.firebaseDownlode(itemView.getContext(),imagename[0],image);
            }else{
                image.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CommuSeeMore.class);
                    intent.putExtra("commuId",item.getId());
                    Log.d(TAG, "onClick: 인텐트");
                    v.getContext().startActivity(intent);
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
