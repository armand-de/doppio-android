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

import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;
import com.example.mobile_contentsapp.Main.SplashActivity;
import com.example.mobile_contentsapp.Profile.Retrofit.CommuDeleteClient;
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

    private String userId;
    private MyCommuAdapter adapter;
    private int id;
    private ArrayList<CommuListGet> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_commu,container,false);

        Bundle bundle = getArguments();
        if (bundle != null){
            userId = bundle.getString("userid");
        }

        RecylcerViewEmpty recipeList = view.findViewById(R.id.profile_my_commu_recycler);
        TextView emptyText = view.findViewById(R.id.commu_empty_text);
        emptyText.setVisibility(View.INVISIBLE);


        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recipeList.setLayoutManager(manager);


        list = new ArrayList<>();

        adapter = new MyCommuAdapter(list);
        recipeList.setAdapter(adapter);
        recipeList.setEmptyView(emptyText);

        findMyCommu(list);
        adapter.setOnClickListener(new MyCommuAdapter.OnLongClickListener() {
            @Override
            public void OnClick(View view, int pos, int id) {
                longClick(view,pos,id);
            }
        });
        return view;
    }
    public void longClick(View view,int pos, int id){
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
                            deleteCommu(id);
                        }
                    });
            Dialog dialog = builder.create();
            dialog.show();
        }
    }
    public void findMyCommu(ArrayList<CommuListGet> list){
        Call<List<CommuListGet>> call = MyCommuListClient.getApiService().myCommuListCall(userId);
        call.enqueue(new Callback<List<CommuListGet>>() {
            @Override
            public void onResponse(Call<List<CommuListGet>> call, Response<List<CommuListGet>> response) {
                if (!response.isSuccessful()){
                }else{
                    for (int i = 0; i < response.body().size(); i++){
                        list.add(response.body().get(i));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CommuListGet>> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러");
            }
        });
    }
    public void deleteCommu(int id){
        Call<Void> call = CommuDeleteClient.getApiService().deletecommu(tokenValue,id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
