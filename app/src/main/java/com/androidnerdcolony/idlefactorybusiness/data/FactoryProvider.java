package com.androidnerdcolony.idlefactorybusiness.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import timber.log.Timber;

/**
 * Created by tiger on 1/29/2017.
 */

public class FactoryProvider extends ContentProvider {

    public static final int CODE_FACTORY = 100;
    public static final int CODE_FACTORY_ID = 101;

    public static final int CODE_USER = 200;
    public static final int CODE_USER_ID = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FactoryDbHelper mHelper;


    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FactoryContract.CONTENT_AUTHORITY;


        matcher.addURI(authority, FactoryContract.PATH_USER_STATE, CODE_USER);
        matcher.addURI(authority, FactoryContract.PATH_FACTORY, CODE_FACTORY);

        matcher.addURI(authority, FactoryContract.PATH_USER_STATE + "/#", CODE_USER_ID);
        matcher.addURI(authority, FactoryContract.PATH_FACTORY + "/#", CODE_FACTORY_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {

        mHelper = new FactoryDbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            case CODE_FACTORY:
                cursor = mHelper.getReadableDatabase().query(
                        FactoryContract.FactoryEntry.FACTORY_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_FACTORY_ID:
                selection = FactoryContract.FactoryEntry.COLUMN_FACTORY_ID + "= ?";
                cursor = mHelper.getReadableDatabase().query(
                        FactoryContract.FactoryEntry.FACTORY_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_USER:
                cursor = mHelper.getReadableDatabase().query(
                        FactoryContract.FactoryEntry.USER_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)){
            case CODE_FACTORY:
                db.insert(FactoryContract.FactoryEntry.FACTORY_TABLE_NAME, null, contentValues);
                returnUri = FactoryContract.FactoryEntry.CONTENT_FACTORY_URI;
                break;
            case CODE_USER:
                db.insert(FactoryContract.FactoryEntry.USER_TABLE_NAME, null, contentValues);
                returnUri = FactoryContract.FactoryEntry.CONTENT_USER_URI;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        return returnUri;
    }

    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] contentValues){
        final SQLiteDatabase db = mHelper.getWritableDatabase();

        int rowEffect = 0;
        switch (sUriMatcher.match(uri)){
            case CODE_FACTORY:
                db.beginTransaction();
                try
                {
                    for (ContentValues value : contentValues){
                      long _id = db.insert(FactoryContract.FactoryEntry.FACTORY_TABLE_NAME, null, value);
                        if (_id != -1){
                            Timber.d("factoryLine " + rowEffect + " added");
                            rowEffect++;
                        }else {
                            throw new UnsupportedOperationException("failed factoryLine add");
                        }
                    }
                }finally {
                    db.endTransaction();
                }
        }
        if (rowEffect > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowEffect;

    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int rowEffected;
        switch (sUriMatcher.match(uri)){
            case CODE_FACTORY_ID:
                selection = FactoryContract.FactoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowEffected = mHelper.getWritableDatabase().update(
                        FactoryContract.FactoryEntry.FACTORY_TABLE_NAME,
                        contentValues, selection, selectionArgs);
                break;
            case CODE_USER_ID :
                selection = FactoryContract.FactoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowEffected = mHelper.getWritableDatabase().update(
                        FactoryContract.FactoryEntry.USER_TABLE_NAME,
                        contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        return rowEffected;
    }
}
