package com.example.intership_retrofit;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

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

    public MutableLiveData<List<Model>> getMovieList() {
        final MutableLiveData<List<Model>> movieData = new MutableLiveData<>();
        Call<List<Model>> call = movieService.getMovies();
        call.enqueue(new Callback<List<Model>>() {

            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
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
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Log.e("SSS", "ON Failure");
            }
        });
        return movieData;

    }

}