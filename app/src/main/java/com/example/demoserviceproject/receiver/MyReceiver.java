package com.example.demoserviceproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.demoserviceproject.Utils.Util;
import com.example.demoserviceproject.services.MyService;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"onReceive");

        //Util.demoScheduleJob(context);
        Util.demoScheduleWork(context);

    }
}
