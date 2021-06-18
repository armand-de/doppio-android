package com.example.mobile_contentsapp.Recipe;

public class RecipeSeeMoreOrderItem {
    private String image;
    private String text;

    public RecipeSeeMoreOrderItem(String image, String text) {
        this.image = image;
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
