package com.example.moviesapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.models.MovieModel;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    List<MovieModel> mMovies = new ArrayList<>();

    public void setMovies(List<MovieModel> movieViews) {
        this.mMovies = movieViews;
        Log.v("Tag1", movieViews.get(0).getTitle());
        notifyDataSetChanged();
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.movieTitle.setText(mMovies.get(position).getTitle());
        holder.movieReleaseDate.setText(mMovies.get(position).getRelease_date());
        holder.movieDuration.setText(mMovies.get(position).getRuntime() + "");
        holder.movieRB.setRating((mMovies.get(position).getVote_average()) / 2);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(position).getPoster_path())
                .into(holder.movieIV);

    }

    @Override
    public int getItemCount() {
        if(mMovies != null)
            return mMovies.size();
        return 0;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView movieTitle, movieReleaseDate, movieDuration;
        ImageView movieIV;
        RatingBar movieRB;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            movieTitle = itemView.findViewById(R.id.movie_title);
            movieReleaseDate = itemView.findViewById(R.id.movie_release_date);
            movieDuration = itemView.findViewById(R.id.movie_duration);
            movieIV = itemView.findViewById(R.id.movie_img);
            movieRB = itemView.findViewById(R.id.rating_bar);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
