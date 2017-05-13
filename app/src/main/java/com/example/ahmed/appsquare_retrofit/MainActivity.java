package com.example.ahmed.appsquare_retrofit;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ahmed.appsquare_retrofit.adapter.RepoAdapter;
import com.example.ahmed.appsquare_retrofit.model.RepoResult;
import com.example.ahmed.appsquare_retrofit.rest.ApiClient;
import com.example.ahmed.appsquare_retrofit.rest.ApiInterface;
import com.example.ahmed.appsquare_retrofit.listener.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    int repoPage=1;
    RecyclerView recyclerView;
    RepoAdapter adapter;
    List<RepoResult> repoResults=new ArrayList<RepoResult>();
    private EndlessRecyclerViewScrollListener scrollListener;
    private SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter=new RepoAdapter(repoResults, R.layout.single_row_main_activity, MainActivity.this);
        recyclerView.setAdapter(adapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                recyclerView.getRecycledViewPool().clear();
                getRepodata(repoPage);
                swipeContainer.setRefreshing(false);
            }
        });



        scrollListener= new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                getRepodata(page);

            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        getRepodata(repoPage);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void getRepodata(final int page){
        ApiInterface apiInterface= ApiClient.getRetrofit().create(ApiInterface.class);

        Call<List<RepoResult>> call=apiInterface.getRepos(page,10);

        call.enqueue(new Callback<List<RepoResult>>() {
            @Override
            public void onResponse(Call<List<RepoResult>> call, Response<List<RepoResult>> response) {

                         repoResults.addAll(response.body());
                         adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<RepoResult>> call, Throwable t) {
                Log.i(TAG,t.getMessage());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            getRepodata(repoPage);
        }

        return super.onOptionsItemSelected(item);
    }
}
