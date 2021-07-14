package com.example.mobile_contentsapp.Recipe;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeCreateClient;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeCreatePost;

import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;
import static com.example.mobile_contentsapp.Recipe.Timepicker.time;

public class RecipeInsertActivity extends Activity {

    private Uri thunmbnailUri;
    private ArrayList<Uri> imgUri = new ArrayList<>();
    private ImageButton thumbnailImage;
    private ImageButton imageBtn;
    private int categoryNum = 1;
    private int recipePosition = 1;
    private boolean oven = false;
    private boolean thumbnail = true;
    private boolean isUpload = false;

    private RecipeWriteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);

        getWindow().setStatusBarColor(Color.parseColor("#f1f2f3"));

        time = 0;

        thumbnailImage = findViewById(R.id.imageadd);

        EditText titleEdit = findViewById(R.id.title_edit);
        EditText detailEdit = findViewById(R.id.recipe_detail_edit);

        TextView timeText = findViewById(R.id.time_text);

        Button timeSetBtn = findViewById(R.id.time_set);
        Button uploadBtn = findViewById(R.id.upload_btn);

        imageBtn = findViewById(R.id.image_add_btn);
        ImageButton backBtn = findViewById(R.id.recipe_insert_back);
        ImageButton ingreAddBtn = findViewById(R.id.add_ingre_btn);
        ImageButton recipeAddBtn = findViewById(R.id.add_recipe);

        Spinner spinner = findViewById(R.id.spinner);
        CheckBox ovenCheck = findViewById(R.id.oven_check);

        categorySet(spinner);

        RecyclerView ingredientRecycler = findViewById(R.id.ingre_recycler);
        RecyclerView recipeRecycler = findViewById(R.id.recipe_recycler);
        ArrayList<IngredientListItem> ingreList = new ArrayList<>();
        ArrayList<RecipeItem> recipeList = new ArrayList<>();

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                thumbnail = true;
                startActivityForResult(intent,101);
            }
        });

        thumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                thumbnail = true;
                startActivityForResult(intent,101);
            }
        });

        LinearLayoutManager manager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager manager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        ingredientRecycler.setLayoutManager(manager1);
        recipeRecycler.setLayoutManager(manager2);

        ingreAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngreDialog dialog = new IngreDialog(RecipeInsertActivity.this);
                dialog.dialog(ingredientRecycler,ingreList);
            }
        });
        Bitmap bitmap = getBitmap(R.drawable.ic_plus_small);

        adapter = new RecipeWriteAdapter(recipeList);
        recipeRecycler.setAdapter(adapter);

        adapter.setOnClickListener(new RecipeWriteAdapter.OnClickListener() {
            @Override
            public void OnClick(View view, int pos, ImageButton imageButton) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                thumbnail = false;
                recipePosition = pos;
                startActivityForResult(intent,101);
            }
        });
        adapter.setOnClickListener2(new RecipeWriteAdapter.OnClickListener2() {
            @Override
            public void OnClick(View view, int pos) {
                imgUri.remove(pos);
            }
        });

        recipeAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeList.add(new RecipeItem("", (bitmap)));
                adapter.notifyDataSetChanged();
                imgUri.add(null);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryNum = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        timeSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timepicker timepicker = new Timepicker(RecipeInsertActivity.this);
                timepicker.picker(timeText);
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            String contents = "";
            String ingredients = "";
            @Override
            public void onClick(View v) {
                if (!isUpload){
                    isUpload = true;
                    if (titleEdit.getText().length() < 3 || titleEdit.getText().length() > 20 || ingreList.isEmpty()
                            || detailEdit.getText().length() == 0 || detailEdit.getText().length() > 100){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RecipeInsertActivity.this)
                                .setMessage("정확한 내용을 입력해주세요")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                      isUpload = false;
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        isUpload = false;
                        return;
                    }

                    ProgressDialog dialog = new ProgressDialog(RecipeInsertActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("로딩중입니다.");
                    dialog.show();

                    for(int i = 0; i < recipeList.size(); i++){
                        if (recipeList.get(i) == null){
                            contents += " ";
                        }else{
                            contents += recipeList.get(i).getText()+"|";
                        }
                        Log.d(TAG, "contents : "+ contents);
                    }
                    for (int i = 0; i < ingreList.size(); i++){
                        ingredients += ingreList.get(i).getText()+"|";
                        Log.d(TAG, "ingredients : "+ ingredients);
                    }
                    onCheckBoxClicked(ovenCheck);

                    String imgs = "";
                    int size = imgUri.size();
                    for (int i = 0; i < size; i++){
                        if (imgUri.get(i) == null){
                            imgs += "|";
                        }else{
                            imgs += FireBase.firebaseUpload(v.getContext(), imgUri.get(i))+"|";
                        }
                    }
                    Log.d(TAG, "onClick: "+imgs);
                    create(dialog,titleEdit.getText().toString(), FireBase.firebaseUpload(v.getContext(), thunmbnailUri)
                            ,imgs,detailEdit.getText().toString(),ingredients,contents,
                            categoryNum,time,oven);
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
    private void categorySet(Spinner spinner){
        ArrayList<CategoryItem> category = new ArrayList<>();
        category.add(new CategoryItem("커피",1,0));
        category.add(new CategoryItem("음료",2,0));
        category.add(new CategoryItem("디저트",3,0));
        category.add(new CategoryItem("그 외",4,0));

        CategoryAdapter category_adapter = new CategoryAdapter(this,category);
        spinner.setAdapter(category_adapter);
    }

    public void create(ProgressDialog dialog,String name,
                       String thumbnail,String image, String description,
                       String ingredients ,String contents,
                       int category, int time, boolean useOven){

        RecipeCreatePost recipe_post = new RecipeCreatePost(name, thumbnail,image,description,
                ingredients,contents,category,time,useOven);

        Call<RecipeCreatePost> call = RecipeCreateClient.getApiService().recipeCreateCall(tokenValue,recipe_post);

        call.enqueue(new Callback<RecipeCreatePost>() {
            @Override
            public void onResponse(Call<RecipeCreatePost> call, Response<RecipeCreatePost> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response);
                    Toast.makeText(RecipeInsertActivity.this, "업로드에 실패했습니다", Toast.LENGTH_SHORT).show();
                    isUpload = false;
                    dialog.dismiss();
                    finish();
                }else{
                    isUpload = false;
                    dialog.dismiss();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RecipeCreatePost> call, Throwable t) {
            }
        });
    }

    public Bitmap getBitmap(int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(RecipeInsertActivity.this, drawableId);

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if(resultCode == RESULT_OK){
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    if (!thumbnail){
                        Log.d(TAG, "onActivityResult: 이미지");
                            adapter.changeimg(recipePosition,img);
                            adapter.notifyDataSetChanged();
                            imgUri.set(recipePosition,data.getData());
                        }else{
                            Log.d(TAG, "onActivityResult: 썸네일");
                            thunmbnailUri = data.getData();
                            thumbnailImage.setImageBitmap(img);
                            imageBtn.setVisibility(View.GONE);
                        }

                }catch (Exception e){
                    Log.d(TAG, "onActivityResult: "+e.getLocalizedMessage());
                    Log.d(TAG, "onActivityResult: "+e.getMessage());
                }
            }else if(requestCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onCheckBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()){
            case R.id.oven_check:
                if(checked){
                    oven = true;
                }else {
                    oven = false;
                }
                break;
        }
    }
}