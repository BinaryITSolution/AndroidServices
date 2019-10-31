package com.example.demoserviceproject.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.work.Constraints;

import com.example.demoserviceproject.MainActivity;
import com.example.demoserviceproject.R;
import com.example.demoserviceproject.receiver.MyReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();
    boolean isOn;
    int mRandomNumber;
    private static int MAX = 1000;
    Thread thread;
    private AsyncTask asyncTask;
    private String mCurrentTime;
    private static final String CHANNEL_ID = "1231231230";



    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        Log.e(TAG, "Service Current Thread: " + Thread.currentThread().getId());
        isOn = true;
        String action = intent.getAction();
        if (action.equals("Start")){
            // notification
            notification();
            //
            generateRandomNumber();

        /*asyncTask = new AsyncTask() {
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
        */
            //generateRandomNumber();
        }
        else if (action.equals("Stop")){
            stopForegroundService();
        }
        else {
            Log.e(TAG,"Action is empty");
        }



        return START_STICKY;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGeneration();
        Log.e(TAG, "onDestroy");

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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

    private void notification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Demo Servie",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("My Service")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();


        startForeground(100,notification);
    }

    private void stopForegroundService(){
        Log.e(TAG, "stopForegroundService");
        stopForeground(true);
        //stopRandomNumberGeneration();
        stopSelf();
    }



}
