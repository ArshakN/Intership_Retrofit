package com.example.intership_retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<Model> movieData;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;
    private MovieAdapter movieAdapter;
    private Model movieModel;
    private int visible_items, total_items, scroll_out_items;
    private Boolean isScrolling = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setupRecyclerView();




    }

    public void findView()
    {
        recyclerView=findViewById(R.id.recycler_view);
        swipeRefresh=findViewById(R.id.swiperefresh);
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
        } else {
            movieAdapter.notifyDataSetChanged();
        }
    }

    private void fillMovie() {
        Call<List<Model>> call = ApiManager.getApiClient().getMovies();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    movieData = response.body();
                    movieAdapter.addAll(movieData);
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Log.e("ARSH", "OnFailure", t);
            }

        });

    }

}
