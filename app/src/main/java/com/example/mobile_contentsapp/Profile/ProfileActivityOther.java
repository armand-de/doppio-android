package com.example.mobile_contentsapp.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_contentsapp.Login.FindPasswordActivity1;
import com.example.mobile_contentsapp.Login.SignInActivity;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.Main.SplashActivity;
import com.example.mobile_contentsapp.Profile.Retrofit.DeleteClient;
import com.example.mobile_contentsapp.Profile.Retrofit.FindOtherProfileClient;
import com.example.mobile_contentsapp.Profile.Retrofit.ProfileClient;
import com.example.mobile_contentsapp.Profile.Retrofit.ProfileUpdate;
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

public class ProfileActivityOther extends AppCompatActivity {

    private boolean isMy = false;
    private String profileImagename = "";

    private Uri profileUri;
    private String userId;

    private TextView nickname;
    private ImageButton profileImage;

    private Profile_Adapter adapter;
    private My_Recipe_Fragment my_recipe_fragment;
    private My_Commu_Fragment my_commu_fragment;
    private ViewPager2 pager2;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getWindow().setStatusBarColor(Color.alpha(R.color.background));

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (id.equals(SplashActivity.userId)){
            isMy = true;
        }

        nickname = findViewById(R.id.profile_nickname);
        profileImage = findViewById(R.id.profile_profile_image);

        pager2 = findViewById(R.id.profile_pager);
        tabLayout = findViewById(R.id.profile_tab);

        ImageButton backBtn = findViewById(R.id.profile_back);
        ImageButton setting = findViewById(R.id.setting);
        ImageView marker = findViewById(R.id.profile_change_mark);

        setProfile(id);

        if (!isMy){
            marker.setVisibility(View.GONE);
            setting.setVisibility(View.GONE);
        }

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),v);
                getMenuInflater().inflate(R.menu.context,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.sign_out){
                            signOut();
                        }else if(item.getItemId()== R.id.change_password){
                            Intent intent = new Intent(ProfileActivityOther.this, FindPasswordActivity1.class);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivityOther.this)
                                    .setMessage("탈퇴하시겠습니까?")
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteAccount();
                                        }
                                    });
                            deleteAccount();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMy){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,101);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setProfile(String id){
        Call<User> call = FindOtherProfileClient.getApiService().findProfile(tokenValue,id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패 " +response.code());
                    finish();
                    Toast.makeText(ProfileActivityOther.this, "프로필을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Log.d(TAG, "onResponse: 성공");
                    userId = response.body().getId();
                    createFragment(userId);

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
    public void createFragment(String id){
        my_recipe_fragment = new My_Recipe_Fragment();
        my_commu_fragment = new My_Commu_Fragment();

        Bundle bundle = new Bundle(1);
        bundle.putString("userid",id);
        my_recipe_fragment.setArguments(bundle);
        my_commu_fragment.setArguments(bundle);

        adapter = new Profile_Adapter(getSupportFragmentManager(),getLifecycle());
        adapter.addiItem(my_recipe_fragment);
        adapter.addiItem(my_commu_fragment);

        pager2.setAdapter(adapter);
        setTab();
        setTabContents();
    }

    public void setTabContents(){
        new TabLayoutMediator(tabLayout, pager2, new TabLayoutMediator.TabConfigurationStrategy() {
        @Override
        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            ArrayList<String> tabName = new ArrayList<>();
            tabName.add("레시피");
            tabName.add("커뮤니티");
            TextView textView = new TextView(ProfileActivityOther.this);
            textView.setTextColor(Color.parseColor("#3F7972"));
            textView.setText(tabName.get(position));
            textView.setTextSize(20);
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
    }).attach();}

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
                updataProfile(FireBase.firebaseUpload(ProfileActivityOther.this,profileUri));
            }
        }
    }

    public void updataProfile(String image){
        ProfileUpdate profileUpdata = new ProfileUpdate(image);

        Call<User> call = ProfileUpdateClient.getApiService().profileUpdateCall(tokenValue, profileUpdata);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    Toast.makeText(ProfileActivityOther.this, "프로필 변경을 실패하였습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러");
            }
        });
    }

    public void signOut(){
        SharedPreferences sharedPreferences = getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        restart();
    }

    public void deleteAccount(){
        Call<Void> call = DeleteClient.getApiService().deleteAccount(tokenValue);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                restart();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void restart(){
        finishAffinity();
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
        System.exit(0);
    }
}
