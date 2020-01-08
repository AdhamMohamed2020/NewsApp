package com.route.newsapp;

import android.app.Application;

import com.route.newsapp.database.MyDataBase;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyDataBase.init(this);
    }
}
