package com.example.demoserviceproject.Utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWork extends Worker {
    private static final String TAG = "MyWork";

    public MyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "doWork:");

        for (int i = 100 ; i > 0 ; i--){

            Log.e(TAG, String.valueOf(i));
        }

        return Result.success();
    }
}
