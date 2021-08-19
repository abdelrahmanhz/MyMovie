package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.example.moviesapp.adapters.MoviesAdapter;
import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.request.Service;
import com.example.moviesapp.response.MovieSearchResponse;
import com.example.moviesapp.utils.Constants;
import com.example.moviesapp.utils.MovieApi;
import com.example.moviesapp.viewmodels.MovieListViewModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    // RecyclerView
    private RecyclerView movieRV;
    private MoviesAdapter moviesAdapter;

    // ViewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieRV = findViewById(R.id.movies_recyclerview);


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        searchMoviesApi("fast", 1);


    }

    public void searchMoviesApi(String query, int pageNumber){
        movieListViewModel.searchMoviesApi(query, pageNumber);
    }

    // Observing data change
    private void ObserveAnyChange(){

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());

                        moviesAdapter.setMovies(movieModels);
                    }
                }
            }
        });
    }

    private void GetRetrofitResponse() {

        MovieApi movieApi = Service.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovies(
                        Constants.API_KEY,
                        "Attack",
                        1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200){
                    Log.v("Tag", "the response" + response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for (MovieModel movie : movies){
                        Log.v("Tag", "the movie title: " + movie.getTitle());
                    }
                }
                else {
                    try {
                        Log.v("Tag", "Error " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    // Initializing Recyclerview & adding data to it
    private void ConfigureRecyclerView(){

        moviesAdapter = new MoviesAdapter();
        movieRV.setAdapter(moviesAdapter);
        movieRV.setLayoutManager(new LinearLayoutManager(this));
    }
}