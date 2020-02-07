package com.example.moremovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.RowViewHolder>{

    private Context context;
    private MoviesListFragment moviesListFragment;
    private List<Movie> moviesList;
    private ItemClickListener mClickListener;

    MoviesAdapter(Context context, MoviesListFragment listFragment) {
        this.moviesListFragment = listFragment;
        this.context = context;
        moviesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_list_movies, parent,false);

        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {

        holder.viewTitle.setText(moviesList.get(position).getTitle());
        holder.viewReleaseYear.setText(String.valueOf(moviesList.get(position).getYear()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class RowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView viewTitle;
        TextView viewReleaseYear;

        RowViewHolder(View view) {

            super(view);
            viewTitle = view.findViewById(R.id.title);
            viewReleaseYear = view.findViewById(R.id.releaseYear);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            final Movie currentMovie = moviesList.get(getAdapterPosition());
            moviesListFragment.setCurrentMovie(currentMovie);
            mClickListener.onItemClick(currentMovie);
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(Movie movie);
    }

    void attachMoviesList(List<Movie> movieList) {
        // getting list from Fragment.
        this.moviesList = movieList;
    }
}
