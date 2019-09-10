package com.example.intership_retrofit.network;

import com.example.intership_retrofit.persistence.entity.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {
    @GET("/json/movies.json")
    Call<List<Movie>> getMovies();
}
