package com.loktra.githubcommits.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loktra.githubcommits.R;
import com.loktra.githubcommits.adapter.CommitRecyclerAdapter;
import com.loktra.githubcommits.api.RestClientAdapter;
import com.loktra.githubcommits.api.SearchQuery;
import com.loktra.githubcommits.model.GithubData;
import com.loktra.githubcommits.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static String BASE_URL = "https://api.github.com/";
    @BindView(R.id.recycler_view_commit)
    RecyclerView mRecyclerView;

    private CommitRecyclerAdapter mAdapter;
    private SearchQuery searchAdapter;
    List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRestAdapter();
        getDataFromAPI();
    }

    //api call to get data from github repo
    private void getDataFromAPI() {
        Call<GithubData> call = searchAdapter.getSearchQuery();
        call.enqueue(new Callback<GithubData>() {
            @Override
            public void onResponse(Call<GithubData> call, Response<GithubData> response) {
                if(response.isSuccessful()) {
                    itemList = response.body().getItems();
                    if (itemList.size() > 25) {
                        itemList = itemList.subList(0, 25);
                    }
                    setupRecycleView();
                } else {
                    Toast.makeText(MainActivity.this,"Could not get Data",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GithubData> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Could not get Data",Toast.LENGTH_SHORT).show();
            }
        });

    }

    //setup recyclerview
    private void setupRecycleView() {
        mAdapter = new CommitRecyclerAdapter(itemList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    //setup api call adapter
    private void setupRestAdapter() {
        searchAdapter = RestClientAdapter.createRestAdapter(SearchQuery.class,BASE_URL,this);
    }
}
