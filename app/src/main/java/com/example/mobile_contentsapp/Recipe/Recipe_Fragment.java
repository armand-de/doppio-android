package com.example.mobile_contentsapp.Recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    int amount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment,container,false);
        int amount = 0;
        RecyclerView recyclerView = view.findViewById(R.id.recipelist_recycler);

        ArrayList<Recipe_List_Get> list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        page_amount();

        Recipe_List_Adapter adapter = new Recipe_List_Adapter(list);

        return view;
    }
    public void page_amount(){
        Call<Page_Get> call = Page_Client.getApiService().page_amount_get_call();
        call.enqueue(new Callback<Page_Get>() {
            @Override
            public void onResponse(Call<Page_Get> call, Response<Page_Get> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패 "+ response.code());
                }else{
                    Log.d(TAG, "onResponse: 성공");
                    amount = response.body().getAmount();
                    recipe_list(amount);
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
                    Log.d(TAG, "onResponse: 성공 ");
                }
            }
            @Override
            public void onFailure(Call<List<Recipe_List_Get>> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러" + t.getMessage());
            }
        });
    }
}
