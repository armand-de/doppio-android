package com.example.mobile_contentsapp.Recipe;

import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mobile_contentsapp.Commu.CommuFragment;

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
            return new RecipeFragment();
        } else {
            return new CommuFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
