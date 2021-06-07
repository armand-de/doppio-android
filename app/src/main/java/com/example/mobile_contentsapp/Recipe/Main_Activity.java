package com.example.mobile_contentsapp.Recipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_contentsapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import retrofit2.http.Url;

import static android.content.ContentValues.TAG;

public class Main_Activity extends AppCompatActivity {
    private Recipe_Fragment recipe_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        recipe_fragment = new Recipe_Fragment();

        ImageButton recipe_btn = findViewById(R.id.recipebtn);
        ImageButton commu_btn = findViewById(R.id.comumbtn);
        ImageButton create_btn = findViewById(R.id.create_btn);

        ViewPager2 pager2 = findViewById(R.id.vp);

        PagerAdapter adapter = new PagerAdapter(this);
        pager2.setAdapter(adapter);

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
                Intent intent = new Intent(Main_Activity.this,Insert_Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recipe_fragment.listRefresh();
    }
}