package com.example.moviesapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel{

    private MovieRepository movieRepository;

    public MovieListViewModel() {

        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    public void searchMoviesApi(String query, int pageNumber){
        movieRepository.searchMoviesApi(query, pageNumber);
    }
}
