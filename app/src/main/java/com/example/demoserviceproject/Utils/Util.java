package com.example.demoserviceproject.Utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.demoserviceproject.services.DemoJobService;

import java.util.concurrent.TimeUnit;

public class Util {

    private static final String TAG = "Util";
    private static int JOB_ID = 123;
    private static ComponentName componentName;

    public static void demoScheduleJob(Context context) {
        componentName = new ComponentName(context, DemoJobService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            try {

                JobInfo info = new JobInfo.Builder(JOB_ID, componentName)
                        .setPersisted(true)
                        .setPeriodic(900000)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .build();

                JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                int result = 0;
                if (jobScheduler != null) {
                    result = jobScheduler.schedule(info);
                }

                if (result == JobScheduler.RESULT_SUCCESS) {
                    Log.e(TAG, "Success");

                } else {
                    Log.e(TAG, "Failed");
                }

            } catch (Exception er) {
                Log.e(TAG, er.getMessage());
            }
        }
    }

    public  static void demoScheduleWork(Context context){
        Log.e(TAG, "demoScheduleWork");

        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                MyWork.class,15,TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueue(periodicWorkRequest);

    }

}
