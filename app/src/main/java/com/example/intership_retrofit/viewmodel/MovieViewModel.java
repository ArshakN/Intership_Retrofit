package com.example.intership_retrofit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.intership_retrofit.network.MovieModel;
import com.example.intership_retrofit.repository.MovieRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {


    private MutableLiveData<List<MovieModel>> mutableLiveData;
    private MovieRepository movieRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        movieRepository = MovieRepository.getInstance();


    }

    public LiveData<List<MovieModel>> getMovieList() {
        mutableLiveData = movieRepository.getMovieList();
        return mutableLiveData;
    }



}
