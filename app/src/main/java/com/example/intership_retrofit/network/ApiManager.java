package com.example.intership_retrofit.network;

import com.example.intership_retrofit.network.MovieService;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {


    private static final String BASE_URL = "https://api.androidhive.info";


    private static MovieService mApiClient;

    public static MovieService getApiClient() {
        if (mApiClient == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                    .build();


            mApiClient = retrofit.create(MovieService.class);
        }


        return mApiClient;

    }
}
