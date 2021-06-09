package com.example.mobile_contentsapp.Recipe;

import android.graphics.Color;
import android.media.Image;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mobile_contentsapp.Commu.Commu_Fragment;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStateAdapter {

    ImageButton imageButton1;
    ImageButton imageButton2;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity, ImageButton imageButton1, ImageButton imageButton2) {
        super(fragmentActivity);
        this.imageButton1 = imageButton1;
        this.imageButton2 = imageButton2;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0){
            return new Recipe_Fragment();
        } else {
            return new Commu_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
