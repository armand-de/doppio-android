package com.example.mobile_contentsapp.Commu;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;
import com.example.mobile_contentsapp.Commu.Retrofit.CommuSearchClient;
import com.example.mobile_contentsapp.Profile.RecylcerViewEmpty;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class CommuFragment extends Fragment {

    private String keyword;
    private int start = -1;
    private List<CommuListGet> listGet;
    private ArrayList<CommuListGet> list;
    private CommuListAdapter adapter;
    private RecylcerViewEmpty commuRecycler;
    private TextView emptyText;
    private boolean remainList = false;
    private boolean isLoding = false;

    @Override
    public void onStart() {
        super.onStart();
        if(!isLoding){
            list.clear();
            adapter.notifyDataSetChanged();
            isLoding = true;
            search(-1,null);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commu,container,false);

        SwipeRefreshLayout swipe = view.findViewById(R.id.commu_swipe);
        EditText searchEdit = view.findViewById(R.id.commu_search_edit);
        ImageButton searchBtn = view.findViewById(R.id.commu_search_btn);
        commuRecycler = view.findViewById(R.id.commu_list_recycler);
        emptyText = view.findViewById(R.id.commu_empty_text);
        emptyText.setVisibility(View.INVISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        commuRecycler.setLayoutManager(manager);

        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    keyword = searchEdit.getText().toString();
                    search(-1,keyword);
                    return true;
                }
                return false;
            }
        });

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
                            && remainList){
                                isLoding = true;
                                search(start, keyword);
                    }
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                adapter.notifyDataSetChanged();
                keyword = searchEdit.getText().toString();
                search(-1,keyword);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoding){
                    list.clear();
                    adapter.notifyDataSetChanged();
                    search(-1,null);
                    swipe.setRefreshing(false);
                }
                swipe.setRefreshing(false);
            }
        });
        return view;
    }

    public void search(int startValue, String keyword){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Call<List<CommuListGet>> call = CommuSearchClient.getApiService().commuSearchCall(startValue,keyword);
        call.enqueue(new Callback<List<CommuListGet>>() {
            @Override
            public void onResponse(Call<List<CommuListGet>> call, Response<List<CommuListGet>> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
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
                    remainList = false;
                }

                commuRecycler.setEmptyView(emptyText);

            }

            @Override
            public void onFailure(Call<List<CommuListGet>> call, Throwable t) {
                return;
            }
        });
        isLoding = false;
    }
}
