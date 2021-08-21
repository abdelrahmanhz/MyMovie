package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moviesapp.models.MovieModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView movieIV;
    private TextView movieTitleTV, movieOverviewTV;
    private RatingBar movieRateRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieIV = findViewById(R.id.imageView_details);
        movieTitleTV = findViewById(R.id.textView_title_details);
        movieOverviewTV = findViewById(R.id.textView_overview_details);
        movieRateRB = findViewById(R.id.ratingBar_details);

        getDateFromIntent();
    }

    private void getDateFromIntent() {

        if(getIntent().hasExtra("movie")){
            MovieModel movie = getIntent().getParcelableExtra("movie");
        }
    }
}