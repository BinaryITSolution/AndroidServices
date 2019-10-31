package com.example.demoserviceproject.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyBoundService extends Service {
    private static final String TAG = "MyBoundService";
    boolean isOn;
    Thread thread;
    private AsyncTask asyncTask;
    private String mCurrentTime;
    public static boolean isServiceRunning;

    public MyBoundService() {
        Log.e(TAG, "MyBoundService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        isOn = true;
        isServiceRunning = true;
        startTime();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        isOn = false;
        isServiceRunning = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
       return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }

    public class MyBoundServiceBinder extends Binder{
        // return the instance od the current service
        public MyBoundService getService(){
            Log.e(TAG, "getService");
            return  MyBoundService.this;
        }
    }

    private IBinder binder = new MyBoundServiceBinder();

    private void startTime(){
        //generateRandomNumber();
        Log.e(TAG, "getService");
        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                while (isOn){
                    try{
                        if (isOn){
                            Thread.sleep(1000);
                            Date date = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:s a", Locale.getDefault());
                            mCurrentTime = simpleDateFormat.format(date);
                            Log.e(TAG,"Current Time: " +  mCurrentTime);
                            Log.e(TAG,"AsyncTask Thread: " + Thread.currentThread().getId());
                        }
                        else {
                            break;
                        }
                    }
                    catch (Exception er){
                        Log.e(TAG, er.getMessage());
                    }
                }
                return null;
            }
        };

        asyncTask.execute();
    }

    public String getTime(){
        return  mCurrentTime;
    }


}
