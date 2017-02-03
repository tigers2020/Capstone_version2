package com.androidnerdcolony.idlefactorybusiness.ui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnerdcolony.idlefactorybusiness.R;
import com.androidnerdcolony.idlefactorybusiness.data.FactoryPreferenceManager;
import com.androidnerdcolony.idlefactorybusiness.datalayout.FactoryLine;
import com.androidnerdcolony.idlefactorybusiness.modules.ConvertNumber;
import com.androidnerdcolony.idlefactorybusiness.modules.DatabaseUtil;
import com.androidnerdcolony.idlefactorybusiness.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.R.attr.cursorVisible;
import static android.R.attr.id;
import static android.R.attr.switchMinWidth;
import static android.R.string.no;

/**
 * Created by tiger on 1/31/2017.
 */

public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ViewHolder> {

    private Context context;
    double balance;
    private Cursor cursor;


    public FactoryAdapter(Context context) {
        this.context = context;
        balance = FactoryPreferenceManager.getBalance(context);
        Timber.d("balance = " + balance);
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
        int level = cursor.getInt(MainActivity.INDEX_LEVEL);
        holder.lineLevelView.setText(String.valueOf(level));

        if (openCost > balance){
            holder.factoryLineOpenButton.setEnabled(false);
        }else{
            holder.factoryLineOpenButton.setEnabled(true);
        }
        if (lineCost > balance){
            holder.factoryLineUpgradeButton.setEnabled(false);
        }else {
            holder.factoryLineUpgradeButton.setEnabled(true);
        }

        if (isOpen) {
            holder.factoryLineOpenButton.setVisibility(View.GONE);
            holder.factoryLineUpgradeButton.setVisibility(View.VISIBLE);
            holder.factoryLineUpgradeButton.setText(lineString);
        } else {
            holder.factoryLineOpenButton.setVisibility(View.VISIBLE);
            holder.factoryLineUpgradeButton.setVisibility(View.GONE);
            holder.factoryLineOpenButton.setText(openString);
        }
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        Timber.d("data swapped.");
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
            factoryLineOpenButton.setOnClickListener(this);
            factoryLineUpgradeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            int position = getAdapterPosition();
            cursor.moveToPosition(position);

            long _id = cursor.getLong(MainActivity.INDEX_ID);
            double openCost = cursor.getDouble(MainActivity.INDEX_OPEN_COST);

            int factoryLevel = cursor.getInt(MainActivity.INDEX_LEVEL);
            double lineCost = cursor.getDouble(MainActivity.INDEX_LINE_COST);

            switch (id){
                case R.id.factory_line_open_button:
                    Timber.d("Open Button Clicked" + id);
                    DatabaseUtil.OpenLine(context, openCost, _id);
                    context.notifyAll();
                    break;
                case R.id.factory_line_upgrade_button:
                    DatabaseUtil.UpgradeLine(context, lineCost, factoryLevel, _id);
                    context.notifyAll();
                    break;
            }
        }
    }

}
