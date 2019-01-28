package com.karanjhinga.newsapp.NewsChannel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.karanjhinga.newsapp.Data.Models.Article;
import com.karanjhinga.newsapp.Data.Models.NewsList;
import com.karanjhinga.newsapp.Data.Source.remote.ApiClient;
import com.karanjhinga.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView newsRecycler;
    private NewsAdapter newsAdapter;
    private LinearLayoutManager manager;
    private int pageNo = 1;
    private String sourceId;
    private List<Article> list = new ArrayList<>(); //LIST OF ITEMS FETCHED FROM API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setupToolbar();     //CUSTOM TOOLBAR WITH CENTERED TEXT AND HIDING WHILE SCROLLING
        inflateViews();    //INITIALIZE ALL CLASS MEMBERS

        sourceId = getIntent().getStringExtra("sourceId"); // GETTING SOURCE ID FROM THE PREVIOUS ACTIVITY

        newsRecycler.setHasFixedSize(true); // PERFORMANCE HACK

        newsRecycler.setLayoutManager(manager);
        newsRecycler.setAdapter(newsAdapter);

        newsAdapter.add(new Article(1)); //ADD PROGRESS BAR TO RECYCLER VIEW INITIALLY

        /* LISTEN FOR SCROLLING AND LOAD DATA WHEN LAST ITEM IS VISIBLE */
        newsRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisiblePosition = manager.findLastCompletelyVisibleItemPosition();
                int totalItems = manager.getItemCount();
                if (lastVisiblePosition == totalItems-1){
                    loadMoreData();
                }
            }
        });
    }

    /* METHOD TO LOAD MORE DATA*/
    private void loadMoreData() {

        ApiClient.getInstance().getApi().getNews(sourceId,ApiClient.api_key,pageNo++,10).enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(@NonNull Call<NewsList> call, @NonNull Response<NewsList> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(NewsActivity.this, "Error: "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                list = response.body().articles;
                newsAdapter.deleteLastItem();
                newsAdapter.addAll(list);
                newsAdapter.add(new Article(1));

            }

            @Override
            public void onFailure(@NonNull Call<NewsList> call, @NonNull Throwable t) {
                Toast.makeText(NewsActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inflateViews() {
        newsRecycler = findViewById(R.id.newsRecycler);
        newsAdapter = new NewsAdapter(this,list);
        manager = new LinearLayoutManager(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        String sourceName = getIntent().getStringExtra("sourceName"); //SOURCE NAME FROM PREVIOUS ACTIVITY
        toolbarTitle.setText(sourceName);  // CHANGING TOOLBAR TITLE TO SOURCE NAME
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // SHOW BACK BUTTON ON OUR TOOLBAR
    }
}
