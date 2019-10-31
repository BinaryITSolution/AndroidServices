package com.example.demoserviceproject.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class DemoJobService extends JobService {
    private static final String TAG = "DemoJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e(TAG,"onStartJob");

        for (int i = 0 ; i <= 100 ; i++){
            Log.e(TAG, String.valueOf(i));
        }

        jobFinished(jobParameters,false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e(TAG,"onStopJob");

        return false;
    }
}
