package com.example.intership_retrofit.persistence;

import android.content.Context;

import androidx.room.Room;

public class DBWrapper {

    private static AppDatabase instance = null;

    public static void create(Context context) {

        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AppDatabase.class, "database").build();
                }
            }
        }
    }

    public static AppDatabase getDatabase() {
        if (instance == null) {
            throw new IllegalStateException("Database not created");
        }
        return instance;
    }
}
