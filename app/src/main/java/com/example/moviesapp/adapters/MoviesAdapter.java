package com.example.moviesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.models.MovieModel;
import com.example.moviesapp.utils.Constants;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<MovieModel> mMovies;
    private final OnMovieListener onMovieListener;


    public MoviesAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }


    public void setMovies(List<MovieModel> movieViews) {
        this.mMovies = movieViews;
//        Log.v("Tag1", movieViews.get(0).getTitle());
        notifyDataSetChanged();
    }


    public MovieModel getSelectedMovie(int position){
        if(mMovies != null){
            if(getItemCount() > 0){
                return mMovies.get(position);
            }
        }
        return null;
    }


    @NotNull
    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                    parent, false), onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MoviesViewHolder holder, int position) {
        holder.movieTitle.setText(mMovies.get(position).getTitle());
        holder.movieReleaseDate.setText(mMovies.get(position).getRelease_date());
        holder.movieRB.setRating((mMovies.get(position).getVote_average()) / 2);
        Glide.with(holder.itemView.getContext())
                .load(Constants.IMG_URL + mMovies.get(position).getPoster_path())
                .into(holder.movieIV);
    }


    @Override
    public int getItemCount() {
        if(mMovies != null)
            return mMovies.size();
        return 0;
    }



    public static class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        // Widgets
        TextView movieTitle, movieReleaseDate;
        ImageView movieIV;
        RatingBar movieRB;

        // Click Listener
        OnMovieListener onMovieListener;


        public MoviesViewHolder(View itemView, OnMovieListener onMovieListener) {
            super(itemView);

            this.onMovieListener = onMovieListener;

            movieTitle = itemView.findViewById(R.id.movie_title);
            movieReleaseDate = itemView.findViewById(R.id.movie_release_date);
            movieIV = itemView.findViewById(R.id.movie_img);
            movieRB = itemView.findViewById(R.id.rating_bar);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            onMovieListener.onMovieClick(getAdapterPosition());
        }
    }

    public interface OnMovieListener {

        void onMovieClick(int position);
        //void onCategoryClick(String category);
    }
}
