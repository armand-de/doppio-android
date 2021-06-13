package com.example.mobile_contentsapp.Recipe;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_List_Client;
import com.example.mobile_contentsapp.Recipe.Retrofit.Recipe_List_Get;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static android.content.ContentValues.TAG;

public class Recipe_List_Task extends AsyncTask<Void, Void, List<Recipe_List_Get>> {
    private int count;

    public Recipe_List_Task(int count) {
        this.count = count;
    }

    @Override
    protected List<Recipe_List_Get> doInBackground(Void... voids) {
        Call<List<Recipe_List_Get>> call = Recipe_List_Client.getApiService().recipe_list_get_call(count);
        try {
            Log.d(TAG, "doInBackground: 성공");
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Recipe_List_Get> recipe_list_gets) {
        super.onPostExecute(recipe_list_gets);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(List<Recipe_List_Get> recipe_list_gets) {
        super.onCancelled(recipe_list_gets);
    }
}
