package com.example.mobile_contentsapp.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mobile_contentsapp.Main.SplashActivity;
import com.example.mobile_contentsapp.Profile.Retrofit.MyRecipeListClient;
import com.example.mobile_contentsapp.Profile.Retrofit.RecipeDeleteClient;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeListGet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;

public class My_Recipe_Fragment extends Fragment {

    private String userId;
    private MyRecipeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_recipe,container,false);

        Bundle bundle = getArguments();
        if (bundle != null){
            userId = bundle.getString("userid");
        }

        RecylcerViewEmpty recipeList = view.findViewById(R.id.profile_my_recipe_recycler);
        TextView emptyText = view.findViewById(R.id.recipe_empty_text);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recipeList.setLayoutManager(manager);


        ArrayList<RecipeListGet> list = new ArrayList<>();
        adapter = new MyRecipeAdapter(list);
        recipeList.setAdapter(adapter);
        recipeList.setEmptyView(emptyText);

        adapter.setOnClickListener(new MyRecipeAdapter.OnLongClickListener() {
            @Override
            public void OnClick(View view, int pos, int id) {
                        if (userId.equals(SplashActivity.userId)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                                    .setMessage("삭제하시겠습니까?")
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            list.remove(pos);
                                            adapter.notifyItemRemoved(pos);
                                            deleteRecipe(id);
                                        }
                                    });
                            Dialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

        findMyRecipe(list);
        return view;
    }

    public void findMyRecipe(ArrayList<RecipeListGet> list){
        Call<List<RecipeListGet>> call = MyRecipeListClient.getApiService().myRecipeListCall(userId);
        call.enqueue(new Callback<List<RecipeListGet>>() {
            @Override
            public void onResponse(Call<List<RecipeListGet>> call, Response<List<RecipeListGet>> response) {
                if (!response.isSuccessful()){
                    return;
                }
                for (int i = 0; i < response.body().size(); i++){
                    list.add(response.body().get(i));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RecipeListGet>> call, Throwable t) {

            }
        });
    }
    public void deleteRecipe(int id){
        Call<Void> call = RecipeDeleteClient.getApiService().deleterecipe(tokenValue,id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
