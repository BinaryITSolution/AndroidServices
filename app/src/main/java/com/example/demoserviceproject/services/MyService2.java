package com.example.demoserviceproject.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.demoserviceproject.receiver.MyReceiver2;

import java.util.Random;

public class MyService2 extends Service {
    private static final String TAG = MyService.class.getSimpleName();
    boolean isOn;
    int mRandomNumber;
    private static int MAX = 1000;
    Thread thread;

    public MyService2() {
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");

        isOn = true;
        generateRandomNumber();

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stopRandomNumberGeneration();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e(TAG, "onTaskRemoved");
        Intent intent = new Intent(this, MyReceiver2.class);
        sendBroadcast(intent);
    }


    private void generateRandomNumber() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isOn) {
                    if (isOn) {
                        Log.e(TAG, "Random Number Current Thread: " + Thread.currentThread().getId());
                        mRandomNumber = new Random().nextInt(MAX);
                        Log.e(TAG, "Random Number: " + mRandomNumber);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
            }
        });
        thread.start();

    }

    private void stopRandomNumberGeneration() {
        isOn = false;
    }
}
