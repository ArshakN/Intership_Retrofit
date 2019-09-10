package com.example.intership_retrofit.view;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.intership_retrofit.persistence.entity.Movie;
import com.example.intership_retrofit.viewmodel.MovieViewModel;
import com.example.intership_retrofit.R;


public class Detailed_Activity extends AppCompatActivity {
    public static final String BUNDLE_KEY = "MOVIE_KEY";
    private TextView title;
    private ImageView movieImage;
    private MovieViewModel movieViewModel;
    private Bundle bundle;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.init();
        initViews();
        bundle = getIntent().getBundleExtra("A");
        currentMovie = bundle.getParcelable(BUNDLE_KEY);
        title.setText(currentMovie.getTitle());
        Glide.with(this).load(currentMovie.getImage()).into(movieImage);

    }

    private void initViews() {
        title = findViewById(R.id.detailed_title);
        movieImage = findViewById(R.id.detailed_image);
    }
}