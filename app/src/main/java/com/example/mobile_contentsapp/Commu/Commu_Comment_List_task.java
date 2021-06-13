package com.example.mobile_contentsapp.Commu;

import android.os.AsyncTask;

import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Comment_List_Api;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Comment_List_Client;
import com.example.mobile_contentsapp.Commu.Retrofit.Commu_Comment_List_Get;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class Commu_Comment_List_task extends AsyncTask<Void, Void, List<Commu_Comment_List_Get>> {

    private int id;

    public Commu_Comment_List_task(int id) {
        this.id = id;
    }

    @Override
    protected List<Commu_Comment_List_Get> doInBackground(Void... voids) {
        Call<List<Commu_Comment_List_Get>> call = Commu_Comment_List_Client.getApiService().commuCommentListApiCall(id);

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
    protected void onPostExecute(List<Commu_Comment_List_Get> commu_comment_list_gets) {
        super.onPostExecute(commu_comment_list_gets);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(List<Commu_Comment_List_Get> commu_comment_list_gets) {
        super.onCancelled(commu_comment_list_gets);
    }
}
