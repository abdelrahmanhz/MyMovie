package com.example.moviesapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.utils.Constants;

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
            Log.v("Tagv", "The clicked Movie is: " + movie.getTitle());

            movieTitleTV.setText(movie.getTitle());
            movieOverviewTV.setText(movie.getOverview());
            movieRateRB.setRating(movie.getVote_average() / 2);

            Glide.with(this)
                    .load(Constants.IMG_URL + movie.getPoster_path())
                    .into(movieIV);


        }
    }
}