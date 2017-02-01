package com.androidnerdcolony.idlefactorybusiness;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by tiger on 1/29/2017.
 */

public class IdleFactoryBusinessApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
