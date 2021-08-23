package com.example.moviesapp.utils;

import com.example.moviesapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    // Search for movies
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovies(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );


    // Get popular movies
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopularMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );
}
