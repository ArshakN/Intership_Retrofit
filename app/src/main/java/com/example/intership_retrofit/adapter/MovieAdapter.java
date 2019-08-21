package com.example.intership_retrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.intership_retrofit.network.MovieModel;
import com.example.intership_retrofit.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

        public OnMovieClickListener onMovieClickListener;
        private List<MovieModel> movieList = new ArrayList<>();
        private Context context;


        public MovieAdapter(Context context) {
            this.context = context;
        }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

         public void setMovieList(ArrayList<MovieModel> movieList) {
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
        public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
            MovieModel result = movieList.get(i);

            movieViewHolder.title.setText(result.getTitle());
            movieViewHolder.raiting.setText(String.valueOf(result.getRating()));
            movieViewHolder.year.setText(String.valueOf(result.getReleaseYear()));
            Glide.with(context).load(result.getImage()).into(movieViewHolder.thumbnail);
       }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        public void addAll(List<MovieModel> list) {
            movieList.addAll(list);
            notifyDataSetChanged();
        }

        void add(MovieModel result) {
            movieList.add(result);
            notifyItemChanged(movieList.size() - 1);
        }

    public interface OnMovieClickListener {

        void onMovieClick(MovieModel currentNews, View viewroot);

    }

        public class MovieViewHolder extends RecyclerView.ViewHolder {

            ImageView thumbnail;
            TextView title;
            TextView raiting;
            TextView year;


            public MovieViewHolder(@NonNull View itemView) {
                super(itemView);
                thumbnail = itemView.findViewById(R.id.image_id);
                title = itemView.findViewById(R.id.title_id);
                raiting = itemView.findViewById(R.id.raiting_id);
                year = itemView.findViewById(R.id.year_id);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MovieModel movie = movieList.get(getLayoutPosition());
                        onMovieClickListener.onMovieClick(movie, v);
                    }
                });
            }
        }
    }

