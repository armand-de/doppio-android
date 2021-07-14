package com.example.mobile_contentsapp.Recipe;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.mobile_contentsapp.R;

public class Timepicker {
    public static int time;
    private Context context;

    public Timepicker(Context context) {
        this.context = context;
    }

    public void picker(TextView timeText){
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_time);
        dialog.show();

        NumberPicker min = dialog.findViewById(R.id.minpicker);
        NumberPicker hour = dialog.findViewById(R.id.hourpicker);
        Button pos = dialog.findViewById(R.id.postext);
        Button neg = dialog.findViewById(R.id.negtext);

        hour.setMinValue(0);
        hour.setMaxValue(15);

        min.setMinValue(0);
        min.setMaxValue(59);

        neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = hour.getValue()*60+min.getValue();
                String time = String.format("%d:%02d",hour.getValue(),+min.getValue());
                timeText.setText(time);
                dialog.dismiss();
            }
        });
    }
}
