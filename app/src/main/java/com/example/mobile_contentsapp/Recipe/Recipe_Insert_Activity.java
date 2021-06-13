package com.example.mobile_contentsapp.Recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_Client;
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Recipe_Insert_Activity extends Activity {

    private Uri thunmbnail_uri;
    private ArrayList<Uri> img_uri = new ArrayList<>();
    Bitmap img;
    private String token;
    private ImageButton main_img_btn;
    private int category_num;
    private int recipe_position=0;
    private boolean oven = true;
    private boolean thumbnail = true;

    private Recipe_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("sp",MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

        EditText title_edit = findViewById(R.id.title_edit);
        EditText detail_edit = findViewById(R.id.recipe_detail_edit);
        TextView time_text = findViewById(R.id.time_text);
        Button time_set = findViewById(R.id.time_set);
        Button upload = findViewById(R.id.upload_btn);
        main_img_btn = findViewById(R.id.imageadd);
        ImageButton ingre_add = findViewById(R.id.add_ingre_btn);
        ImageButton recipe_add = findViewById(R.id.add_recipe);
        Spinner spinner = findViewById(R.id.spinner);
        CheckBox oven_check = findViewById(R.id.oven_check);

        img_uri.add(null);
        img_uri.add(null);

        time_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timepicker timepicker = new Timepicker(Recipe_Insert_Activity.this);
                timepicker.picker(time_text);
            }
        });

        RecyclerView ingrerecycler = findViewById(R.id.ingre_recycler);
        RecyclerView reciperecycler = findViewById(R.id.recipe_recycler);
        ArrayList<ingreList_Item> ingrelist = new ArrayList<>();
        ArrayList<Recipe_Item> recipelist = new ArrayList<>();

        main_img_btn.setOnClickListener(new View.OnClickListener() {
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
        ingrerecycler.setLayoutManager(manager1);
        reciperecycler.setLayoutManager(manager2);

        ingre_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingre_dialog dialog = new Ingre_dialog(Recipe_Insert_Activity.this);
                dialog.dialog(ingrerecycler,ingrelist);
            }
        });
        Bitmap bitmap = getBitmap(R.drawable.ic_small_img);

        recipelist.add(new Recipe_Item("",bitmap));
        recipelist.add(new Recipe_Item("",bitmap));

        adapter = new Recipe_adapter(recipelist);
        reciperecycler.setAdapter(adapter);

        adapter.setOnClickListener(new Recipe_adapter.OnClickListener() {
            @Override
            public void OnClick(View view, int pos, ImageButton imageButton) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                thumbnail = false;
                recipe_position = pos;
                startActivityForResult(intent,101);
            }
        });

        adapter.setOnLongClickListener(new Recipe_adapter.OnLongClickListener() {
            @Override
            public void OnLongClick(View view, int pos) {
                img_uri.remove(pos);
            }
        });

        recipe_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.additem(new Recipe_Item("",(bitmap)));
                adapter.notifyDataSetChanged();
                img_uri.add(null);
            }
        });

        ArrayList<Category_Item> category = new ArrayList<>();
        category.add(new Category_Item("커피",1,0));
        category.add(new Category_Item("음료",2,0));
        category.add(new Category_Item("디저트",3,0));
        category.add(new Category_Item("그 외",4,0));

        Category_Adapter category_adapter = new Category_Adapter(this,category);
        spinner.setAdapter(category_adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_num = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            String contents = "";
            String ingredients = "";
            @Override
            public void onClick(View v) {
                for(int i = 0; i < recipelist.size(); i++){
                    if (recipelist.get(i) == null){
                        contents += " ";
                    }else{
                        contents += recipelist.get(i).getText()+"|";
                    }
                    Log.d(TAG, "contents : "+ contents);
                }
                for (int i = 0; i < ingrelist.size(); i++){
                    ingredients += ingrelist.get(i).getText()+"|";
                    Log.d(TAG, "ingredients : "+ ingredients);
                }
                onCheckBoxClicked(oven_check);

                String imgs = "";
                int size = img_uri.size();
                for (int i = 0; i < size; i++){
                    if (img_uri.get(i) == null){
                        imgs += "|";
                    }else{
                        imgs += FireBase.firebaseUpload(v.getContext(),img_uri.get(i))+"|";
                    }
                }
                Log.d(TAG, "onClick: "+imgs);
                create(title_edit.getText().toString(), FireBase.firebaseUpload(v.getContext(), thunmbnail_uri)
                        ,imgs,detail_edit.getText().toString(),ingredients,contents,
                        category_num,time_text.getText().toString(),oven);
            }
        });

    }
    private String uploadFile(FirebaseStorage storage,Uri uri){
        String imgname = "";
        if(uri !=null){
            String uuid = UUID.randomUUID().toString();
            imgname = "images/"+uuid;
            StorageReference storageReference = storage.getReferenceFromUrl("gs://mobile-contents-812ea.appspot.com").child(imgname);

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Recipe_Insert_Activity.this, "업로드에 실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return imgname;
    }
    private void selectFile(FirebaseStorage storage){
        StorageReference storageReference = storage.getReference();

    }

    public void create(String name, String thumbnail,String image, String description,String ingredients ,String contents,
                       int category, String time, boolean useOven){
        int timevalue = Integer.parseInt(time.split("분")[0]);

        Recipe_Post recipe_post = new Recipe_Post(name, thumbnail,image,description,ingredients,contents,category,timevalue,useOven);
        Call<Recipe_Post> call = Recipe_Client.getApiService().recipe_post_call(token,recipe_post);

        call.enqueue(new Callback<Recipe_Post>() {
            @Override
            public void onResponse(Call<Recipe_Post> call, Response<Recipe_Post> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }else{
                    Log.d(TAG, "onResponse: 성공");
                    Toast.makeText(Recipe_Insert_Activity.this, "성공", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Recipe_Post> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 실패"+t.getMessage());
            }
        });
    }

    public Bitmap getBitmap(int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(Recipe_Insert_Activity.this, drawableId);

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
                            adapter.changeimg(recipe_position,img);
                            adapter.notifyDataSetChanged();
                            img_uri.set(recipe_position,data.getData());
                        }else{
                            Log.d(TAG, "onActivityResult: 썸네일");
                            thunmbnail_uri = data.getData();
                            main_img_btn.setImageBitmap(img);
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