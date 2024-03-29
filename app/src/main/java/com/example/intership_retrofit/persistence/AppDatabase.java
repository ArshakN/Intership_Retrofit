package com.example.intership_retrofit.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.intership_retrofit.persistence.entity.Movie;


@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}