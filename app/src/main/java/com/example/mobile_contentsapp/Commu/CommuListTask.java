package com.example.mobile_contentsapp.Commu;

import android.os.AsyncTask;

import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Create_Post;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_List_Client;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_List_Get;

import java.util.List;

import retrofit2.Call;

public class Commu_List_Task extends AsyncTask<Void, Void, List<Commu_List_Get>> {

    private int start;

    public Commu_List_Task(int start) {
        this.start = start;
    }

    @Override
    protected List<Commu_List_Get> doInBackground(Void... voids) {
        Call<List<Commu_List_Get>> call = Commu_List_Client.getApiService().commuListApiCall(start);
        try{
            return call.execute().body();
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Commu_List_Get> commu_list_gets) {
        super.onPostExecute(commu_list_gets);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(List<Commu_List_Get> commu_list_gets) {
        super.onCancelled(commu_list_gets);
    }
}
