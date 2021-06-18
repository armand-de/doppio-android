package com.example.mobile_contentsapp.Recipe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RecipeItem {
    private String text;
    private Bitmap bitmap;

    public RecipeItem(String text, Bitmap bitmap) {
        this.text = text;
        this.bitmap = bitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
