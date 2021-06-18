package com.example.mobile_contentsapp.Commu;

import android.os.AsyncTask;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuSearchClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class CommuSearchTask extends AsyncTask<Void,Void, List<CommuListGet>> {
    private String keyword;
    private int start;

    public CommuSearchTask(String keyword, int start) {
        this.keyword = keyword;
        this.start = start;
    }

    @Override
    protected List<CommuListGet> doInBackground(Void... voids) {
        Call<List<CommuListGet>> call = CommuSearchClient.getApiService().commuSearchCall(keyword,start);
        try {
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
    protected void onPostExecute(List<CommuListGet> commu_list_tasks) {
        super.onPostExecute(commu_list_tasks);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(List<CommuListGet> commu_list_tasks) {
        super.onCancelled(commu_list_tasks);
    }
}
