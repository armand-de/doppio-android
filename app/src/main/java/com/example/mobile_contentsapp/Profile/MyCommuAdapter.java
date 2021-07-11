package com.example.mobile_contentsapp.Profile;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Commu.CommuSeeMore;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MyCommuAdapter extends RecyclerView.Adapter<MyCommuAdapter.ViewHolder> {

    ArrayList<CommuListGet> items;

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
        holder.onBind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        public void onBind(CommuListGet item){
            title.setText(item.getTitle());
            name.setText("");
            heartText.setText(String.valueOf(item.getPreference()));
            String[] imagename = item.getImage().split("\\|");
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
}
