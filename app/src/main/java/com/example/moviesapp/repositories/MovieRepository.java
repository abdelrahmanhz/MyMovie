package com.example.moviesapp.repositories;


import androidx.lifecycle.MutableLiveData;
import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.request.MovieApiClient;
import java.util.List;

public class MovieRepository {

    private final MovieApiClient movieApiClient;

    private static MovieRepository instance;

    private String mQuery;
    private int mPageNumber;



    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public MutableLiveData<List<MovieModel>> getMovies(){return movieApiClient.getMovies();}
    public MutableLiveData<List<MovieModel>> getPopularMovies(){return movieApiClient.getPopularMovies();}

    public void searchMoviesApi(String query, int pageNumber){

        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query, pageNumber);
    }
    public void searchPopularMoviesApi(int pageNumber){
        mPageNumber = pageNumber;
        movieApiClient.searchPopularMoviesApi(pageNumber);
    }

    public void searchNextPage(){
        searchMoviesApi(mQuery, mPageNumber + 1);
    }
    public void getNextPage(){
        searchPopularMoviesApi(mPageNumber + 1);
    }

}
