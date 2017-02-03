package com.androidnerdcolony.idlefactorybusiness.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.TimeUtils;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by tiger on 2/2/2017.
 */

public class FactoryWork {

    private static final int SYNC_INTERVAL_LINE = (int) TimeUnit.SECONDS.toSeconds(2);
    private static final int SYNC_INTERVAL_FLEXTIME = SYNC_INTERVAL_LINE / 3;
    private static void scheduleFirebaseJobDispatcherSync(Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncFactoryJob = dispatcher.newJobBuilder()
                .setService(FactoryWorkService.class)
                .setTag("factory_sync")
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_LINE,
                        SYNC_INTERVAL_LINE + SYNC_INTERVAL_FLEXTIME
                ))
                .setReplaceCurrent(true)
                .build();
        Timber.d("start schedule");
        dispatcher.schedule(syncFactoryJob);
    }

    synchronized public static void initialize(@NonNull final Context context) {
        scheduleFirebaseJobDispatcherSync(context);
    }
}
