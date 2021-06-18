package com.example.mobile_contentsapp.Commu;

import android.os.AsyncTask;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuListClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;

import java.util.List;

import retrofit2.Call;

public class CommuListTask extends AsyncTask<Void, Void, List<CommuListGet>> {

    private int start;

    public CommuListTask(int start) {
        this.start = start;
    }

    @Override
    protected List<CommuListGet> doInBackground(Void... voids) {
        Call<List<CommuListGet>> call = CommuListClient.getApiService().commuListApiCall(start);
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
    protected void onPostExecute(List<CommuListGet> commu_list_gets) {
        super.onPostExecute(commu_list_gets);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(List<CommuListGet> commu_list_gets) {
        super.onCancelled(commu_list_gets);
    }
}
