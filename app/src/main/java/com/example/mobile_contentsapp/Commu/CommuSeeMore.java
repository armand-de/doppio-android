package com.example.mobile_contentsapp.Commu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentCountClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentCountGet;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentListClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentListGet;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentPost;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuCommentPostClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuFindClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuFindGet;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuHeartDownClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuHeartSelectClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuHeartSelectGet;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuHeartUp;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuHeartUpClient;
import com.example.mobile_contentsapp.Main.FireBase;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;

public class CommuActivity extends AppCompatActivity {

    private int id;
    private int start = -1;
    private boolean ispost = false;
    private boolean leftList = false;
    private boolean isLoading = false;
    private boolean isHeart = false;
    private int commuId;

    private TextView titleText;
    private TextView contentsText;
    private TextView heartText;
    private TextView commentText;
    private TextView nickname;
    private ViewPager2 pager2;
    private RecyclerView commentList;
    private ImageButton heartBtn;
    private ImageView profile;

    private CommuCommentAdapter adapter;
    private ArrayList<CommuCommentListGet> list;
    private List<CommuCommentListGet> listGet;

    private ArrayList<String> imageList;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commu_see_more);

        pager2 = findViewById(R.id.commu_detail_pager);
        titleText = findViewById(R.id.commu_detail_title);
        contentsText = findViewById(R.id.commu_detail_contents);
        heartText = findViewById(R.id.commu_detail_heart_text);
        commentText = findViewById(R.id.commu_detail_comment_text);
        commentList = findViewById(R.id.commu_comment_recycler);

        nickname = findViewById(R.id.commu_detail_nickname);
        profile = findViewById(R.id.commu_detail_profile);
        heartBtn = findViewById(R.id.commu_detail_heart);

        EditText commentEdit = findViewById(R.id.commu_comment_edit);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        ImageButton backBtn = findViewById(R.id.commu_detail_back_btn);
        ImageButton commentPostBtn = findViewById(R.id.post_comment);
        SwipeRefreshLayout swipe = findViewById(R.id.commu_swipe);

        Intent intent = getIntent();
        id = intent.getIntExtra("commuId", 0);
        findCommu(id);

        LinearLayoutManager manager = new LinearLayoutManager(CommuActivity.this, LinearLayoutManager.VERTICAL, false);
        commentList.setLayoutManager(manager);

        imageList = new ArrayList<>();
        list = new ArrayList<>();
        adapter = new CommuCommentAdapter(list);
        commentList.setAdapter(adapter);

        commentCount();

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        commentEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
                }
                return true;
            }
        });

        commentPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentEdit.clearFocus();
                if (!ispost){
                    ispost = true;
                    postComment(commentEdit.getText().toString(),commuId,commentEdit);
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(),0);
                    commentCount();
                }
            }
        });

        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHeart){
                    isHeart = !isHeart;
                    heartUp();
                    heartBtn.setImageDrawable(ContextCompat.getDrawable(CommuActivity.this,R.drawable.ic_selectheart));
                    int heartValue = Integer.parseInt(heartText.getText().toString())+1;
                    heartText.setText(String.valueOf(heartValue));
                }else{
                    isHeart = !isHeart;
                    heartDown();
                    heartBtn.setImageDrawable(ContextCompat.getDrawable(CommuActivity.this,R.drawable.ic_heart));
                    int heartValue = Integer.parseInt(heartText.getText().toString())-1;
                    if (heartValue == 0){
                        heartValue = 0;
                    }
                    heartText.setText(String.valueOf(heartValue));
                }
            }

        });
        commentList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (manager != null && manager.findLastCompletelyVisibleItemPosition() == list.size() - 1) {
                        Log.d(TAG, "onScrolled: scroll");
                        if (leftList){
                            loadMore();
                            isLoading = true;
                        }
                    }
                }
            }


        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setComment();
                swipe.setRefreshing(false);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void postComment(String contents,int id,EditText commentEdit){
        CommuCommentPost commu_comment_post = new CommuCommentPost(id,contents);
        Call<CommuCommentPost> call = CommuCommentPostClient
                .getApiService().commuCommentPostCall(tokenValue ,commu_comment_post);
        call.enqueue(new Callback<CommuCommentPost>() {
            @Override
            public void onResponse(Call<CommuCommentPost> call, Response<CommuCommentPost> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    Log.d(TAG, "onResponse: "+tokenValue);
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                list.clear();
                setComment();
                adapter.notifyDataSetChanged();
                commentList.setAdapter(adapter);
                commentEdit.setText("");
                ispost = false;

            }

            @Override
            public void onFailure(Call<CommuCommentPost> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러");
            }
        });
    }

    public void HeartSelect(){
        Call<CommuHeartSelectGet> call = CommuHeartSelectClient.getApiService().commuHeartSelectApiCall(tokenValue,commuId);
        call.enqueue(new Callback<CommuHeartSelectGet>() {
            @Override
            public void onResponse(Call<CommuHeartSelectGet> call, Response<CommuHeartSelectGet> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                isHeart = response.body().isExist;
                if (isHeart){
                    heartBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_selectheart));
                }else{
                    heartBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_heart));
                }

            }

            @Override
            public void onFailure(Call<CommuHeartSelectGet> call, Throwable t) {

            }
        });
    }

    public void findCommu(int id){
        Call<CommuFindGet> call = CommuFindClient.getApiService().commuFindApiCall(id);
        call.enqueue(new Callback<CommuFindGet>() {
            @Override
            public void onResponse(Call<CommuFindGet> call, Response<CommuFindGet> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    finish();
                    Toast.makeText(CommuActivity.this, "게시물이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                titleText.setText(response.body().getTitle());
                contentsText.setText(response.body().getContents());
                String[] allImage = response.body().getImage().split("\\|");
                if (response.body().getImage().isEmpty()){
                    pager2.setVisibility(View.GONE);
                }else{
                    for (int i = 0; i <allImage.length; i++){
                        if (!allImage[i].isEmpty()){
                            imageList.add(allImage[i]);
                        }
                    }
                }

                pager2.setOffscreenPageLimit(1);
                pager2.setAdapter(new CommuImageSlideAdapter(CommuActivity.this,imageList));

                commuId = response.body().getId();
                HeartSelect();
                heartText.setText(String.valueOf(response.body().getPreference()));
                nickname.setText(response.body().getUser().getNickname());
                if (response.body().getUser().getImage() != null){
                    FireBase.firebaseDownlode(CommuActivity.this,response.body().getUser().getImage(),profile);
                }
            }

            @Override
            public void onFailure(Call<CommuFindGet> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러");
                finish();
                Toast.makeText(CommuActivity.this, "서버가 불안정합니다", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void commentCount(){
        Call<CommuCommentCountGet> call = CommuCommentCountClient.getApiService().commuCommentCountCall(id);
        call.enqueue(new Callback<CommuCommentCountGet>() {
            @Override
            public void onResponse(Call<CommuCommentCountGet> call, Response<CommuCommentCountGet> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                commentText.setText(String.valueOf(response.body().getCount()));
                if (response.body().getCount() != 0){
                    setComment();
                }
            }

            @Override
            public void onFailure(Call<CommuCommentCountGet> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });
    }

    public void heartUp(){
        CommuHeartUp commu_heart = new CommuHeartUp(commuId);
        Call<CommuHeartUp> call = CommuHeartUpClient.getApiService().commuHeartUpApiCall(tokenValue,commu_heart);
        call.enqueue(new Callback<CommuHeartUp>() {
            @Override
            public void onResponse(Call<CommuHeartUp> call, Response<CommuHeartUp> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }else{
                    Log.d(TAG, "onResponse: 성공");
                }
            }

            @Override
            public void onFailure(Call<CommuHeartUp> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러");
            }
        });
    }

    public void heartDown(){
        CommuHeartUp commu_heart = new CommuHeartUp(commuId);
        Call<CommuHeartUp> call = CommuHeartDownClient.getApiService().commuHeartDownApiCall(tokenValue,commu_heart);
        call.enqueue(new Callback<CommuHeartUp>() {
            @Override
            public void onResponse(Call<CommuHeartUp> call, Response<CommuHeartUp> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }else{
                    Log.d(TAG, "onResponse: 성공");
                }
            }

            @Override
            public void onFailure(Call<CommuHeartUp> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러");
            }
        });
    }

    public void setComment(){
        list.add(null);
        adapter.notifyDataSetChanged();

        Call<List<CommuCommentListGet>> call =
                CommuCommentListClient.getApiService().commuCommentListApiCall(tokenValue,id,-1);
        call.enqueue(new Callback<List<CommuCommentListGet>>() {
            @Override
            public void onResponse(Call<List<CommuCommentListGet>> call, Response<List<CommuCommentListGet>> response) {
                if (!response.isSuccessful()){
                    return;
                }
                listGet = response.body();
                list.clear();
                adapter.notifyDataSetChanged();

                if (listGet.size() != 0){
                    start = listGet.get(listGet.size()-1).getId();
                    for (int i = 0; i < listGet.size(); i++){
                        list.add(listGet.get(i));
                    }
                    adapter.notifyDataSetChanged();
                    leftList = true;
                }else{
                    leftList = false;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CommuCommentListGet>> call, Throwable t) {

            }
        });
        isLoading = false;
    }

    public void loadMore(){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Call<List<CommuCommentListGet>> call =
                CommuCommentListClient.getApiService().commuCommentListApiCall(tokenValue,id,start);
        call.enqueue(new Callback<List<CommuCommentListGet>>() {
            @Override
            public void onResponse(Call<List<CommuCommentListGet>> call, Response<List<CommuCommentListGet>> response) {
                if (!response.isSuccessful()){
                    return;
                }
                listGet = response.body();

                if (listGet.size() != 0){
                    list.remove(list.size()-1);
                    adapter.notifyItemRemoved(list.size());
                    
                    start = listGet.get(listGet.size()-1).getId();
                    for (int i = 0; i < listGet.size(); i++){
                        list.add(listGet.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    leftList = false;
                }
            }

            @Override
            public void onFailure(Call<List<CommuCommentListGet>> call, Throwable t) {

            }
        });

        list.remove(list.size()-1);
        adapter.notifyItemRemoved(list.size());

        isLoading = false;
    }
}
