package com.example.mobile_contentsapp.Recipe;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.Page_Client;
import com.example.mobile_contentsapp.Recipe.Retrofit.Page_Get;
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_List_Client;
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_List_Get;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Recipe_Fragment extends Fragment {
    private int count;
    private List<Recipe_List_Get> list_get;
    private ArrayList<Recipe_List_Get> list;
    private Recipe_List_Adapter adapter;
    private RecyclerView recyclerView;
    boolean isLoding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment,container,false);
        isLoding = false;
        recyclerView = view.findViewById(R.id.recipelist_recycler);

        list = new ArrayList<>();
        page_amount(list);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter = new Recipe_List_Adapter(list);


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        if (count >= 1){
                            recipe_list(count);
                            loadMore(list,adapter);
                            count--;
                            isLoding = false;
                        }
                    }
                }

            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
    public void page_amount(ArrayList<Recipe_List_Get> list){
        Call<Page_Get> call = Page_Client.getApiService().page_amount_get_call();
        call.enqueue(new Callback<Page_Get>() {
            @Override
            public void onResponse(Call<Page_Get> call, Response<Page_Get> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패 "+ response.code());
                }else{
                    count = response.body().getAmount();

                    Log.d(TAG, "page_onResponse: 성공"+count);
                    recipe_list(count);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < list_get.size(); i++){
                                list.add(list_get.get(i));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    },3000);
                    count--;
                }
            }

            @Override
            public void onFailure(Call<Page_Get> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });
    }
    public void recipe_list(int amount){
        Call<List<Recipe_List_Get>> call = Recipe_List_Client.getApiService().recipe_list_get_call(amount);
        call.enqueue(new Callback<List<Recipe_List_Get>>() {
            @Override
            public void onResponse(Call<List<Recipe_List_Get>> call, Response<List<Recipe_List_Get>> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패 "+response.code());
                }else{
                    Log.d(TAG, "recipe_onResponse: 성공 ");
                    list_get = response.body();
                }
            }
            @Override
            public void onFailure(Call<List<Recipe_List_Get>> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러" + t.getMessage());
            }
        });
    }
    public void loadMore(ArrayList<Recipe_List_Get> list, Recipe_List_Adapter adapter){
        list.add(null);
        adapter.notifyItemInserted(list.size()+1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.remove(list.size()+1);
                int scrollPosition = list.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + count-1*15;

                for (int i = 0; i < 15; i++){
                    list.add(list_get.get(i));
                    Log.d(TAG, "run: "+list_get.get(i));
                }
                adapter.notifyDataSetChanged();
                isLoding = false;
            }
        },2000);

    }
    public void listRefresh(){
        adapter = new Recipe_List_Adapter(list);
        adapter.notifyDataSetChanged();
    }
}
