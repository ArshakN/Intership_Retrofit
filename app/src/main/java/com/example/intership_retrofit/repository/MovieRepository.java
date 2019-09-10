package com.example.intership_retrofit.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.intership_retrofit.persistence.AppDatabase;
import com.example.intership_retrofit.persistence.DBWrapper;
import com.example.intership_retrofit.persistence.entity.Movie;
import com.example.intership_retrofit.network.ApiManager;
import com.example.intership_retrofit.network.MovieService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository movieRepository;
    private MovieService movieService;
    AppDatabase database = DBWrapper.getDatabase();

    public MovieRepository() {
        movieService = ApiManager.getApiClient();
    }

    public static MovieRepository getInstance() {
        if (movieRepository == null) {
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    public MutableLiveData<List<Movie>> getMovieList() {
        final MutableLiveData<List<Movie>> movieData = new MutableLiveData<>();
        Call<List<Movie>> call = movieService.getMovies();
        call.enqueue(new Callback<List<Movie>>() {

            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
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
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e("SSS", "ON Failure");
            }
        });
        return movieData;
    }

    public void saveMovie(final Movie movie) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.movieDao().insert(movie);
                return null;
            }
        }.execute();
    }

    public void deleteMovie(final Movie movie)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.movieDao().delete(movie);
                return null;
            }
        }.execute();

    }

    public LiveData<List<Movie>> getMovie() {
        return database.movieDao().getAll();}
}