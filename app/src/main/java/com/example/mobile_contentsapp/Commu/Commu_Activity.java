package com.example.mobile_contentsapp.Commu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Comment_List_Client;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Comment_List_Get;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Find_Client;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Find_Get;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_List_Client;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_List_Get;
import com.example.mobile_contentsapp.Login.Sign_in_Activity;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Commu_Activity extends Activity {

    private int start;
    private boolean isLoding;

    private TextView titleText;
    private TextView contentsText;
    private RecyclerView commentList;
    private List<Commu_Comment_List_Get> listGet;
    private Commu_Comment_Adapter adapter;
    private ArrayList<Commu_Comment_List_Get> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commu_detail);

        titleText = findViewById(R.id.commu_detail_title);
        contentsText = findViewById(R.id.commu_detail_contents);
        commentList = findViewById(R.id.commu_comment_recycler);

        Intent intent = getIntent();
        int id = intent.getIntExtra("commuId",0);
        findCommu(id);

        LinearLayoutManager manager = new LinearLayoutManager(Commu_Activity.this,LinearLayoutManager.VERTICAL,false);
        commentList.setLayoutManager(manager);

        list = new ArrayList<>();
        adapter = new Commu_Comment_Adapter(list);
        commentList.setAdapter(adapter);

        try {
            list.add(null);
            adapter.notifyItemInserted(list.size()-1);

            listGet = new Commu_Comment_List_task(-1).get();
            list.remove(list.size()-1);
            adapter.notifyItemRemoved(list.size());
            if (listGet.size() != 0){
                start = listGet.get(listGet.size()-1).getId();
                for (int i = 0; i < listGet.size(); i++){
                    list.add(listGet.get(i));
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        commentList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoding){
                    if (manager != null && manager.findLastCompletelyVisibleItemPosition() == list.size()-1){
                        try {
                            listGet = new Commu_Comment_List_task(start).execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (listGet != null){
                            if (listGet.size() != 0){
                                loadMore();
                                start = listGet.get(listGet.size()-1).getId();
                                isLoding = false;
                            }
                        }
                    }
                }

            }
        });

    }
    public void findCommu(int id){
        Call<Commu_Find_Get> call = Commu_Find_Client.getApiService().commuFindApiCall(Sign_in_Activity.tokenValue,id);
        call.enqueue(new Callback<Commu_Find_Get>() {
            @Override
            public void onResponse(Call<Commu_Find_Get> call, Response<Commu_Find_Get> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                titleText.setText(response.body().getTitle());
                contentsText.setText(response.body().getContents());
            }

            @Override
            public void onFailure(Call<Commu_Find_Get> call, Throwable t) {

                Log.d(TAG, "onFailure: 시스템 에러");
            }

        });
    }

    public void loadMore(){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.remove(list.size()-1);
                int scrollPosition = list.size();
                adapter.notifyItemRemoved(scrollPosition);

                for (int i = 0; i < listGet.size(); i++){
                    list.add(listGet.get(i));
                    Log.d(TAG, "run: "+listGet.get(i));
                }
                adapter.notifyDataSetChanged();
                isLoding = false;
            }
        },2000);

    }
}
