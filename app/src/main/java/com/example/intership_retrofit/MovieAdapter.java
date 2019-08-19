package com.example.intership_retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

        public OnMovieClickListener onMovieClickListener;
        private List<Model> movieList = new ArrayList<>();
        private Context context;


        public MovieAdapter(Context context) {

            this.context = context;
        }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;

    }

         public void setMovieList(ArrayList<Model> movieList) {
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
            Model result = movieList.get(i);

            movieViewHolder.title.setText(result.getTitle());
            movieViewHolder.raiting.setText(String.valueOf(result.getRating()));
            movieViewHolder.year.setText(String.valueOf(result.getReleaseYear()));
            Glide.with(context).load(result.getImage()).into(movieViewHolder.thumbnail);


       }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        public void addAll(List<Model> list) {
            movieList.addAll(list);
            notifyDataSetChanged();
        }

        void add(Model result) {
            movieList.add(result);
            notifyItemChanged(movieList.size() - 1);
        }

    public interface OnMovieClickListener {

        void onMovieClick(Model currentNews, View viewroot);

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
                        Model movie = movieList.get(getLayoutPosition());
                        onMovieClickListener.onMovieClick(movie, v);
                    }
                });
            }
        }
    }

