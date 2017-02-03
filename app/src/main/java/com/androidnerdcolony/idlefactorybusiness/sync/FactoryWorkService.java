package com.androidnerdcolony.idlefactorybusiness.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import timber.log.Timber;

/**
 * Created by tiger on 2/2/2017.
 */
public class FactoryWorkService extends JobService {

    private AsyncTask<Void, Void, Void> mFetchWorkTask;


    @Override
    public boolean onStartJob(final JobParameters job) {
        Timber.d("start Job");
        mFetchWorkTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();

                FactoryWorkTask.working(context);

                jobFinished(job, false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        };

        mFetchWorkTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mFetchWorkTask != null) {
            mFetchWorkTask.cancel(true);
        }
        return true;
    }
}
