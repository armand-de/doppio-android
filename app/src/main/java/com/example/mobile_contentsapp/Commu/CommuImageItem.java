package com.example.mobile_contentsapp.Commu;

import android.graphics.Bitmap;

public class CommuImageItem {
    private Bitmap image;

    public CommuImageItem(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
