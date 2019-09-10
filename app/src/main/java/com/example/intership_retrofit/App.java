package com.example.intership_retrofit;

import android.app.Application;

import com.example.intership_retrofit.Utils.NetworkCheck;
import com.example.intership_retrofit.persistence.DBWrapper;


public class App extends Application {
    public static boolean isNetworkEnabled;
    @Override
    public void onCreate() {
        super.onCreate();
        DBWrapper.create(this);
        isNetworkEnabled = NetworkCheck.isNetworkAvailable(this);
    }


}