package com.androidnerdcolony.idlefactorybusiness.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidnerdcolony.idlefactorybusiness.data.FactoryContract.FactoryEntry;


/**
 * Created by tiger on 1/29/2017.
 */

public class FactoryDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "factory_tycoon.db";

    private static final int DATABASE_VERSION = 1;

    public FactoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_USER_TABLE =
                "CREATE TABLE " + FactoryEntry.USER_TABLE_NAME + " (" +
                        FactoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        FactoryEntry.COLUMN_BALANCE + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_LEVEL + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_EXP + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_PRESTIGE + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_TOKEN + " TEXT NOT NULL" +
                        ");";
        final String SQL_CREATE_FACTORY_TABLE =
                "CREATE TABLE " + FactoryEntry.FACTORY_TABLE_NAME + " (" +
                        FactoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        FactoryEntry.COLUMN_FACTORY_ID + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_FACTORY_LINE_ID + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_IDLE_CASH + " REAL NOT NULL, " +
                        FactoryEntry.COLUMN_LEVEL + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_LINE_COST + " REAL NOT NULL, " +
                        FactoryEntry.COLUMN_OPEN_COST + " REAL NOT NULL, " +
                        FactoryEntry.COLUMN_WORK_CAPACITY + " REAL NOT NULL, " +
                        FactoryEntry.COLUMN_WORKING + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_OPEN + " INTEGER NOT NULL, " +
                        FactoryEntry.COLUMN_QUALITY + " REAL NOT NULL, " +
                        FactoryEntry.COLUMN_TIME + " REAL NOT NULL, " +
                        FactoryEntry.COLUMN_VALUE + " REAL NOT NULL, " +

                        "UNIQUE (" + FactoryEntry.COLUMN_FACTORY_LINE_ID + ") ON CONFLICT REPLACE" +
                        ");";

        sqLiteDatabase.execSQL(SQL_CREATE_FACTORY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FactoryEntry.USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FactoryEntry.FACTORY_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
