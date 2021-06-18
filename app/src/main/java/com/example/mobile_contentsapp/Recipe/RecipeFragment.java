package com.example.mobile_contentsapp.Recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeListClient;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeListGet;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeSearchClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class RecipeFragment extends Fragment {
    private int start = -1;
    private int categoryNumber;
    private List<RecipeListGet> listGet;
    private ArrayList<RecipeListGet> list;
    private RecipeListAdapter adapter;

    private ImageButton searchBtn;
    private RecyclerView recipeRecyclerView;
    private String keyword;
    private boolean remainList = false;
    private boolean isSearch = false;
    private boolean isLoding = false;

    @Override
    public void onResume() {
        super.onResume();
        setList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment,container,false);

        recipeRecyclerView = view.findViewById(R.id.recipe_list_recycler);
        searchBtn = view.findViewById(R.id.recipe_search);
        SwipeRefreshLayout swipe = view.findViewById(R.id.recipe_swipe);
        Spinner categorySpinner = view.findViewById(R.id.search_category_spinner);
        EditText recipeSearchEdit = view.findViewById(R.id.recipe_search_edit);

        ArrayList<CategoryItem> categoryList = new ArrayList<>();
        setCategory(categoryList);
        CategoryAdapter categoryAdapter = new CategoryAdapter(view.getContext(),categoryList);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recipeRecyclerView.setLayoutManager(manager);
        adapter = new RecipeListAdapter(list);

        recipeRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoding){
                    if (manager != null && manager.findLastCompletelyVisibleItemPosition() == list.size()-1 &&
                            remainList){
                        if(!isSearch){
                            loadMore();
                        }else{
                            searchMore(keyword,start);
                        }
                        isLoding = true;
                    }
                }
            }
        });
        recipeRecyclerView.setAdapter(adapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = recipeSearchEdit.getText().toString();
                if (keyword.isEmpty() && categoryNumber == 0){
                    isSearch = false;
                    setList();
                }else{
                    isSearch = true;
                    search();
                }
                adapter.notifyDataSetChanged();
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setList();
                swipe.setRefreshing(false);
            }
        });

        return view;
    }
    public void setList(){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Call<List<RecipeListGet>> call = RecipeListClient.getApiService().recipeListCall(-1);
        call.enqueue(new Callback<List<RecipeListGet>>() {
            @Override
            public void onResponse(Call<List<RecipeListGet>> call, Response<List<RecipeListGet>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "게시물을 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
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
                    remainList = true;
                }else{
                    remainList = false;
                }
            }

            @Override
            public void onFailure(Call<List<RecipeListGet>> call, Throwable t) {
                Toast.makeText(getContext(), "알 수 없는 오류 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setCategory(ArrayList<CategoryItem> categoryList){
        categoryList.add(new CategoryItem("전체",0,1));
        categoryList.add(new CategoryItem("커피",1,1));
        categoryList.add(new CategoryItem("음료",2,1));
        categoryList.add(new CategoryItem("디저트",3,1));
        categoryList.add(new CategoryItem("그 외",4,1));

    }

    public void loadMore(){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Call<List<RecipeListGet>> call = RecipeListClient.getApiService().recipeListCall(start);
        call.enqueue(new Callback<List<RecipeListGet>>() {
            @Override
            public void onResponse(Call<List<RecipeListGet>> call, Response<List<RecipeListGet>> response) {
                if(!response.isSuccessful()){
                }
                listGet = response.body();
                list.remove(list.size()-1);
                adapter.notifyItemRemoved(list.size());
                if (listGet.size() != 0){
                    start = listGet.get(listGet.size()-1).getId();
                    for (int i = 0; i < listGet.size(); i++){
                        list.add(listGet.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    remainList = false;
                }
            }

            @Override
            public void onFailure(Call<List<RecipeListGet>> call, Throwable t) {
            }
        });
        isLoding = false;

    }

    public void search(){
        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("category", categoryNumber);
        map.put("start", String.valueOf(-1));

        Call<List<RecipeListGet>> call = RecipeSearchClient.getApiService().recipeSearchCall(map);
        call.enqueue(new Callback<List<RecipeListGet>>() {
            @Override
            public void onResponse(Call<List<RecipeListGet>> call, Response<List<RecipeListGet>> response) {
                if (!response.isSuccessful()){
                    return;
                }
                listGet = response.body();
                list.remove(list.size()-1);
                adapter.notifyItemRemoved(list.size());
                if (listGet.size() != 0){
                    start = listGet.get(listGet.size()-1).getId();
                    for (int i = 0;  i < listGet.size(); i++){
                        list.add(listGet.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    remainList = false;
                }
            }
            @Override
            public void onFailure(Call<List<RecipeListGet>> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });
    }

    public void searchMore(String keyword, int startValue){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        isLoding = true;

        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("category", categoryNumber);
        map.put("start", String.valueOf(startValue));

        Call<List<RecipeListGet>> call = RecipeSearchClient.getApiService().recipeSearchCall(map);
        call.enqueue(new Callback<List<RecipeListGet>>() {
            @Override
            public void onResponse(Call<List<RecipeListGet>> call, Response<List<RecipeListGet>> response) {
                if (!response.isSuccessful()){
                }
                Log.d(TAG, "onResponse: 성공");
                listGet = response.body();
                list.remove(list.size()-1);
                adapter.notifyItemRemoved(list.size());
                if (listGet.size() != 0){
                    start = listGet.get(listGet.size()-1).getId();
                    for (int i = 0;  i < listGet.size(); i++){
                        list.add(listGet.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    remainList = false;
                }
            }
            @Override
            public void onFailure(Call<List<RecipeListGet>> call, Throwable t) {
            }
        });
        isLoding = false;
    }

}
