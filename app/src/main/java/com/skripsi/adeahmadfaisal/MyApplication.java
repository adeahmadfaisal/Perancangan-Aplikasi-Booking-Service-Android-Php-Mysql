package com.skripsi.adeahmadfaisal;

import android.app.Application;
import android.content.Context;

import com.onesignal.OneSignal;
import com.skripsi.adeahmadfaisal.notification.MyNotificationOpenedHandler;
import com.skripsi.adeahmadfaisal.notification.MyNotificationReceivedHandler;

public class MyApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    //Called when the application is starting, before any other application objects have been created.
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //MyNotificationOpenedHandler : This will be called when a notification is tapped on.
        //MyNotificationReceivedHandler : This will be called when a notification is received while your app is running.
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .setNotificationReceivedHandler( new MyNotificationReceivedHandler() )
                .init();
    }


}
