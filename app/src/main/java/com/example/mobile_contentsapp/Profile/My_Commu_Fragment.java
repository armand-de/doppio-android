package com.example.mobile_contentsapp.Profile;

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

import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;
import com.example.mobile_contentsapp.Profile.Retrofit.MyCommuListClient;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;

public class My_Commu_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_commu,container,false);
        RecylcerViewEmpty recipeList = view.findViewById(R.id.profile_my_commu_recycler);
        TextView emptyText = view.findViewById(R.id.commu_empty_text);
        emptyText.setVisibility(View.INVISIBLE);


        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recipeList.setLayoutManager(manager);

        ArrayList<CommuListGet> list = new ArrayList<>();
        findMyCommu(list,recipeList,emptyText);
        return view;
    }
    public void findMyCommu(ArrayList<CommuListGet> list, RecylcerViewEmpty recipeList, TextView emptyText){
        Call<List<CommuListGet>> call = MyCommuListClient.getApiService().myCommuListCall(tokenValue);
        call.enqueue(new Callback<List<CommuListGet>>() {
            @Override
            public void onResponse(Call<List<CommuListGet>> call, Response<List<CommuListGet>> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                }else{
                    Log.d(TAG, "onResponse: 성공");
                    for (int i = 0; i < response.body().size(); i++){
                        list.add(response.body().get(i));
                        MyCommuAdapter adapter = new MyCommuAdapter(list);
                        recipeList.setAdapter(adapter);
                        recipeList.setEmptyView(emptyText);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommuListGet>> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러");
            }
        });
    }
}
