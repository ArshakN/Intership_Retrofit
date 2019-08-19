package com.example.intership_retrofit;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;


public class Detailed_Activity extends AppCompatActivity {
    public static final String BUNDLE_KEY = "MOVIE_KEY";
    private TextView title;
    private ImageView movieImage;
    private FilmsViewModel movieViewModel;
    private Bundle bundle;
    private Model currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        movieViewModel = ViewModelProviders.of(this).get(FilmsViewModel.class);
        movieViewModel.init();
        initViews();
        bundle = getIntent().getBundleExtra("A");
        currentMovie = bundle.getParcelable(BUNDLE_KEY);
        title.setText(currentMovie.getTitle());
        String url = getIntent().getStringExtra("B");
        currentMovie.setImage(url);
        Glide.with(this).load(url).into(movieImage);

    }

    private void initViews() {
        title = findViewById(R.id.detailed_title);
        movieImage = findViewById(R.id.detailed_image);
    }
}