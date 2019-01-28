package com.karanjhinga.newsapp.newssource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karanjhinga.newsapp.categories.CategoryAdapter;
import com.karanjhinga.newsapp.data.source.NewsRepository;
import com.karanjhinga.newsapp.data.source.remote.NewsRemoteRepository;
import com.karanjhinga.newsapp.data.utils.interfaces.GetSourcesCallback;
import com.karanjhinga.newsapp.newschannel.NewsActivity;
import com.karanjhinga.newsapp.categories.CategoryListener;
import com.karanjhinga.newsapp.data.models.Source;
import com.karanjhinga.newsapp.data.models.SourceList;
import com.karanjhinga.newsapp.data.source.remote.api.ApiClient;
import com.karanjhinga.newsapp.utils.Helper;
import com.karanjhinga.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CategoryListener, SourceSelectedListener, GetSourcesCallback {

    private RelativeLayout contentMain;
    private LinearLayout noInternetLayout,loadingLayout,categoryLayout;
    private TextView reloadButton,categorySelected;
    private ImageView removeCategory;
    private List<Source> sourceList = new ArrayList<>();
    private RecyclerView categoryRecycler,sourceRecycler;
    private CategoryAdapter categoryAdapter;
    private SourcesAdapter sourcesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflateViews(); // METHOD TO INFLATE ALL VIEWS

        /* CATEGORY RECYCLER VIEW */
        categoryRecycler.setHasFixedSize(true); //INCREASE PERFORMANCE OUR OUR RECYCLER VIEW
        categoryRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        categoryRecycler.setAdapter(categoryAdapter);

        /* SOURCES RECYCLER VIEW */
        sourceRecycler.setHasFixedSize(true);
        sourceRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        sourceRecycler.setLayoutManager(new LinearLayoutManager(this));
        sourceRecycler.setAdapter(sourcesAdapter);

        loadData(); // LOAD ALL SOURCES INITIALLY

        /* RELOAD BUTTON OF OUR NO INTERNET LAYOUT */
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

        /* BUTTON TO CLEAR OUR CATEGORY SELECTION */
        removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryLayout.setVisibility(View.GONE);
                categoryRecycler.setVisibility(View.VISIBLE);
                loadData();
            }
        });
    }

    private void inflateViews() {
        contentMain = findViewById(R.id.contentMain);
        noInternetLayout = findViewById(R.id.noInternetLayout);
        loadingLayout = findViewById(R.id.loadingLayout);
        reloadButton = findViewById(R.id.reloadButton);
        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryAdapter = new CategoryAdapter(this,this);

        categoryLayout = findViewById(R.id.categorySelectedLayout);
        categorySelected = findViewById(R.id.categoryName);
        removeCategory = findViewById(R.id.removeCategory);

        sourceRecycler = findViewById(R.id.sourceRecycler);
        sourcesAdapter = new SourcesAdapter(this,sourceList,this);
    }

    // THIS FUNCTION INVOKES WHEN A CATEGORY IS SELECTED
    @Override
    public void onClicked(String category) {
        categoryLayout.setVisibility(View.VISIBLE); //SHOW A CATEGORY SELECTION VIEW
        categorySelected.setText(category+" News Sources");
        categoryRecycler.setVisibility(View.GONE); //HIDE ALL CATEGORIES
        loadData(category.toLowerCase()); // LOAD SOURCES SPECIFIC TO A CATEGORY
    }



    /* THIS METHOD INVOKES WHEN A SOURCE IS SELECTED */
    @Override
    public void onSourceSelected(Source source) {
        Intent i = new Intent(this,NewsActivity.class);
        i.putExtra("sourceId",source.id);                               // PASSING SOURCE NAME AND ID TO NEXT ACTIVITY
        i.putExtra("sourceName",source.name);
        startActivity(i);
    }

    // FUNCTIONALITY OF LOADING ALL SOURCES
    private void loadData() {
        internetAndLoading();
        NewsRepository.getInstance().getAllSources(this);

    }

    //FUNCTIONALITY OF LOADING SOURCES BY CATEGORY
    private void loadData(String category) {
        internetAndLoading();
        NewsRepository.getInstance().getAllSourcesByCategory(category, this);

    }

    private void internetAndLoading(){
        if (!Helper.isNetworkConnected(this)){
            noInternetLayout.setVisibility(View.VISIBLE);           //SHOW NO INTERNET CONNECTION
            contentMain.setVisibility(View.GONE);
            return;
        }
        noInternetLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);                    //SHOW LOADING LAYOUT
        contentMain.setVisibility(View.GONE);
    }

    @Override
    public void onSourcesFetched(List<Source> sourceList) {
        sourcesAdapter.updateData(sourceList);
        sourceRecycler.smoothScrollToPosition(0);
        loadingLayout.setVisibility(View.GONE);
        contentMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
