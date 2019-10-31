package com.example.demoserviceproject.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";
    boolean isOn;
    private String mCurrentTime;
    private int mRandomNumber;
    private static int MAX = 1000;
    private static final String ACTION_TIME = "TIME";
    private static final String ACTION_RANDOM ="RANDOM";



    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
        isOn = false;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG,"onHandleIntent");
        isOn = true;
        if (intent != null){
            String action = intent.getAction();

            if (ACTION_TIME.equals(action)){
                startTime();
            }
            else if (ACTION_RANDOM.equals(action)){
                generateRandomNumber() ;
            }
            else {
                Log.e(TAG,"Intent action does not match");
            }
        }

    }

    private void startTime(){
        //generateRandomNumber();
        while (isOn){
            try{
                if (isOn){
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:s a", Locale.getDefault());
                    mCurrentTime = simpleDateFormat.format(date);
                    Log.e(TAG,"Current Time: " +  mCurrentTime);
                    Log.e(TAG,"onHandleIntent Thread: " + Thread.currentThread().getId());
                    Thread.sleep(1000);
                }
                else {
                    break;
                }
            }
            catch (Exception er){
                Log.e(TAG, er.getMessage());
            }
        }
    }

    private void generateRandomNumber(){
        while (isOn){
            try {
                if (isOn){
                    Log.e(TAG,"Random Number Current Thread: " + Thread.currentThread().getId());
                    mRandomNumber = new Random().nextInt(MAX);
                    Log.e(TAG,"Random Number: " + mRandomNumber);
                    Thread.sleep(1000);
                }
                else {
                    break;
                }
            }
            catch(Exception er){
                Log.e(TAG,er.getMessage());
            }

        }

    }


}
