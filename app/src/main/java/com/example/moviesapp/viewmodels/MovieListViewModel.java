package com.example.moviesapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel{

    private MovieRepository movieRepository;

    public MovieListViewModel() {

        movieRepository = MovieRepository.getInstance();
    }

    public MutableLiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }
    public MutableLiveData<List<MovieModel>> getPopularMovies(){
        return movieRepository.getPopularMovies();
    }


    public void searchMoviesApi(String query, int pageNumber){
        movieRepository.searchMoviesApi(query, pageNumber);
    }

    public void getPopularMoviesApi(int pageNumber){
        movieRepository.searchPopularMoviesApi(pageNumber);
    }


    public void searchNextPage(){
        movieRepository.searchNextPage();
    }

    public void getNextPage(){
        movieRepository.getNextPage();
    }
}
