package com.example.intership_retrofit.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.intership_retrofit.persistence.entity.Movie;
import com.example.intership_retrofit.repository.MovieRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private LiveData<List<Movie>> listLiveData;
    private MutableLiveData<List<Movie>> mutableLiveData;
    private MovieRepository movieRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<Movie>> getMovieList() {
        mutableLiveData = movieRepository.getMovieList();
        return mutableLiveData;
    }

    public LiveData<List<Movie>> getSaveMovieList() {
        listLiveData = movieRepository.getMovie();
        return listLiveData;
    }

    public void saveMovie(Movie movie){
        if (movieRepository==null){
            Log.i("LOG","NULL REPO");
        }
        else if (movie==null){
            Log.i("LOG","NULL RESULT");
        }
        movieRepository.saveMovie(movie);
    }

    public void deleteMovie(Movie movie) {
        movieRepository.deleteMovie(movie);
    }

}
