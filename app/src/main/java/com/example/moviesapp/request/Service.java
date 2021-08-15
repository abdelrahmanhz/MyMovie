package com.example.moviesapp.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.moviesapp.utils.Constants;
import com.example.moviesapp.utils.MovieApi;

public class Service {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }
}
