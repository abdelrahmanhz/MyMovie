package com.example.moviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.moviesapp.adapters.MoviesAdapter;
import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.request.Service;
import com.example.moviesapp.response.MovieSearchResponse;
import com.example.moviesapp.utils.Constants;
import com.example.moviesapp.utils.MovieApi;
import com.example.moviesapp.viewmodels.MovieListViewModel;

import org.jetbrains.annotations.NotNull;

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

    // Toolbar
    Toolbar toolbar;

    // ViewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        movieRV = findViewById(R.id.movies_recyclerview);


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        // SearchView
        setupSearchView();
        configureRecyclerView();
        observeAnyChange();

    }

    // SearchView
    private void setupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMoviesApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    // Observing data change
    private void observeAnyChange(){

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

    // Initializing Recyclerview & adding data to it
    private void configureRecyclerView(){

        moviesAdapter = new MoviesAdapter();
        movieRV.setAdapter(moviesAdapter);
        movieRV.setLayoutManager(new LinearLayoutManager(this));

        // RecyclerView Pagination
        movieRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }
}