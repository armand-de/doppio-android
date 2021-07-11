package com.example.mobile_contentsapp.Commu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuCreateClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuCreatePost;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;

public class CommuInsertActivity extends AppCompatActivity {

    private ArrayList<CommuImageItem> list;
    private CommuImageAdapter adapter;
    private ArrayList<Uri> uriList;
    private AlertDialog dialog;

    private boolean isUpload = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu_create);

        RecyclerView imageRecycler = findViewById(R.id.commu_image_recycler);
        EditText titleEdit = findViewById(R.id.commu_create_tilte_edit);
        EditText contentsEdit = findViewById(R.id.commu_create_contents_edit);
        Button uplodeBtn = findViewById(R.id.commu_upload_btn);
        ImageButton imageAddBtn = findViewById(R.id.commu_image_add);

        LinearLayoutManager manager = new LinearLayoutManager(CommuInsertActivity.this ,LinearLayoutManager.HORIZONTAL,false);
        imageRecycler.setLayoutManager(manager);

        list = new ArrayList<>();
        adapter = new CommuImageAdapter(list);
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
                if (!isUpload){
                    isUpload = true;
                    if (titleEdit.getText().length() == 0 || titleEdit.getText().length() > 40
                            || contentsEdit.getText().length() == 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CommuInsertActivity.this)
                                .setMessage("정확한 내용을 입력해주세요")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        dialog = builder.create();
                        dialog.show();
                        return;
                    }
                    ProgressDialog dialog = new ProgressDialog(CommuInsertActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("로딩중입니다.");

                    String allImageName = "";
                    for (int i = 0; i < uriList.size(); i++){
                        allImageName += FireBase.firebaseUpload(v.getContext(),uriList.get(i))+"|";
                    }
                    uplode(titleEdit.getText().toString(),allImageName,contentsEdit.getText().toString());
                }
            }
        });


    }

    public void uplode(String title, String image, String contents){
        CommuCreatePost commuCreatePost = new CommuCreatePost(title, image, contents);
        Call<CommuCreatePost> call = CommuCreateClient.getApiService().commuCreateApi(tokenValue,commuCreatePost);
        call.enqueue(new Callback<CommuCreatePost>() {
            @Override
            public void onResponse(Call<CommuCreatePost> call, Response<CommuCreatePost> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(CommuInsertActivity.this, "업로드에 실패했습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                isUpload = false;
                dialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<CommuCreatePost> call, Throwable t) {
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
                    list.add(new CommuImageItem(img));
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