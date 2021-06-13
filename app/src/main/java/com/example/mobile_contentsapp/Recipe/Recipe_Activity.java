package com.example.mobile_contentsapp.Recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.Heart_Client;
import com.example.mobile_contentsapp.Recipe.Retrofit.Heart_Post;
import com.example.mobile_contentsapp.Recipe.Retrofit.Heart_Post_Id;
import com.example.mobile_contentsapp.Recipe.Retrofit.Heart_Recommand_Client;
import com.example.mobile_contentsapp.Recipe.Retrofit.Heart_cancel_Client;
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_Find_Client;
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_Find_Get;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Recipe_Activity extends AppCompatActivity {

    private String token;
    private SharedPreferences sharedPreferences;

    private boolean heartSelect;

    private ImageView recipeMainImage;
    private ImageView recipeCategoryImage;
    private TextView recipeTitle;
    private TextView recipeHeartText;
    private TextView recipeTimeText;
    private TextView recipeCategoryText;
    private TextView recipeIntroText;
    private TextView recipeProfileText;

    private ImageButton heartBtn;
    private ImageButton backbtn;

    private ImageView recipeProfile;

    private RecyclerView ingreRecyclerView;
    private RecyclerView orderRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        sharedPreferences = getSharedPreferences("sp",MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

        recipeMainImage = findViewById(R.id.recipe_detail_main_img);
        recipeCategoryImage = findViewById(R.id.recipe_detail_catrgory);
        recipeTitle = findViewById(R.id.recipe_detail_title);
        recipeHeartText = findViewById(R.id.recipe_detail_heart_text);
        recipeTimeText = findViewById(R.id.recipe_detail_time_text);
        recipeCategoryText = findViewById(R.id.recipe_detail_catrgory_text);
        recipeIntroText = findViewById(R.id.recipe_detail_intro_text);
        recipeProfileText = findViewById(R.id.recipe_detail_profile_naem);

        heartBtn = findViewById(R.id.recipe_detail_heart);
        backbtn = findViewById(R.id.recipe_detail_back);

        ingreRecyclerView = findViewById(R.id.recipe_detail_ingre_recycler);
        orderRecyclerView = findViewById(R.id.recipe_detail_order_recycler);

        Intent intent = getIntent();
        int id = intent.getIntExtra("recipeId",0);

        findRecipe(id);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void fireBase(String image, ImageView imageView){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://mobile-contents-812ea.appspot.com");
        StorageReference storageReference = storage.getReference();

        storageReference.child(image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Recipe_Activity.this).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public void findRecipe(int id){
        Call<Recipe_Find_Get> call = Recipe_Find_Client.getApiService().recipe_find_call(id);
        call.enqueue(new Callback<Recipe_Find_Get>() {
            @Override
            public void onResponse(Call<Recipe_Find_Get> call, Response<Recipe_Find_Get> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                }
                Log.d(TAG, "onResponse: 성공");
                Recipe_Find_Get recipe_find_get = response.body();

                Heart_Post_Id heart_post_id = new Heart_Post_Id(recipe_find_get.getId());

                recipeTitle.setText(recipe_find_get.getName());
                recipeTimeText.setText(recipe_find_get.getTime()+"분");
                recipeHeartText.setText(recipe_find_get.getPreference()+"");
                recipeProfileText.setText(recipe_find_get.getUser().getNickname());
                Log.d(TAG, "onResponse: "+recipe_find_get.getPreference());
                recipeCategoryImage.setColorFilter(Color.parseColor("#2d665f"));

                fireBase(recipe_find_get.getUser().getImage(),recipeProfile);
                setCategory(recipe_find_get.getCategory());
                setIngre(recipe_find_get.getIngredients());
                setOrder(recipe_find_get.getImage(),recipe_find_get.getContents());
                setHeart(heart_post_id);

                recipeIntroText.setText(recipe_find_get.getDescription());



                heartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickHeart(heart_post_id,recipe_find_get.getPreference());
                        heartSelect = !heartSelect;
                    }
                });

            }

            @Override
            public void onFailure(Call<Recipe_Find_Get> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });

    }
    public void setCategory(int num){
        switch (num){
            case 0:
                recipeCategoryText.setText("커피");
                recipeCategoryImage.setImageDrawable(ContextCompat.getDrawable(Recipe_Activity.this,R.drawable.ic_coffee));
                break;
            case 1:
                recipeCategoryText.setText("음료");
                recipeCategoryImage.setImageDrawable(ContextCompat.getDrawable(Recipe_Activity.this,R.drawable.ic_juice));
                break;
            case 2:
                recipeCategoryText.setText("디저트");
                recipeCategoryImage.setImageDrawable(ContextCompat.getDrawable(Recipe_Activity.this,R.drawable.ic_cake));
                break;
            case 3:
                recipeCategoryText.setText("그 외");
                recipeCategoryImage.setImageDrawable(ContextCompat.getDrawable(Recipe_Activity.this,R.drawable.circle));
                break;
        }
    }
    public void setIngre(String ingretext){
        FlexboxLayoutManager manager = new FlexboxLayoutManager(Recipe_Activity.this);
        ingreRecyclerView.setLayoutManager(manager);
        ArrayList<ingreList_Item> list = new ArrayList<>();
        String[] ingre = ingretext.split("\\|");
        for(int i = 0; i < ingre.length; i++){
            if (ingre[i] != null){
                list.add(new ingreList_Item(ingre[i]));
            }
        }
        IngreList_adapter adapter = new IngreList_adapter(list);
        ingreRecyclerView.setAdapter(adapter);
    }
    public void setOrder(String image, String contents){
        LinearLayoutManager manager = new LinearLayoutManager(Recipe_Activity.this,LinearLayoutManager.VERTICAL,false);
        orderRecyclerView.setLayoutManager(manager);
        ArrayList<Recipe_detail_order_item> list = new ArrayList<>();

        String[] img = image.split("\\|",-1);
        Log.d(TAG, "setOrder: "+image);
        String[] content = contents.split("\\|",-1);
        Log.d(TAG, "setOrder: "+contents);
        for(int i = 0; i < content.length; i++){
            if (!img[i].isEmpty() || !content[i].isEmpty()) {
                list.add(new Recipe_detail_order_item(img[i], content[i]));
            }
        }
        Recipe_Detail_Order_Adapter adapter = new Recipe_Detail_Order_Adapter(list);
        orderRecyclerView.setAdapter(adapter);
    }
    public void setHeart(Heart_Post_Id heart_post_id){

        Call<Heart_Post> call = Heart_Client.getApiService().heartGetCall(token,heart_post_id);
        call.enqueue(new Callback<Heart_Post>() {
            @Override
            public void onResponse(Call<Heart_Post> call, Response<Heart_Post> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패 "+ response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                if (response.body().isExist()){
                    heartBtn.setImageResource(R.drawable.ic_selectheart);
                    heartSelect = true;
                }else{
                    heartBtn.setImageResource(R.drawable.ic_heart);
                    heartSelect = false;
                }
            }

            @Override
            public void onFailure(Call<Heart_Post> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });
    }
    public void clickHeart(Heart_Post_Id heart_post_id,int heart){
        if (heartSelect){
            Call<Heart_Post_Id> call = Heart_cancel_Client.getApiService().cancelCall(token,heart_post_id);
            call.enqueue(new Callback<Heart_Post_Id>() {
                @Override
                public void onResponse(Call<Heart_Post_Id> call, Response<Heart_Post_Id> response) {
                    if (!response.isSuccessful()){
                        Log.d(TAG, "onResponse: 실패" + response.code());
                        return;
                    }
                    Log.d(TAG, "onResponse: 성공");
                    heartBtn.setImageResource(R.drawable.ic_heart);
                    recipeHeartText.setText(String.valueOf(heart));
                }

                @Override
                public void onFailure(Call<Heart_Post_Id> call, Throwable t) {
                    Log.d(TAG, "onFailure: 시스템 에러" +t.getMessage());
                }
            });
        }else{
            Call<Heart_Post_Id> call = Heart_Recommand_Client.getApiService().recommandCall(token,heart_post_id);
            call.enqueue(new Callback<Heart_Post_Id>() {
                @Override
                public void onResponse(Call<Heart_Post_Id> call, Response<Heart_Post_Id> response) {
                    if (!response.isSuccessful()){
                        Log.d(TAG, "onResponse: 실패" + response.code());
                        return;
                    }
                    Log.d(TAG, "onResponse: 성공");
                    heartBtn.setImageResource(R.drawable.ic_selectheart);
                    recipeHeartText.setText(String.valueOf(heart+1));
                }

                @Override
                public void onFailure(Call<Heart_Post_Id> call, Throwable t) {
                    Log.d(TAG, "onFailure: 시스템 에러" +t.getMessage());
                }
            });
        }

    }
}