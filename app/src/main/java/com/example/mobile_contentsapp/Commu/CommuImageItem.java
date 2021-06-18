package com.example.mobile_contentsapp.Commu;

import android.graphics.Bitmap;

public class Commu_Image_Item {
    private Bitmap image;

    public Commu_Image_Item(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
