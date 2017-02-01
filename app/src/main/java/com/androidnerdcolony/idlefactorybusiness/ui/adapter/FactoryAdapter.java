package com.androidnerdcolony.idlefactorybusiness.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.icu.text.NumberFormat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnerdcolony.idlefactorybusiness.R;
import com.androidnerdcolony.idlefactorybusiness.modules.ConvertNumber;
import com.androidnerdcolony.idlefactorybusiness.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        cursor.moveToPosition(position);

        double openCost = cursor.getDouble(MainActivity.INDEX_OPEN_COST);
        double lineCost = cursor.getDouble(MainActivity.INDEX_LINE_COST);
        String openCostString = ConvertNumber.numberToString(openCost);
        String lineCostString = ConvertNumber.numberToString(lineCost);
        boolean isOpen = cursor.getInt(MainActivity.INDEX_OPEN) != 0;
        boolean isWork = cursor.getInt(MainActivity.INDEX_WORKING) != 0;

        String openString = context.getString(R.string.open) + "\n" + openCostString;
        String lineString = context.getString(R.string.upgrade) + "\n" + lineCostString;
        if (isOpen){
            holder.factoryLineOpenButton.setVisibility(View.GONE);
            holder.factoryLineUpgradeButton.setVisibility(View.VISIBLE);
            holder.factoryLineOpenButton.setText(openString);

        }
        else {
            holder.factoryLineOpenButton.setVisibility(View.VISIBLE);
            holder.factoryLineUpgradeButton.setVisibility(View.GONE);
            holder.factoryLineUpgradeButton.setText(lineString);
        }


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

        @BindView(R.id.worker)
        ImageView workerView;
        @BindView(R.id.manager)
        ImageView managerView;
        @BindView(R.id.factory_line_box)
        ImageView factoryLineBoxView;
        @BindView(R.id.factory_line_open_button)
        Button factoryLineOpenButton;
        @BindView(R.id.factory_line_upgrade_button)
        Button factoryLineUpgradeButton;
        @BindView(R.id.working_progress)
        ProgressBar workingProgressBar;
        @BindView(R.id.work_profits)
        TextView workProfitView;
        @BindView(R.id.line_level)
        TextView lineLevelView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
