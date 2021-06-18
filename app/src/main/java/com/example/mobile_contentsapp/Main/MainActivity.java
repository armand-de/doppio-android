package com.example.mobile_contentsapp.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_contentsapp.Commu.Commu_Fragment;
import com.example.mobile_contentsapp.Commu.Commu_Insert_Activity;
import com.example.mobile_contentsapp.Login.Sign_in_Activity;
import com.example.mobile_contentsapp.Profile.ProfileActivity;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Recipe_Insert_Activity;
import com.example.mobile_contentsapp.Recipe.PagerAdapter;
import com.example.mobile_contentsapp.Recipe.Recipe_Fragment;

public class Main_Activity extends AppCompatActivity {
    private Recipe_Fragment recipeFragment;
    private Commu_Fragment commuFragment;
    private int pagePosition;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        recipeFragment = new Recipe_Fragment();
        commuFragment = new Commu_Fragment();

        ImageButton recipe_btn = findViewById(R.id.recipebtn);
        ImageButton commu_btn = findViewById(R.id.comumbtn);
        ImageButton create_btn = findViewById(R.id.create_btn);
        ImageButton profileBtn = findViewById(R.id.profile_btn);

        SwipeRefreshLayout swipe = findViewById(R.id.main_swipe);
        ViewPager2 pager2 = findViewById(R.id.vp);

        PagerAdapter adapter = new PagerAdapter(this,recipe_btn,commu_btn);
        pager2.setAdapter(adapter);

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == 0){
                    recipe_btn.setColorFilter(Color.parseColor("#2d665f"));
                    commu_btn.setColorFilter(Color.parseColor("#BFD5D3"));
                }else{
                    recipe_btn.setColorFilter(Color.parseColor("#BFD5D3"));
                    commu_btn.setColorFilter(Color.parseColor("#2d665f"));
                }
                pagePosition = position;
            }
        });

        recipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager2.setCurrentItem(0,true);
            }
        });
        commu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager2.setCurrentItem(1,true);
            }
        });
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagePosition == 0){
                    Intent intent = new Intent(Main_Activity.this, Recipe_Insert_Activity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Main_Activity.this, Commu_Insert_Activity.class);
                    startActivity(intent);
                }
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Activity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRestart();
                swipe.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recipeFragment.listRefresh();
    }
}