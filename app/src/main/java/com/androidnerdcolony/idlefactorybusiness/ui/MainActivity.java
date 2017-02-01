package com.androidnerdcolony.idlefactorybusiness.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.androidnerdcolony.idlefactorybusiness.R;
import com.androidnerdcolony.idlefactorybusiness.data.FactoryContract.FactoryEntry;
import com.androidnerdcolony.idlefactorybusiness.modules.ConvertNumber;
import com.androidnerdcolony.idlefactorybusiness.modules.DefaultDatabase;
import com.androidnerdcolony.idlefactorybusiness.ui.adapter.FactoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.R.attr.data;
import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String[] DEFAULT_USER_PROJECTION = new String[]{
            FactoryEntry.COLUMN_DATE,
            FactoryEntry.COLUMN_BALANCE,
            FactoryEntry.COLUMN_EXP,
            FactoryEntry.COLUMN_PRESTIGE,
            FactoryEntry.COLUMN_TOKEN
    };
    public static final int INDEX_DATE = 0;
    public static final int INDEX_BALANCE = 1;
    public static final int INDEX_EXP = 2;
    public static final int INDEX_PRESTIGE = 3;
    public static final int INDEX_TOKEN = 4;

    public static final String[] DEFAULT_FACTORY_PROJECTION = new String[]{
            FactoryEntry.COLUMN_FACTORY_ID,
            FactoryEntry.COLUMN_FACTORY_LINE_ID,
            FactoryEntry.COLUMN_IDLE_CASH,
            FactoryEntry.COLUMN_LEVEL,
            FactoryEntry.COLUMN_LINE_COST,
            FactoryEntry.COLUMN_OPEN_COST,
            FactoryEntry.COLUMN_WORK_CAPACITY,
            FactoryEntry.COLUMN_WORKING,
            FactoryEntry.COLUMN_OPEN,
            FactoryEntry.COLUMN_QUALITY,
            FactoryEntry.COLUMN_TIME,
            FactoryEntry.COLUMN_VALUE
    };

    public static final int INDEX_FACTORY_ID = 0;
    public static final int INDEX_FACTORY_LINE_ID = 1;
    public static final int INDEX_IDLE_CASH = 2;
    public static final int INDEX_LEVEL = 3;
    public static final int INDEX_LINE_COST = 4;
    public static final int INDEX_OPEN_COST = 5;
    public static final int INDEX_WORK_CAPACITY = 6;
    public static final int INDEX_WORKING = 7;
    public static final int INDEX_OPEN = 8;
    public static final int INDEX_QUALITY = 9;
    public static final int INDEX_TIME = 10;
    public static final int INDEX_VALUE = 11;

    private static final int USER_LOADER = 1001;
    private static final int FACTORY_LOADER = 1002;


    LoaderManager mLoaderManager;
    FactoryAdapter mAdapter;

    private Context context;

    @BindView(R.id.balance)
    TextView balanceView;
    @BindView(R.id.idle_cash)
    TextView idleCashView;
    @BindView(R.id.factory_line)
    RecyclerView factoryLineRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);

        mAdapter = new FactoryAdapter(context);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

        factoryLineRecyclerView.setLayoutManager(layoutManager);

        factoryLineRecyclerView.setAdapter(mAdapter);
        firstDataCheck();

        mLoaderManager = getSupportLoaderManager();
        Loader<Cursor> userLoader = mLoaderManager.getLoader(USER_LOADER);

        if (userLoader == null){
            mLoaderManager.initLoader(USER_LOADER, null, this);
        }else{
            mLoaderManager.restartLoader(USER_LOADER, savedInstanceState, this);
        }
        Loader<Cursor> factoryLoader = mLoaderManager.getLoader(FACTORY_LOADER);
        if (factoryLoader == null){
            mLoaderManager.initLoader(FACTORY_LOADER, null, this);
        }else{
            mLoaderManager.restartLoader(FACTORY_LOADER, savedInstanceState, this);
        }


    }

    private void firstDataCheck() {
        String selection = "";
        String[] selectionArgs = new String[]{};
        String sortOrder = "";
        Timber.d("checking firstData");
        Cursor cursor = getContentResolver().query(FactoryEntry.CONTENT_USER_URI, null, selection, selectionArgs, sortOrder);

        if (cursor.getCount() <= 0 && !cursor.moveToFirst()){
            Timber.d("data is empty");
            DefaultDatabase.setDefaultDatabase(context);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = "";
        String[] selectionArgs = new String[]{};
        String sortOrder = "";
        Uri query;

        switch (id){
            case USER_LOADER:
                query = FactoryEntry.CONTENT_USER_URI;
                return new CursorLoader(context, query, DEFAULT_USER_PROJECTION,
                        selection,
                        selectionArgs,
                        sortOrder);
            case FACTORY_LOADER:
                query = FactoryEntry.CONTENT_FACTORY_URI;
                return  new CursorLoader(context, query, DEFAULT_FACTORY_PROJECTION,
                        selection,
                        selectionArgs,
                        sortOrder);
            default:
                throw new UnsupportedOperationException("unknown Loader" + id);
        }



    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        Timber.d("loader ID = " + id);
        switch (id){
            case USER_LOADER:
                if (data.moveToFirst()) {
                    double balance = data.getDouble(INDEX_BALANCE);
                    double idleCash = data.getDouble(INDEX_IDLE_CASH);

                    String balanceString = ConvertNumber.numberToString(balance);
                    String idleCashString = ConvertNumber.numberToString(idleCash);

                    balanceView.setText(balanceString);
                    idleCashView.setText(idleCashString);
                }
                break;
            case FACTORY_LOADER:
                Timber.d("set Cursor in Adapter");
                mAdapter.swapCursor(data);
                mAdapter.notifyDataSetChanged();
                break;
            default:
                throw new UnsupportedOperationException("unknown loader ID");

        }




        data.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_insertData:
                DefaultDatabase.setDefaultDatabase(context);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insert_data, menu);

        return true;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        loader.reset();

    }
}
