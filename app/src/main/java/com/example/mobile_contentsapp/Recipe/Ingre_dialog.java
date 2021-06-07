package com.example.mobile_contentsapp.Recipe;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.R;

import java.util.ArrayList;

public class Ingre_dialog {
    private Context context;

    public Ingre_dialog(Context context) {
        this.context = context;
    }
    public void dialog(RecyclerView recyclerView,ArrayList<ingreList_Item> list){
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ingre_dialog);
        dialog.show();

        EditText ingre_name = dialog.findViewById(R.id.ingre_dig_edit);
        EditText amount_edit = dialog.findViewById(R.id.ingre_amount_edit);
        Button pos_btn = dialog.findViewById(R.id.ingre_dig_pos);
        Button neg_btn = dialog.findViewById(R.id.ingre_dig_neg);
        
        pos_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = ingre_name.getText().toString()+" "+amount_edit.getText().toString();
                list.add(new ingreList_Item(result));
                IngreList_adapter adapter = new IngreList_adapter(list);
                recyclerView.setAdapter(adapter);
                dialog.dismiss();
            }
        });
        neg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
