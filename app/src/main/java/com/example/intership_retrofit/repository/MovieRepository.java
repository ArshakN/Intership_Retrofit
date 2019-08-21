package com.example.intership_retrofit.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.intership_retrofit.network.MovieModel;
import com.example.intership_retrofit.network.ApiManager;
import com.example.intership_retrofit.network.MovieService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {


    private static MovieRepository movieRepository;

    private MovieService movieService;


    public MovieRepository() {
        movieService = ApiManager.getApiClient();
    }

    public static MovieRepository getInstance() {
        if (movieRepository == null) {
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    public MutableLiveData<List<MovieModel>> getMovieList() {
        final MutableLiveData<List<MovieModel>> movieData = new MutableLiveData<>();
        Call<List<MovieModel>> call = movieService.getMovies();
        call.enqueue(new Callback<List<MovieModel>>() {

            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if (response.body() == null) {
                    Log.e("SSS", "NULL BODY");
                }
                if (!response.isSuccessful()) {
                    Log.e("SSS", "RESPONSE NOT S");
                }
                Log.e("SSS", "ON RESPONSE REPO");
                if (response.body() != null && response.isSuccessful()) {
                    movieData.setValue(response.body());
                    Log.e("SSS", "ON RESPONSE SUCCCESSFUL");
                }
            }


            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                Log.e("SSS", "ON Failure");
            }
        });
        return movieData;

    }

}