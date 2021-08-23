package com.example.moviesapp.request;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.response.MovieSearchResponse;
import com.example.moviesapp.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    // LiveData for search
    private final MutableLiveData<List<MovieModel>> mMovies;

    // LiveData for popular movies
    private final MutableLiveData<List<MovieModel>> mPopularMovies;
    private static MovieApiClient instance;

    private RetrieveMoviesRunnable retrieveMoviesRunnable;
    private RetrievePopularMoviesRunnable retrievePopularMoviesRunnable;

    public static MovieApiClient getInstance() {
        if (instance == null)
            instance = new MovieApiClient();
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mPopularMovies = new MutableLiveData<>();
    }

    public MutableLiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }
    public MutableLiveData<List<MovieModel>> getPopularMovies() {
        return mPopularMovies;
    }


    public void searchMoviesApi(String query, int pageNumber) {



        if(retrieveMoviesRunnable != null)
            retrieveMoviesRunnable = null;

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(() -> {
            // Cancelling the retrofit call
            myHandler.cancel(true);
        }, 3000, TimeUnit.MILLISECONDS);
    }


    public void searchPopularMoviesApi(int pageNumber) {



        if(retrievePopularMoviesRunnable != null)
            retrievePopularMoviesRunnable = null;

        retrievePopularMoviesRunnable = new RetrievePopularMoviesRunnable(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrievePopularMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(() -> {
            // Cancelling the retrofit call
            myHandler2.cancel(true);
        }, 1000, TimeUnit.MILLISECONDS);
    }


    // Retrieving data from REST Api by runnable class
    private class RetrieveMoviesRunnable implements Runnable {

        private final String query;
        private final int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response = getMovies(query, pageNumber).execute();

                if (cancelRequest)
                    return;

                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());

                    if (pageNumber == 1){
                        mMovies.postValue(list);

                    }
                    else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }
                else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }



        }
            private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
                return Service.getMovieApi().searchMovies(
                        Constants.API_KEY,
                        query,
                        pageNumber
                );

        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrievePopularMoviesRunnable implements Runnable {

        private final int pageNumber;
        boolean cancelRequest;

        public RetrievePopularMoviesRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response2 = getPopularMovies(pageNumber).execute();

                if (cancelRequest)
                    return;

                if(response2.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());

                    if (pageNumber == 1){
                        mPopularMovies.postValue(list);

                    }
                    else {
                        List<MovieModel> currentMovies = mPopularMovies.getValue();
                        currentMovies.addAll(list);
                        mPopularMovies.postValue(currentMovies);
                    }
                }
                else {
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mPopularMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mPopularMovies.postValue(null);
            }



        }
        private Call<MovieSearchResponse> getPopularMovies(int pageNumber){
            return Service.getMovieApi().getPopularMovies(
                    Constants.API_KEY,
                    pageNumber
            );

        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }
}


