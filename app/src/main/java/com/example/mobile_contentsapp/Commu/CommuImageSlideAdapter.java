package com.example.mobile_contentsapp.Commu;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class CommuImageSlideAdapter extends RecyclerView.Adapter<CommuImageSlideAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> items;

    public CommuImageSlideAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commu_image_slider_item,parent,false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton image;
        private Dialog dialog;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Commu_image_slider_item);
        }
        public void onBind(String imageName){
            FireBase.firebaseDownlode(context,imageName,image);
            dialog = new Dialog(itemView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_image_slider);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(imageName);
                }
            });
        }

        public void showDialog(String imageName){
            dialog.show();
            ImageView imageView = dialog.findViewById(R.id.dialog_image);
            FireBase.firebaseDownlode(context,imageName,imageView);
        }
    }
}
