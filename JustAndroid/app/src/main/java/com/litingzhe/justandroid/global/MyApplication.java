package com.litingzhe.justandroid.global;

import android.app.Application;

/**
 *
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
    }



    public static MyApplication getInstance() {
        return instance;
    }


}

