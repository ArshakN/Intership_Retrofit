package com.example.intership_retrofit.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.intership_retrofit.persistence.entity.Movie;


import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);


}

