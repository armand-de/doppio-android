package com.example.mobile_contentsapp.Recipe;

import android.graphics.drawable.Drawable;

public class Category_Item {
    private String text;
    private int image;

    public Category_Item(String text, int image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
