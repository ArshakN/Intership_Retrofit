package com.example.intership_retrofit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.intership_retrofit.App;
import com.example.intership_retrofit.persistence.entity.Movie;
import com.example.intership_retrofit.R;
import com.example.intership_retrofit.view.MainActivity;
import com.example.intership_retrofit.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    MovieViewModel movieViewModel;
    public OnMovieClickListener onMovieClickListener;
    private List<Movie> movieList = new ArrayList<>();
    private Context context;


    public MovieAdapter(Context context, MovieViewModel movieViewModel) {
        this.context = context;
        this.movieViewModel=movieViewModel;
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model, viewGroup, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, final int i) {
        Movie result = movieList.get(i);

        movieViewHolder.title.setText(result.getTitle());
        movieViewHolder.raiting.setText(String.valueOf(result.getRating()));
        movieViewHolder.year.setText(String.valueOf(result.getReleaseYear()));
        Glide.with(context).load(result.getImage()).into(movieViewHolder.thumbnail);

        movieViewHolder.saveMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieViewModel.saveMovie(movieList.get(i));
                Toast.makeText(context,"Movie Saved to offline view",Toast.LENGTH_SHORT).show();
            }
        });

        movieViewHolder.deleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieViewModel.deleteMovie(movieList.get(i));
                Toast.makeText(context,"Movie Deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addAll(List<Movie> list) {
        movieList.addAll(list);
        notifyDataSetChanged();
    }

    void add(Movie result) {
        movieList.add(result);
        notifyItemChanged(movieList.size() - 1);
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie currentMovie, View viewroot);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title;
        TextView raiting;
        TextView year;
        ImageView saveMovie;
        ImageView deleteMovie;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.image_id);
            title = itemView.findViewById(R.id.title_id);
            raiting = itemView.findViewById(R.id.raiting_id);
            year = itemView.findViewById(R.id.year_id);
            saveMovie=itemView.findViewById(R.id.id_save);
            deleteMovie=itemView.findViewById(R.id.id_delete);

            if (App.isNetworkEnabled){
                saveMovie.setVisibility(View.VISIBLE);
                deleteMovie.setVisibility(View.INVISIBLE);
            }
            else{
                deleteMovie.setVisibility(View.VISIBLE);
                saveMovie.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie = movieList.get(getLayoutPosition());
                    onMovieClickListener.onMovieClick(movie, v);
                }
            });
        }
    }
}

