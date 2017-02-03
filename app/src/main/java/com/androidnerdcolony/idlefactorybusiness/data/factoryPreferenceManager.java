package com.androidnerdcolony.idlefactorybusiness.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by tiger on 1/31/2017.
 */

public class FactoryPreferenceManager {


    public static void setBalance(Context context, double balance) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String balanceString = String.valueOf(balance);
        editor.putString(FactoryContract.FactoryEntry.COLUMN_BALANCE, balanceString );
        editor.apply();
    }

    public static double getBalance(Context context) {
       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String balanceString = preferences.getString(FactoryContract.FactoryEntry.COLUMN_BALANCE, "0");
        return Double.valueOf(balanceString);
    }
}
