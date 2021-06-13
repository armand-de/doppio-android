package com.example.mobile_contentsapp.Recipe;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_List_Get;
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_Search_Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Recipe_Fragment extends Fragment {
    private int start;
    private int categorynumber;
    private List<Recipe_List_Get> list_get;
    private ArrayList<Recipe_List_Get> list;
    private Recipe_List_Adapter adapter;

    private ImageButton searchBtn;
    private RecyclerView recyclerView;
    boolean isLoding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment,container,false);

        isLoding = false;
        recyclerView = view.findViewById(R.id.recipe_list_recycler);
        Spinner categorySpinner = view.findViewById(R.id.search_category_spinner);
        EditText recipeSearchEdit = view.findViewById(R.id.recipe_search_edit);
        searchBtn = view.findViewById(R.id.recipe_search);

        ArrayList<Category_Item> categoryList = new ArrayList<>();
        setCategory(categoryList);
        Category_Adapter categoryAdapter = new Category_Adapter(view.getContext(),categoryList);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorynumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter = new Recipe_List_Adapter(list);

        try {
            list.add(null);
            adapter.notifyItemInserted(list.size()-1);

            list_get = new Recipe_List_Task(-1).execute().get();
            list.remove(list.size()-1);
            adapter.notifyItemRemoved(list.size());
            if (list_get != null){
                start = list_get.get(list_get.size()-1).getId();
                Log.d(TAG, "onCreateView: "+ start);

                for (int i = 0;  i < list_get.size(); i++){
                    list.add(list_get.get(i));
                    Log.d(TAG, "run: "+list_get.get(i));
                }

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



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

                        try {
                            list_get = new Recipe_List_Task(start).execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (list_get != null){
                            if (list_get.size() != 0){
                                loadMore(list,adapter);
                                start = list_get.get(list_get.size()-1).getId();
                                isLoding = false;
                            }
                        }
                    }
                }

            }
        });
        recyclerView.setAdapter(adapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(recipeSearchEdit.getText().toString(), categorynumber);
                adapter.update();
            }
        });


        return view;
    }
    public void setCategory(ArrayList<Category_Item> categoryList){
        categoryList.add(new Category_Item("전체",4,1));
        categoryList.add(new Category_Item("커피",1,1));
        categoryList.add(new Category_Item("음료",2,1));
        categoryList.add(new Category_Item("디저트",3,1));
        categoryList.add(new Category_Item("그 외",4,1));

    }

    public void loadMore(ArrayList<Recipe_List_Get> list, Recipe_List_Adapter adapter){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.remove(list.size()-1);
                int scrollPosition = list.size();
                adapter.notifyItemRemoved(scrollPosition);

                for (int i = 0; i < list_get.size(); i++){
                    list.add(list_get.get(i));
                    Log.d(TAG, "run: "+list_get.get(i));
                }
                adapter.notifyDataSetChanged();
                isLoding = false;
            }
        },2000);

    }

    public void search(String keyword,int category){
        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("category", category);
        map.put("start", String.valueOf(start));

        Call<List<Recipe_List_Get>> call = Recipe_Search_Client.getApiService().recipe_SearchGetCall(map);
        call.enqueue(new Callback<List<Recipe_List_Get>>() {
            @Override
            public void onResponse(Call<List<Recipe_List_Get>> call, Response<List<Recipe_List_Get>> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패" + response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                list_get = response.body();
                list.clear();
                for (int i = 0;  i < list_get.size(); i++){
                    list.add(list_get.get(i));
                    Log.d(TAG, "run: "+list_get.get(i));
                }
            }
            @Override
            public void onFailure(Call<List<Recipe_List_Get>> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });
    }

    public void listRefresh(){
        adapter = new Recipe_List_Adapter(list);
        adapter.notifyDataSetChanged();
    }
}
