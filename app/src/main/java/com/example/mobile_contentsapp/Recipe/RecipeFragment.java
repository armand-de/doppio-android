package com.example.mobile_contentsapp.Recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobile_contentsapp.Profile.RecylcerViewEmpty;
import com.example.mobile_contentsapp.R;
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

    private Spinner categorySpinner;
    private ImageButton searchBtn;
    private RecylcerViewEmpty recipeRecyclerView;
    private String keyword;
    private boolean remainList = false;
    private boolean isSearch = false;
    private boolean isLoding = false;


    @Override
    public void onStart() {
        super.onStart();
        if (!isLoding) {
            isLoding = true;
            categorySpinner.setSelection(0);
            list.clear();
            adapter.notifyDataSetChanged();
            searchMore(-1, null, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe,container,false);

        recipeRecyclerView = view.findViewById(R.id.recipe_list_recycler);
        TextView emptyText = view.findViewById(R.id.recipe_empty_text);
        searchBtn = view.findViewById(R.id.recipe_search);
        SwipeRefreshLayout swipe = view.findViewById(R.id.recipe_swipe);
        categorySpinner = view.findViewById(R.id.search_category_spinner);
        EditText recipeSearchEdit = view.findViewById(R.id.recipe_search_edit);

        ArrayList<CategoryItem> categoryList = new ArrayList<>();
        setCategory(categoryList);
        CategoryAdapter categoryAdapter = new CategoryAdapter(view.getContext(),categoryList);
        categorySpinner.setAdapter(categoryAdapter);

        recipeSearchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    keyword = recipeSearchEdit.getText().toString();
                    searchMore(-1,keyword,categoryNumber);
                    adapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryNumber = position;
                list.clear();
                adapter.notifyDataSetChanged();
                searchMore(-1,null,categoryNumber);
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
                        isLoding = true;
                        searchMore(start,keyword,categoryNumber);
                    }
                }
            }
        });
        recipeRecyclerView.setAdapter(adapter);
        recipeRecyclerView.setEmptyView(emptyText);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                adapter.notifyDataSetChanged();
                keyword = recipeSearchEdit.getText().toString();
                searchMore(-1,keyword,categoryNumber);
                adapter.notifyDataSetChanged();
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                categorySpinner.setSelection(0);
                list.clear();
                adapter.notifyDataSetChanged();
                searchMore(-1,null,0);
                swipe.setRefreshing(false);
            }
        });

        return view;
    }
    private void setCategory(ArrayList<CategoryItem> categoryList){
        categoryList.add(new CategoryItem("전체",0,1));
        categoryList.add(new CategoryItem("커피",1,1));
        categoryList.add(new CategoryItem("음료",2,1));
        categoryList.add(new CategoryItem("디저트",3,1));
        categoryList.add(new CategoryItem("그 외",4,1));

    }

    private void searchMore(int startValue, String keyword, int categoryNumber){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Call<List<RecipeListGet>> call = RecipeSearchClient.getApiService().recipeSearchCall(String.valueOf(startValue),keyword,categoryNumber);
        call.enqueue(new Callback<List<RecipeListGet>>() {
            @Override
            public void onResponse(Call<List<RecipeListGet>> call, Response<List<RecipeListGet>> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
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
