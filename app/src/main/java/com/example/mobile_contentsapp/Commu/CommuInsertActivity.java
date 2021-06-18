package com.example.mobile_contentsapp.Commu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Create_Client;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Create_Post;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;

public class Commu_Insert_Activity extends AppCompatActivity {

    ArrayList<Commu_Image_Item> list;
    Commu_Image_Adapter adapter;
    ArrayList<Uri> uriList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu__insert_);

        RecyclerView imageRecycler = findViewById(R.id.commu_image_recycler);
        EditText titleEdit = findViewById(R.id.commu_create_tilte_edit);
        EditText contentsEdit = findViewById(R.id.commu_create_contents_edit);
        Button uplodeBtn = findViewById(R.id.commu_upload_btn);
        ImageButton imageAddBtn = findViewById(R.id.commu_image_add);

        LinearLayoutManager manager = new LinearLayoutManager(Commu_Insert_Activity.this ,LinearLayoutManager.HORIZONTAL,false);
        imageRecycler.setLayoutManager(manager);

        list = new ArrayList<>();
        adapter = new Commu_Image_Adapter(list);
        uriList = new ArrayList<>();

        imageRecycler.setAdapter(adapter);

        imageAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,101);
            }
        });
        uplodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allImageName = "";
                for (int i = 0; i < uriList.size(); i++){
                    allImageName += FireBase.firebaseUpload(v.getContext(),uriList.get(i))+"|";
                }
                uplode(titleEdit.getText().toString(),allImageName,contentsEdit.getText().toString());
            }
        });


    }

    public void uplode(String title, String image, String contents){
        Commu_Create_Post commu_create_post = new Commu_Create_Post(title, image, contents);
        Call<Commu_Create_Post> call = Commu_Create_Client.getApiService().commuCreateApi(tokenValue,commu_create_post);
        call.enqueue(new Callback<Commu_Create_Post>() {
            @Override
            public void onResponse(Call<Commu_Create_Post> call, Response<Commu_Create_Post> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                finish();
            }

            @Override
            public void onFailure(Call<Commu_Create_Post> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if(resultCode == RESULT_OK){
                InputStream in = null;
                try {
                    in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    uriList.add(data.getData());
                    list.add(new Commu_Image_Item(img));
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onActivityResult: 업로드");
                }catch (Exception e){
                    Log.d(TAG, "onActivityResult: "+e.getLocalizedMessage());
                    Log.d(TAG, "onActivityResult: "+e.getMessage());
                }
            }
        }
    }
}