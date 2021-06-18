package com.example.mobile_contentsapp.Recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.HeartFindClient;
import com.example.mobile_contentsapp.Recipe.Retrofit.HeartFindPost;
import com.example.mobile_contentsapp.Recipe.Retrofit.HeartPostId;
import com.example.mobile_contentsapp.Recipe.Retrofit.HeartPostClient;
import com.example.mobile_contentsapp.Recipe.Retrofit.HeartCancelClient;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeFindClient;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeFindGet;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;

public class RecipeSeeMore extends AppCompatActivity {

    private boolean heartSelect;
    private int heartValue;

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

    private HeartPostId heart_post_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_see_more);

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

        recipeProfile = findViewById(R.id.recipe_detail_profile);

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

        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHeart(heart_post_id,heartValue);
                heartSelect = !heartSelect;
            }
        });
    }


    public void findRecipe(int id){
        Call<RecipeFindGet> call = RecipeFindClient.getApiService().recipeFindCall(id);
        call.enqueue(new Callback<RecipeFindGet>() {
            @Override
            public void onResponse(Call<RecipeFindGet> call, Response<RecipeFindGet> response) {
                if(!response.isSuccessful()){
                    finish();
                    Toast.makeText(RecipeSeeMore.this, "레시피를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
                }
                RecipeFindGet recipe_find_get = response.body();
                heartValue = recipe_find_get.getPreference();

                heart_post_id = new HeartPostId(recipe_find_get.getId());

                Log.d(TAG, "onResponse: "+recipe_find_get.getThumbnail());

                FireBase.firebaseDownlode(getApplicationContext(),recipe_find_get.getThumbnail(),recipeMainImage);
                if (recipe_find_get.getUser().getImage() != null){
                    FireBase.firebaseDownlode(RecipeSeeMore.this,recipe_find_get.getUser().getImage(),recipeProfile);
                }
                recipeTitle.setText(recipe_find_get.getName());
                recipeTimeText.setText(recipe_find_get.getTime()+"분");
                recipeHeartText.setText(recipe_find_get.getPreference()+"");
                recipeProfileText.setText(recipe_find_get.getUser().getNickname());


                Log.d(TAG, "onResponse: "+recipe_find_get.getPreference());
                recipeCategoryImage.setColorFilter(Color.parseColor("#2d665f"));
                if (recipe_find_get.getUser().getImage() != null){
                    FireBase.firebaseDownlode(RecipeSeeMore.this,recipe_find_get.getUser().getImage(),recipeProfile);
                }

                setCategory(recipe_find_get.getCategory());
                setIngre(recipe_find_get.getIngredients());
                setOrder(recipe_find_get.getImage(),recipe_find_get.getContents());
                setHeart(heart_post_id);

                recipeIntroText.setText(recipe_find_get.getDescription());
            }

            @Override
            public void onFailure(Call<RecipeFindGet> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });

    }
    public void setCategory(int num){
        switch (num){
            case 1:
                recipeCategoryText.setText("커피");
                recipeCategoryImage.setImageDrawable(ContextCompat.getDrawable(RecipeSeeMore.this,R.drawable.ic_coffee));
                break;
            case 2:
                recipeCategoryText.setText("음료");
                recipeCategoryImage.setImageDrawable(ContextCompat.getDrawable(RecipeSeeMore.this,R.drawable.ic_juice));
                break;
            case 3:
                recipeCategoryText.setText("디저트");
                recipeCategoryImage.setImageDrawable(ContextCompat.getDrawable(RecipeSeeMore.this,R.drawable.ic_cake));
                break;
            case 4:
                recipeCategoryText.setText("그 외");
                recipeCategoryImage.setImageDrawable(ContextCompat.getDrawable(RecipeSeeMore.this,R.drawable.circle));
                break;
        }
    }
    public void setIngre(String ingretext){
        FlexboxLayoutManager manager = new FlexboxLayoutManager(RecipeSeeMore.this);
        ingreRecyclerView.setLayoutManager(manager);
        ArrayList<IngredientListItem> list = new ArrayList<>();
        String[] ingre = ingretext.split("\\|");
        for(int i = 0; i < ingre.length; i++){
            if (ingre[i] != null){
                list.add(new IngredientListItem(ingre[i]));
            }
        }
        IngredientListAdapter adapter = new IngredientListAdapter(list);
        ingreRecyclerView.setAdapter(adapter);
    }
    public void setOrder(String image, String contents){
        LinearLayoutManager manager = new LinearLayoutManager(RecipeSeeMore.this,LinearLayoutManager.VERTICAL,false);
        orderRecyclerView.setLayoutManager(manager);
        ArrayList<RecipeSeeMoreOrderItem> list = new ArrayList<>();

        String[] img = image.split("\\|",-1);
        Log.d(TAG, "setOrder: "+image);
        String[] content = contents.split("\\|",-1);
        Log.d(TAG, "setOrder: "+contents);
        int len = (content.length < img.length)? img.length: content.length;
        for(int i = 0; i < len-1; i++){
            if (!img[i].isEmpty() || !content[i].isEmpty()) {
                list.add(new RecipeSeeMoreOrderItem(img[i], content[i]));
            }
        }
        RecipeSeeMoreOrderAdapter adapter = new RecipeSeeMoreOrderAdapter(list);
        orderRecyclerView.setAdapter(adapter);
    }
    public void setHeart(HeartPostId heart_post_id){

        Call<HeartFindPost> call = HeartFindClient.getApiService().heartFindCall(tokenValue,heart_post_id);
        call.enqueue(new Callback<HeartFindPost>() {
            @Override
            public void onResponse(Call<HeartFindPost> call, Response<HeartFindPost> response) {
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
            public void onFailure(Call<HeartFindPost> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });
    }
    public void clickHeart(HeartPostId heart_post_id, int heart){
        if (heartSelect){
            Call<HeartPostId> call = HeartCancelClient.getApiService().cancelCall(tokenValue,heart_post_id);
            call.enqueue(new Callback<HeartPostId>() {
                @Override
                public void onResponse(Call<HeartPostId> call, Response<HeartPostId> response) {
                    if (!response.isSuccessful()){
                        Log.d(TAG, "onResponse: 실패" + response.code());
                        return;
                    }
                    Log.d(TAG, "onResponse: 성공");
                    heartBtn.setImageResource(R.drawable.ic_heart);
                    recipeHeartText.setText(String.valueOf(heart));
                }

                @Override
                public void onFailure(Call<HeartPostId> call, Throwable t) {
                    Log.d(TAG, "onFailure: 시스템 에러" +t.getMessage());
                }
            });
        }else{
            Call<HeartPostId> call = HeartPostClient.getApiService().heartPostcall(tokenValue,heart_post_id);
            call.enqueue(new Callback<HeartPostId>() {
                @Override
                public void onResponse(Call<HeartPostId> call, Response<HeartPostId> response) {
                    if (!response.isSuccessful()){
                        Log.d(TAG, "onResponse: 실패" + response.code());
                        return;
                    }
                    Log.d(TAG, "onResponse: 성공");
                    heartBtn.setImageResource(R.drawable.ic_selectheart);
                    recipeHeartText.setText(String.valueOf(heart+1));
                }

                @Override
                public void onFailure(Call<HeartPostId> call, Throwable t) {
                    Log.d(TAG, "onFailure: 시스템 에러" +t.getMessage());
                }
            });
        }

    }
}