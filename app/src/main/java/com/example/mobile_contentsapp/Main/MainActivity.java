package com.example.mobile_contentsapp.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_contentsapp.Commu.CommuFragment;
import com.example.mobile_contentsapp.Commu.CommuInsertActivity;
import com.example.mobile_contentsapp.Profile.ProfileActivityOther;
import com.example.mobile_contentsapp.Profile.Retrofit.ProfileClient;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.RecipeInsertActivity;
import com.example.mobile_contentsapp.Recipe.PagerAdapter;
import com.example.mobile_contentsapp.Recipe.RecipeFragment;
import com.example.mobile_contentsapp.Recipe.Retrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;
import static com.example.mobile_contentsapp.Main.SplashActivity.userId;

public class MainActivity extends AppCompatActivity {
    private RecipeFragment recipeFragment;
    private CommuFragment commuFragment;
    private ImageButton profileBtn;
    private int pagePosition;

    @Override
    public void onBackPressed() {
        endDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setProfile(profileBtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(Color.parseColor("#f1f2f3"));

        recipeFragment = new RecipeFragment();
        commuFragment = new CommuFragment();

        profileBtn = findViewById(R.id.profile_btn);

        LinearLayout recipeBox = findViewById(R.id.recipe_btn_layout);
        LinearLayout commuBox = findViewById(R.id.commu_btn_layout);

        ImageButton recipeBtn = findViewById(R.id.recipebtn);
        ImageButton commuBtn = findViewById(R.id.commubtn);
        ImageButton createBtn = findViewById(R.id.create_btn);

        TextView recipeText = findViewById(R.id.recipe_title);
        TextView commuText = findViewById(R.id.commu_title);

        ViewPager2 pager2 = findViewById(R.id.vp);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0,0,0,0);
        params2.setMargins(50,0,0,0);

        PagerAdapter adapter = new PagerAdapter(this,recipeBtn,commuBtn);
        pager2.setAdapter(adapter);

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == 0){
                    recipeBox.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.white_round_btn));
                    commuBox.setBackground(null);
                    recipeText.setVisibility(View.VISIBLE);
                    commuText.setVisibility(View.GONE);
                    recipeBtn.setColorFilter(Color.parseColor("#3F7972"));
                    commuBtn.setColorFilter(Color.parseColor("#BFD5D3"));
                    recipeBtn.setLayoutParams(params2);
                    commuBtn.setLayoutParams(params);
                }else{
                    commuBox.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.white_round_btn));
                    recipeBox.setBackground(null);
                    commuText.setVisibility(View.VISIBLE);
                    recipeText.setVisibility(View.GONE);
                    recipeBtn.setColorFilter(Color.parseColor("#BFD5D3"));
                    commuBtn.setColorFilter(Color.parseColor("#3F7972"));
                    recipeBtn.setLayoutParams(params);
                    commuBtn.setLayoutParams(params2);
                }
                pagePosition = position;
            }
        });


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
                Intent intent = new Intent(MainActivity.this, ProfileActivityOther.class);
                intent.putExtra("id",userId);
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
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
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
