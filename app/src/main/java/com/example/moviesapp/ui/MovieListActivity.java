package com.example.moviesapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.moviesapp.R;
import com.example.moviesapp.adapters.MoviesAdapter;
import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.utils.Constants;
import com.example.moviesapp.viewmodels.MovieListViewModel;
import org.jetbrains.annotations.NotNull;


public class MovieListActivity extends AppCompatActivity implements MoviesAdapter.OnMovieListener {

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

        // Get popular movies
        observePopularMovies();
        observeAnyChange();
        movieListViewModel.getPopularMoviesApi(1);

    }



    // SearchView
    private void setupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Constants.POPULAR = false;
                movieListViewModel.searchMoviesApi(query, 1);
                Log.v("Tag", "Constants.POPULAR = " + Constants.POPULAR);
                moviesAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() == 0){
                    Constants.POPULAR = true;
                    movieListViewModel.getPopularMoviesApi(1);
                    Log.v("Tag", "Constants.POPULAR = " + Constants.POPULAR);
                    moviesAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });


//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//
//                return false;
//            }
//        });

    }


    // Observing data change
    private void observePopularMovies() {

        movieListViewModel.getPopularMovies().observe(this, movieModels -> {
            if(movieModels != null && Constants.POPULAR){
                for(MovieModel movieModel: movieModels){
                    Log.v("Tag", "onChanged: " + movieModel.getTitle());
                    moviesAdapter.setMovies(movieModels);
                }
            }
        });


    }


    private void observeAnyChange(){

        movieListViewModel.getMovies().observe(this, movieModels -> {
            if(movieModels != null && !Constants.POPULAR){
                for(MovieModel movieModel: movieModels){
                    Log.v("Tag", "onChanged: " + movieModel.getTitle());
                    moviesAdapter.setMovies(movieModels);
                }
            }
        });
    }


    // Initializing Recyclerview & adding data to it
    private void configureRecyclerView(){

        moviesAdapter = new MoviesAdapter(this);
        movieRV.setAdapter(moviesAdapter);
        movieRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // RecyclerView Pagination

            movieRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                    if(!recyclerView.canScrollHorizontally(1)){
                        if(Constants.POPULAR)
                            movieListViewModel.getNextPage();
                        else{
                            movieListViewModel.searchNextPage();
                        }

                    }
                }
            });
        }



    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra("movie", moviesAdapter.getSelectedMovie(position));
        startActivity(intent);
    }


}