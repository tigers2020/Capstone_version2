package com.androidnerdcolony.idlefactorybusiness.sync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.androidnerdcolony.idlefactorybusiness.data.FactoryContract.FactoryEntry;
import com.androidnerdcolony.idlefactorybusiness.data.FactoryPreferenceManager;
import com.androidnerdcolony.idlefactorybusiness.ui.MainActivity;

import java.util.Date;

import timber.log.Timber;

/**
 * Created by tiger on 2/2/2017.
 */
public class FactoryWorkTask {
    synchronized public static void working(Context context) {

        Date date = new Date();
        Timber.d("start Working: " + date);

        Cursor cursor = context.getContentResolver().query(FactoryEntry.CONTENT_FACTORY_URI, MainActivity.DEFAULT_FACTORY_PROJECTION, null, null, null);
        double oldBalance = FactoryPreferenceManager.getBalance(context);
        double balance = oldBalance;
        double workProfit;
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                double workCapacity = cursor.getDouble(MainActivity.INDEX_WORK_CAPACITY);
                int level = cursor.getInt(MainActivity.INDEX_LEVEL);
                boolean isOpen = cursor.getInt(MainActivity.INDEX_OPEN) != 0;
                boolean isWorking = cursor.getInt(MainActivity.INDEX_WORKING) != 0;

                Timber.d("workCapacity = " + workCapacity + " level = " + level + " isOpen = " + isOpen);

                if (isOpen) {
                    workProfit = workCapacity + ((workCapacity * 0.09) * level);
                } else {
                    workProfit = 0;
                }
                Timber.d("workProfit = " + workProfit);
                balance = balance + workProfit;
            }

            if (balance != oldBalance) {
                ContentValues value = new ContentValues();
                value.put(FactoryEntry.COLUMN_BALANCE, balance);
                Uri uri = FactoryEntry.CONTENT_USER_URI;
                Timber.d("working profit done" + balance);
                context.getContentResolver().update(uri, value, null, null);
                context.getContentResolver().notifyChange(uri, null);
            }

        }

        if (cursor != null) {
            cursor.close();
        }

    }
}
