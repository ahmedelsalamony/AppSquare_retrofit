package com.example.ahmed.appsquare_retrofit;

import android.app.Application;
import android.content.Context;

/**
 * Created by ahmed on 5/11/2017.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}