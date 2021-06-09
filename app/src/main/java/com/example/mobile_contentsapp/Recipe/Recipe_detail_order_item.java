package com.example.mobile_contentsapp.Recipe;

public class Recipe_detail_order_item {
    private String img;
    private String text;

    public Recipe_detail_order_item(String img, String text) {
        this.img = img;
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
