package com.example.mobile_contentsapp.Commu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuListClient;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuSearchClient;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommuFragment extends Fragment {

    private String keyword;
    private int start = -1;
    private List<CommuListGet> listGet;
    private ArrayList<CommuListGet> list;
    private CommuListAdapter adapter;
    private boolean leftList = false;
    private boolean isSearch = false;
    private boolean isLoding = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commu_fragment,container,false);


        SwipeRefreshLayout swipe = view.findViewById(R.id.commu_swipe);
        EditText searchEdit = view.findViewById(R.id.commu_search_edit);
        ImageButton searchBtn = view.findViewById(R.id.commu_search_btn);
        RecyclerView commuRecycler = view.findViewById(R.id.commu_list_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        commuRecycler.setLayoutManager(manager);

        list = new ArrayList<>();
        adapter = new CommuListAdapter(list);

        commuRecycler.setAdapter(adapter);

        commuRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoding){
                    if (manager != null &&
                            manager.findLastCompletelyVisibleItemPosition() == list.size()-1
                            &&leftList){
                        if(!isSearch){
                            loadMore();
                        }else{
                            searchMore();
                        }isLoding = true;

                    }
                }

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = searchEdit.getText().toString();
                if (keyword.isEmpty()){
                    isSearch = false;
                }else{
                    isSearch = true;
                    search();
                }
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoding){
                    setList();
                    swipe.setRefreshing(false);
                }
                swipe.setRefreshing(false);
            }
        });
        return view;
    }
    public void setList(){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Call<List<CommuListGet>> call = CommuListClient.getApiService().commuListApiCall(-1);
        call.enqueue(new Callback<List<CommuListGet>>() {
            @Override
            public void onResponse(Call<List<CommuListGet>> call, Response<List<CommuListGet>> response) {
                if (!response.isSuccessful()){
                 return;
                }
                listGet = response.body();
                list.clear();
                adapter.notifyDataSetChanged();
                if (listGet.size() != 0){
                    start = listGet.get(listGet.size()-1).getId();
                    for (int i = 0;  i < listGet.size(); i++){
                        list.add(listGet.get(i));
                    }
                    leftList = true;
                }else{
                    leftList = false;
                }
                adapter.notifyDataSetChanged();
                isLoding = false;
            }
            @Override
            public void onFailure(Call<List<CommuListGet>> call, Throwable t) {

            }
        });
    }
    public void loadMore(){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Call<List<CommuListGet>> call = CommuListClient.getApiService().commuListApiCall(start);
        call.enqueue(new Callback<List<CommuListGet>>() {
            @Override
            public void onResponse(Call<List<CommuListGet>> call, Response<List<CommuListGet>> response) {
                if (!response.isSuccessful()){
                    return;
                }
                listGet = response.body();
                list.remove(list.size()-1);
                adapter.notifyItemRemoved(list.size());

                if (listGet.size() != 0){
                    start = listGet.get(listGet.size()-1).getId();
                    for (int i = 0;  i < listGet.size(); i++){
                        list.add(listGet.get(i));
                    }
                    adapter.notifyDataSetChanged();
                } else{
                    leftList = false;
                }
                isLoding = false;
            }

            @Override
            public void onFailure(Call<List<CommuListGet>> call, Throwable t) {
                return;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setList();
    }

    public void search(){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Call<List<CommuListGet>> call = CommuSearchClient.getApiService().commuSearchCall(keyword,-1);
        call.enqueue(new Callback<List<CommuListGet>>() {
            @Override
            public void onResponse(Call<List<CommuListGet>> call, Response<List<CommuListGet>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                listGet = response.body();
                list.clear();
                adapter.notifyDataSetChanged();
                if (listGet.size() != 0){
                    start = listGet.get(listGet.size()-1).getId();
                    for (int i = 0;  i < listGet.size(); i++){
                        list.add(listGet.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    leftList = false;
                }
            }

            @Override
            public void onFailure(Call<List<CommuListGet>> call, Throwable t) {
                return;
            }
        });
    }
    public void searchMore(){
        isLoding = true;
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Call<List<CommuListGet>> call = CommuSearchClient.getApiService().commuSearchCall(keyword,start);
        call.enqueue(new Callback<List<CommuListGet>>() {
            @Override
            public void onResponse(Call<List<CommuListGet>> call, Response<List<CommuListGet>> response) {
                if (!response.isSuccessful()){
                    return;
                }
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
                    leftList = false;
                }

            }

            @Override
            public void onFailure(Call<List<CommuListGet>> call, Throwable t) {
                return;
            }
        });
        isLoding = false;
    }
}
