package com.example.mobile_contentsapp.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.Profile.Retrofit.ProfileClient;
import com.example.mobile_contentsapp.Profile.Retrofit.ProfileUpdata;
import com.example.mobile_contentsapp.Profile.Retrofit.ProfileUpdateClient;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;

public class ProfileActivity extends AppCompatActivity {

    private boolean change = false;
    private Uri profileUri;
    private String profileImagename = "";

    private My_Recipe_Fragment my_recipe_fragment;
    private My_Commu_Fragment my_commu_fragment;
    private ViewPager2 pager2;
    private TabLayout tabLayout;

    private TextView nickname;
    private ImageButton profileImage;

    @Override
    public void onBackPressed() {
        if (change){
            backDialog();
        }else{
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pager2 = findViewById(R.id.profile_pager);
        tabLayout = findViewById(R.id.profile_tab);

        nickname = findViewById(R.id.profile_nickname);
        profileImage = findViewById(R.id.profile_profile_image);

        ImageView marker = findViewById(R.id.profile_change_mark);

        ImageButton backBtn = findViewById(R.id.profile_back);
        ImageButton profileChangeBtn = findViewById(R.id.profile_change);
        ImageButton setting = findViewById(R.id.setting);

        setMarker(marker);

        setProfile();
        createFragment();

        Profile_Adapter adapter = new Profile_Adapter(getSupportFragmentManager(),getLifecycle());
        adapter.addiItem(my_recipe_fragment);
        adapter.addiItem(my_commu_fragment);

        setTab();

        pager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, pager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                ArrayList<String> tabName = new ArrayList<>();
                tabName.add("레시피");
                tabName.add("스토리");
                TextView textView = new TextView(ProfileActivity.this);
                textView.setTextColor(Color.parseColor("#2d665f"));
                textView.setText(tabName.get(position));
                textView.setTextSize(16);
                textView.setGravity(Gravity.CENTER);
                tab.setCustomView(textView);
                switch (position){
                    case 0:
                        pager2.setCurrentItem(0);
                        break;
                    case 1:
                        pager2.setCurrentItem(1);
                        break;
                }
            }
        }).attach();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(change){
                    backDialog();
                }else{
                    finish();
                }
            }
        });
        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!change){
                    profileChangeBtn.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.big_circle));
                    profileChangeBtn.setImageDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_pen));
                }else{
                    profileChangeBtn.setBackgroundColor(Color.parseColor("#00000000"));
                    profileChangeBtn.setImageDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_white_pen));
                    updataProfile(FireBase.firebaseUpload(v.getContext(),profileUri));
                }
                change = !change;
                setMarker(marker);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,101);
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,Setting.class);
                startActivity(intent);
            }
        });
    }
    public void createFragment(){
        my_recipe_fragment = new My_Recipe_Fragment();
        my_commu_fragment = new My_Commu_Fragment();
    }
    public void setTab(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();

                switch (pos){
                    case 0:
                        pager2.setCurrentItem(0);
                        break;
                    case 1:
                        pager2.setCurrentItem(1);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void setProfile(){
        Call<User> call = ProfileClient.getApiService().profileCall(tokenValue);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패 " +response.code());
                    finish();
                    Toast.makeText(ProfileActivity.this, "프로필을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    nickname.setText(response.body().getNickname());
                    if (response.body().getImage() != null){
                        profileImagename = response.body().getImage();
                    }
                    FireBase.firebaseDownlode(getApplicationContext(),profileImagename,profileImage);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    public void setMarker(ImageView marker1){
        if (change){
            marker1.setVisibility(View.VISIBLE);
        }else{
            marker1.setVisibility(View.INVISIBLE);
        }
    }
    public void updataProfile(String image){
        ProfileUpdata profileUpdata = new ProfileUpdata(image);

        Call<User> call = ProfileUpdateClient.getApiService().profileUpdateCall(tokenValue, profileUpdata);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ProfileActivity.this, "프로필 변경을 실패하였습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러");
            }
        });
    }

    public void backDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("프로필 변경")
                .setMessage("변경사항을 저장하시겠습니까?")
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updataProfile(profileImagename);
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if(resultCode == RESULT_OK){
                try {
                    profileUri = data.getData();
                }catch (Exception e){
                    e.printStackTrace();
                }
                profileImage.setImageURI(profileUri);
            }
        }
    }
}