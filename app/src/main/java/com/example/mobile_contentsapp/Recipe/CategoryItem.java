package com.example.mobile_contentsapp.Recipe;

import android.graphics.drawable.Drawable;

public class Category_Item {
    private String text;
    private int image;
    private int typeCode;

    public Category_Item(String text, int image, int typeCode) {
        this.text = text;
        this.image = image;
        this.typeCode = typeCode;
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

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }



}
