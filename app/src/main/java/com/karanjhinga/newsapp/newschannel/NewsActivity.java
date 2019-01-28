package com.karanjhinga.newsapp.newschannel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.karanjhinga.newsapp.data.models.Article;
import com.karanjhinga.newsapp.data.models.NewsList;
import com.karanjhinga.newsapp.data.source.NewsRepository;
import com.karanjhinga.newsapp.data.source.remote.api.ApiClient;
import com.karanjhinga.newsapp.R;
import com.karanjhinga.newsapp.data.utils.interfaces.GetNewsCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity implements GetNewsCallback {

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
        NewsRepository.getInstance().getNews(sourceId,pageNo++,this);
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

    @Override
    public void onNewsFetched(List<Article> newsList) {
        newsAdapter.deleteLastItem();
        newsAdapter.addAll(newsList);
        newsAdapter.add(new Article(1));
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
