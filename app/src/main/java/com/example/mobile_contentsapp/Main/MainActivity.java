package com.example.mobile_contentsapp.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_contentsapp.Commu.CommuFragment;
import com.example.mobile_contentsapp.Commu.CommuInsertActivity;
import com.example.mobile_contentsapp.Profile.ProfileActivity;
import com.example.mobile_contentsapp.Profile.Retrofit.ProfileClient;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.RecipeInsertActivity;
import com.example.mobile_contentsapp.Recipe.PagerAdapter;
import com.example.mobile_contentsapp.Recipe.RecipeFragment;
import com.example.mobile_contentsapp.Recipe.Retrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;

public class MainActivity extends AppCompatActivity {
    private RecipeFragment recipeFragment;
    private CommuFragment commuFragment;
    private int pagePosition;

    @Override
    public void onBackPressed() {
        endDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        recipeFragment = new RecipeFragment();
        commuFragment = new CommuFragment();

        ImageButton recipeBtn = findViewById(R.id.recipebtn);
        ImageButton commuBtn = findViewById(R.id.comumbtn);
        ImageButton createBtn = findViewById(R.id.create_btn);
        ImageButton profileBtn = findViewById(R.id.profile_btn);

        ViewPager2 pager2 = findViewById(R.id.vp);

        PagerAdapter adapter = new PagerAdapter(this,recipeBtn,commuBtn);
        pager2.setAdapter(adapter);

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == 0){
                    recipeBtn.setColorFilter(Color.parseColor("#2d665f"));
                    commuBtn.setColorFilter(Color.parseColor("#BFD5D3"));
                }else{
                    recipeBtn.setColorFilter(Color.parseColor("#BFD5D3"));
                    commuBtn.setColorFilter(Color.parseColor("#2d665f"));
                }
                pagePosition = position;
            }
        });

        setProfile(profileBtn);

        recipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager2.setCurrentItem(0,true);
            }
        });
        commuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager2.setCurrentItem(1,true);
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagePosition == 0){
                    Intent intent = new Intent(MainActivity.this, RecipeInsertActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, CommuInsertActivity.class);
                    startActivity(intent);
                }
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
    public void setProfile(ImageButton profile){
        Call<User> call = ProfileClient.getApiService().profileCall(tokenValue);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    return;
                }
                if (response.body().getImage() != null){
                    FireBase.firebaseDownlode(MainActivity.this,response.body().getImage(),profile);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
    public void endDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("앱 종료")
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
