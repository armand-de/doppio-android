package com.example.mobile_contentsapp.Commu;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_contentsapp.Commu.Retrofit.Commu_List_Get;
import com.example.mobile_contentsapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

public class Commu_Fragment extends Fragment {

    private int start;
    private List<Commu_List_Get> listGet;
    private ArrayList<Commu_List_Get> list;
    private Commu_List_Adapter adapter;
    boolean isLoding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commu_fragment,container,false);
        RecyclerView commuRecycler = view.findViewById(R.id.commu_list_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        commuRecycler.setLayoutManager(manager);

        list = new ArrayList<>();
        adapter = new Commu_List_Adapter(list);

        try {
            list.add(null);
            adapter.notifyItemInserted(list.size()-1);

            listGet = new Commu_List_Task(-1).execute().get();
            list.remove(list.size()-1);
            adapter.notifyItemRemoved(list.size());
            if (listGet.size() != 0){
                start = listGet.get(listGet.size()-1).getId();
                Log.d(TAG, "onCreateView: "+ start);

                for (int i = 0;  i < listGet.size(); i++){
                    list.add(listGet.get(i));
                    Log.d(TAG, "run: "+listGet.get(i));
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



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
                    if (manager != null && manager.findLastCompletelyVisibleItemPosition() == list.size()-1){
                        try {
                            listGet = new Commu_List_Task(start).execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (listGet != null){
                            if (listGet.size() != 0){
                                loadMore();
                                start = listGet.get(listGet.size()-1).getId();
                                isLoding = false;
                            }
                        }
                    }
                }

            }
        });

        return view;
    }
    public void loadMore(){
        list.add(null);
        adapter.notifyItemInserted(list.size()-1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.remove(list.size()-1);
                int scrollPosition = list.size();
                adapter.notifyItemRemoved(scrollPosition);

                for (int i = 0; i < listGet.size(); i++){
                    list.add(listGet.get(i));
                    Log.d(TAG, "run: "+listGet.get(i));
                }
                adapter.notifyDataSetChanged();
                isLoding = false;
            }
        },2000);

    }
}
