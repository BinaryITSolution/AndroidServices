package com.example.demoserviceproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.demoserviceproject.services.MyService2;

public class MyReceiver2 extends BroadcastReceiver {
    private static final String TAG = "MyReceiver2";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            context.startService(new Intent(context, MyService2.class));
        }
        catch (Exception er){
            Log.e(TAG, "onReceive:");
        }


    }
}
