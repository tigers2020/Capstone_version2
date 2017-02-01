package com.androidnerdcolony.idlefactorybusiness.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnerdcolony.idlefactorybusiness.R;

import timber.log.Timber;

/**
 * Created by tiger on 1/31/2017.
 */

public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ViewHolder> {

    private Context context;
    private Cursor cursor;

    public FactoryAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.factory_line, parent, false);
        view.setFocusable(true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Timber.d("loading BindViewHolder");

    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }
    public void swapCursor(Cursor newCursor){
        cursor = newCursor;
        Timber.d("data swapped.");
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
