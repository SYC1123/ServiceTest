package com.example.servicetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static java.lang.Thread.sleep;

public class MyService extends Service {

    private boolean isWork = true;
    private static int time = 0;

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    //创建服务的时候调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate: ");

    }

    //每次服务启动的时候调用，添加服务一旦启动就执行的逻辑
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand: ");
        //final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isWork) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                    final Notification notification = new NotificationCompat.Builder(getApplication())
                            .setContentTitle("标题")
                            .setContentText(time+"")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                            .setContentIntent(pi)
                            .build();
                      //manager.notify(1,notification);
                    startForeground(1, notification);
//                    if(time==5){
//                        stopSelf();
//                    }
                    time++;
                    Log.d("TAG", "run: " + time);
                }
                time=0;
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    //服务销毁的时候调用
    @Override
    public void onDestroy() {
        isWork = false;
        super.onDestroy();
        Log.d("MyService", "onDestroy: ");
    }
}
