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
    public void dialog(RecyclerView recyclerView,ArrayList<ingredientListItem> list){
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ingre_dialog);
        dialog.show();

        EditText ingredientName = dialog.findViewById(R.id.ingre_name_edit);
        EditText amountEdit = dialog.findViewById(R.id.ingre_amount_edit);
        Button posBtn = dialog.findViewById(R.id.ingre_dig_pos);
        Button negBtn = dialog.findViewById(R.id.ingre_dig_neg);
        
        posBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = ingreName.getText().toString()+" "+amountEdit.getText().toString();
                list.add(new ingredientListItem(result));
                IngreList_adapter adapter = new IngreList_adapter(list);
                recyclerView.setAdapter(adapter);
                dialog.dismiss();
            }
        });
        negBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
