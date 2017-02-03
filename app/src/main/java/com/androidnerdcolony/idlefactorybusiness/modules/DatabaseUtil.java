package com.androidnerdcolony.idlefactorybusiness.modules;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.androidnerdcolony.idlefactorybusiness.data.FactoryContract.FactoryEntry;
import com.androidnerdcolony.idlefactorybusiness.data.FactoryPreferenceManager;

import java.util.Date;

import static android.R.attr.id;

/**
 * Created by tiger on 1/30/2017.
 */

public class DatabaseUtil {
    public static void setDefaultDatabase(Context context) {
        ContentValues defaultUserState = new ContentValues();
        ContentValues[] defaultFactoryStates = new ContentValues[10];


        long millisecond;
        Date date = new Date();
        millisecond = date.getTime();

        defaultUserState.put(FactoryEntry.COLUMN_DATE, millisecond);
        defaultUserState.put(FactoryEntry.COLUMN_BALANCE, 500);
        defaultUserState.put(FactoryEntry.COLUMN_EXP, 0);
        defaultUserState.put(FactoryEntry.COLUMN_LEVEL, 1);
        defaultUserState.put(FactoryEntry.COLUMN_PRESTIGE, 0);
        defaultUserState.put(FactoryEntry.COLUMN_TOKEN, "");

        double defaultLineCost = 1000;
        double defaultOpenCost = 500;
        double defaultWorkingCapacity = 100;


        for (int i = 0; i < 10; i++) {
            ContentValues defaultFactoryState = new ContentValues();
            defaultFactoryState.put(FactoryEntry.COLUMN_FACTORY_ID, 1);
            defaultFactoryState.put(FactoryEntry.COLUMN_FACTORY_LINE_ID, i);
            defaultFactoryState.put(FactoryEntry.COLUMN_IDLE_CASH, 1);
            defaultFactoryState.put(FactoryEntry.COLUMN_LEVEL, 1);
            defaultFactoryState.put(FactoryEntry.COLUMN_LINE_COST, defaultLineCost);
            defaultFactoryState.put(FactoryEntry.COLUMN_OPEN_COST, defaultOpenCost);
            defaultFactoryState.put(FactoryEntry.COLUMN_WORK_CAPACITY, defaultWorkingCapacity);
            defaultFactoryState.put(FactoryEntry.COLUMN_WORKING, 0);
            defaultFactoryState.put(FactoryEntry.COLUMN_OPEN, 0);
            defaultFactoryState.put(FactoryEntry.COLUMN_QUALITY, 100);
            defaultFactoryState.put(FactoryEntry.COLUMN_TIME, 100);
            defaultFactoryState.put(FactoryEntry.COLUMN_VALUE, 100);
            defaultLineCost = defaultLineCost * 100;
            defaultOpenCost = defaultOpenCost * 100;
            defaultWorkingCapacity = defaultWorkingCapacity * 50;

            defaultFactoryStates[i] = defaultFactoryState;
        }
        context.getContentResolver().insert(FactoryEntry.CONTENT_USER_URI, defaultUserState);
        context.getContentResolver().bulkInsert(FactoryEntry.CONTENT_FACTORY_URI, defaultFactoryStates);

    }

    public static void OpenLine(Context context, double openCost, long id) {
        ContentValues factoryValue = new ContentValues();
        ContentValues userValue = new ContentValues();
        double balance = FactoryPreferenceManager.getBalance(context);
        balance = balance - openCost;
        UpdateBalance(context, balance);
        factoryValue.put(FactoryEntry.COLUMN_OPEN, 1);
        Uri factoryUri = FactoryEntry.CONTENT_FACTORY_URI.buildUpon().appendPath(String.valueOf(id)).build();
        context.getContentResolver().update(factoryUri, factoryValue, null, null);
    }

    public static void UpgradeLine(Context context, double lineCost, int level, long id) {
        ContentValues values = new ContentValues();
        double balance = FactoryPreferenceManager.getBalance(context);
        balance = balance - lineCost;
        level++;

        UpdateBalance(context, balance);
        values.put(FactoryEntry.COLUMN_LEVEL, level);
        Uri uri = FactoryEntry.CONTENT_FACTORY_URI.buildUpon().appendPath(String.valueOf(id)).build();
        context.getContentResolver().update(uri, values, null, null);

    }
    public static void UpdateBalance(Context context, double balance){
        ContentValues values = new ContentValues();
        Uri uri = FactoryEntry.CONTENT_USER_URI;

        values.put(FactoryEntry.COLUMN_BALANCE, balance);
        context.getContentResolver().update(uri, values, null, null);
        FactoryPreferenceManager.setBalance(context, balance);
    }
}
