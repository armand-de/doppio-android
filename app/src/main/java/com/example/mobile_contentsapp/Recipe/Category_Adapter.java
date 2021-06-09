package com.example.mobile_contentsapp.Recipe;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class Category_Adapter extends BaseAdapter {
    private int WHITE_TYPE = 0;
    private int NORMAL_TYPE = 1;
    private Context context;
    private ArrayList<Category_Item> data;
    private LayoutInflater inflater;

    public Category_Adapter(Context context, ArrayList<Category_Item> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data!=null) return data.size();
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.spinner_getview,parent,false);
        }
        if (data != null){
            ConstraintLayout spinnerConstraint = convertView.findViewById(R.id.spinner_constraint);
            TextView text = convertView.findViewById(R.id.category_text);
            ImageView image =convertView.findViewById(R.id.category_icon);

            text.setText(data.get(position).getText());

            switch(data.get(position).getImage()){
                case 1:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_white_coffee));
                    break;
                case 2:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_juice));
                    break;
                case 3:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_cake));
                    break;
                case 4:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.circle));
                    break;
                default:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.circle));

                    break;
            }

            if (data.get(position).getTypeCode() == 0){
                spinnerConstraint.setBackground(ContextCompat.getDrawable(convertView.getContext(),R.drawable.round_btn));
                text.setTextColor(Color.parseColor("#ffffff"));
                image.setColorFilter(Color.parseColor("#ffffff"));
            }else{
                spinnerConstraint.setBackground(ContextCompat.getDrawable(convertView.getContext(),R.drawable.white_round_btn));
                text.setTextColor(Color.parseColor("#2d665f"));
                image.setColorFilter(Color.parseColor("#2d665f"));
            }

        }
        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
       if (convertView == null){
           convertView = inflater.inflate(R.layout.spinner_select,parent,false);
       }
        if (data != null){

            ConstraintLayout spinnerConstraint = convertView.findViewById(R.id.spinner_select_constraint);
            TextView text = convertView.findViewById(R.id.category_select_text);
            ImageView image =convertView.findViewById(R.id.category_select_icon);

            if (data.get(position).getTypeCode() == 0){
                spinnerConstraint.setBackgroundColor(Color.parseColor("#2d665f"));
                text.setTextColor(Color.parseColor("#ffffff"));
                image.setColorFilter(Color.parseColor("#ffffff"));
            }else{
                spinnerConstraint.setBackgroundColor(Color.parseColor("#ffffff"));
                text.setTextColor(Color.parseColor("#2d665f"));
                image.setColorFilter(Color.parseColor("#2d665f"));
            }

            text.setText(data.get(position).getText());
            switch(data.get(position).getImage()){
                case 1:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_white_coffee));
                    break;
                case 2:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_juice));
                    break;
                case 3:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_cake));
                    break;
                case 4:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.circle));
                    break;
                default:
                    image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.circle));
                    text.setText("그 외");
                    break;
            }

        }
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
