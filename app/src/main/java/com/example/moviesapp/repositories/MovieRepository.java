package com.example.moviesapp.repositories;

import androidx.lifecycle.LiveData;
import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private MovieApiClient movieApiClient;

    private static MovieRepository instance;



    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){return movieApiClient.getMovies();}
}
