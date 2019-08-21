package com.example.intership_retrofit.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intership_retrofit.network.MovieModel;
import com.example.intership_retrofit.viewmodel.MovieViewModel;
import com.example.intership_retrofit.R;
import com.example.intership_retrofit.adapter.MovieAdapter;
import com.example.intership_retrofit.network.ApiManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.intership_retrofit.view.Detailed_Activity.BUNDLE_KEY;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {
    private List<MovieModel> movieData;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private int visible_items, total_items, scroll_out_items;
    private Boolean isScrolling = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        linearLayoutManager=new LinearLayoutManager(this);
        setupRecyclerView();
        movieViewModel= ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.init();

        movieViewModel.getMovieList().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(@Nullable List<MovieModel> model) {
                Log.e("SSS", "ON CHANGED");
                setupRecyclerView();
                movieAdapter.setMovieList((ArrayList<MovieModel>) model);

            }
        });


    }

    public void findView()
    {
        recyclerView=findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.progressBar_id);
    }

    public void setupRecyclerView()
    {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(MainActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(movieAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScrolling = true;
                    }
                }
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    visible_items = linearLayoutManager.getChildCount();
                    total_items = linearLayoutManager.getItemCount();
                    scroll_out_items = linearLayoutManager.findFirstVisibleItemPosition();
                    if (isScrolling && visible_items + scroll_out_items == total_items) {
                        isScrolling = false;
                        progressBar.setVisibility(View.VISIBLE);
                      fillMovie();
                    }
                }
            });
            movieAdapter.setOnMovieClickListener(this);
        } else {
            movieAdapter.notifyDataSetChanged();
        }
    }

    private void fillMovie() {
        Call<List<MovieModel>> call = ApiManager.getApiClient().getMovies();
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    movieData = response.body();
                    movieAdapter.addAll(movieData);
                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                Log.e("ARSH", "OnFailure", t);
            }

        });

    }

    @Override
    public void onMovieClick(MovieModel currentMovie, View viewroot) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY, currentMovie);
        Intent intent = new Intent(this, Detailed_Activity.class);
        intent.putExtra("A", bundle);
        intent.putExtra("B", currentMovie.getImage());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade(Fade.IN));
            getWindow().setEnterTransition(new Fade(Fade.OUT));
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(viewroot.findViewById(R.id.title_id), "text_trans"),
                            new Pair<View, String>(viewroot.findViewById(R.id.image_id), "image_trans"));

            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
    }

