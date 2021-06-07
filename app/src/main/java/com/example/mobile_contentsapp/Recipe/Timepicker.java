package com.example.mobile_contentsapp.Recipe;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.mobile_contentsapp.R;

import org.w3c.dom.Text;

public class Timepicker {
    private Context context;
    public Timepicker(Context context) {
        this.context = context;
    }
    public void picker(TextView textView){
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.timedialog);
        dialog.show();

        NumberPicker hour = dialog.findViewById(R.id.hourpicker);
        Button pos = dialog.findViewById(R.id.postext);
        Button neg = dialog.findViewById(R.id.negtext);

        hour.setMinValue(0);
        hour.setMaxValue(300);

        neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(String.valueOf(hour.getValue()));
                dialog.dismiss();
            }
        });
    }
}
