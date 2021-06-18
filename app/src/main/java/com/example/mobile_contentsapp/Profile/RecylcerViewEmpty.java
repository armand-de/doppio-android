package com.example.mobile_contentsapp.Profile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RecylcerViewEmpty extends RecyclerView {
    private View emptyView;

    public RecylcerViewEmpty(@NonNull Context context) {
        super(context);
    }

    public RecylcerViewEmpty(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private AdapterDataObserver emptyObserver = new AdapterDataObserver()
    {
        @Override
        public void onChanged()
        {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null)
            {
                if (adapter.getItemCount() == 0)
                {
                    emptyView.setVisibility(View.VISIBLE);
                    RecylcerViewEmpty.this.setVisibility(View.GONE);
                }
                else
                {
                    emptyView.setVisibility(View.GONE);
                    RecylcerViewEmpty.this.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null){
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

}
