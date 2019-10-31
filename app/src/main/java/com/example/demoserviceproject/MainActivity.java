package com.example.demoserviceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demoserviceproject.Utils.Util;
import com.example.demoserviceproject.services.MyBoundService;
import com.example.demoserviceproject.services.MyIntentService;
import com.example.demoserviceproject.services.MyService;
import com.example.demoserviceproject.services.MyService2;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Intent myServiceIntent,myBoundServiceIntent,myIntentService,myIntentService2;

    Button btnStartMyService, btnStopMyService,btnStartMyBoundService,btnStopMyBoundService,btnBindMyBoundService,btnUnBindMyBoundService,
            btnStartMyIntentService,btnStopMyIntentService,btnStopForegroundMyService,btnStartMyService2,btnStopMyService2;
    private  boolean isServiceBound;
    private ServiceConnection serviceConnection;
    private MyBoundService myBoundService;
    TextView lbTime;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lbTime = findViewById(R.id.lbTime);

        myServiceIntent = new Intent(getApplicationContext(), MyService.class);
        myBoundServiceIntent = new Intent(getApplicationContext(), MyBoundService.class);
        myIntentService = new Intent(getApplicationContext(), MyIntentService.class);
        myIntentService2 = new Intent(getApplicationContext(), MyService2.class);

        btnStartMyService = findViewById(R.id.btnStartMyService);
        btnStartMyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start my service
                Log.e(TAG, "Main Current Thread: " + Thread.currentThread().getId());
                myServiceIntent = new Intent(getApplicationContext(), MyService.class);
                myServiceIntent.setAction("Start");
                startService(myServiceIntent);
            }
        });

        btnStopMyService = findViewById(R.id.btnStopMyService);
        btnStopMyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stopService(myServiceIntent);
            }
        });

        btnStartMyBoundService = findViewById(R.id.btnStartMyBoundService);
        btnStartMyBoundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyBoundService();
            }
        });
        btnStopMyBoundService = findViewById(R.id.btnStopMyBoundService);
        btnStopMyBoundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(myBoundServiceIntent);
            }
        });

        btnBindMyBoundService = findViewById(R.id.btnBindMyBoundService);
        btnBindMyBoundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService();
            }
        });

        btnUnBindMyBoundService  = findViewById(R.id.btnUnBindMyBoundService);
        btnUnBindMyBoundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unBindService();
            }
        });

        btnStartMyIntentService = findViewById(R.id.btnStartMyIntentService);
        btnStartMyIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Main Current Thread: " + Thread.currentThread().getId());
                myIntentService.setAction("RANDOM");
                startService(myIntentService);

            }
        });

        btnStopMyIntentService = findViewById(R.id.btnStopMyIntentService);
        btnStopMyIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(myIntentService);

            }
        });

        btnStopForegroundMyService =findViewById(R.id.btnStopForegroundMyService);
        btnStopForegroundMyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myServiceIntent = new Intent(getApplicationContext(), MyService.class);
                myServiceIntent.setAction("Stop");
                startService(myServiceIntent);

            }
        });

        btnStartMyService2 = findViewById(R.id.btnStartMyService2);
        btnStartMyService2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(myIntentService2);

            }
        });
        btnStopMyService2 = findViewById(R.id.btnStopMyService2);
        btnStopMyService2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(myIntentService2);
            }
        });


        Util.demoScheduleJob(getApplicationContext());
        //work
        Util.demoScheduleWork(getApplicationContext());

    }

    private void startMyBoundService(){
        Log.e(TAG, "Main Current Thread: " + Thread.currentThread().getId());
        startService(myBoundServiceIntent);
    }

    private void bindService(){
        if (MyBoundService.isServiceRunning){
            if (serviceConnection == null){
                serviceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        MyBoundService.MyBoundServiceBinder serviceBinder = (MyBoundService.MyBoundServiceBinder) iBinder;
                        myBoundService = serviceBinder.getService();
                        isServiceBound = true;
                        setTime();

                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {
                        isServiceBound = false;

                    }
                };
            }
            bindService(myBoundServiceIntent,serviceConnection, Context.BIND_AUTO_CREATE);

        }
        else {
            Log.e(TAG,"Service is not running");
        }
    }

    private  void unBindService(){
        if (isServiceBound){
            unbindService(serviceConnection);
            isServiceBound = false;
        }
        else {
            Log.e(TAG, "Service is not bound");
        }
    }

    private void setTime(){
        if (myBoundService == null){
            Log.e(TAG,"Service is not bound");
        }
        else {
            handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Runnable Thread: " + Thread.currentThread().getId());
                    String time  = myBoundService.getTime();
                    lbTime.setText(time);
                    handler.postDelayed(this,1000);
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }




}
