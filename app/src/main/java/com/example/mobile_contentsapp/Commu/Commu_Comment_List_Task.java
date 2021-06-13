package com.example.mobile_contentsapp.Commu.Retrofit;

import android.os.AsyncTask;

import com.example.mobile_contentsapp.Login.Sign_in_Activity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class Commu_Comment_List_Task extends AsyncTask<Void, Void, List<Commu_Comment_List_Get>> {

    private int id;
    private int start;

    public Commu_Comment_List_Task(int id, int start) {
        this.id = id;
        this.start = start;
    }

    @Override
    protected List<Commu_Comment_List_Get> doInBackground(Void... voids) {
        Call<List<Commu_Comment_List_Get>> call = Commu_Comment_List_Client.getApiService().commuCommentListApiCall(Sign_in_Activity.tokenValue
        ,id,start);
        try {
            if (call.execute().isSuccessful()){
            return call.execute().body();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
