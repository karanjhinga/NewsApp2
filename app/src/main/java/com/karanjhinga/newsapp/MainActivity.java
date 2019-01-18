package com.karanjhinga.newsapp;

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

import com.karanjhinga.newsapp.Adapters.CategoryAdapter;
import com.karanjhinga.newsapp.Adapters.SourcesAdapter;
import com.karanjhinga.newsapp.Interfaces.CategoryListener;
import com.karanjhinga.newsapp.Interfaces.SourceSelectedListener;
import com.karanjhinga.newsapp.Models.Source;
import com.karanjhinga.newsapp.Models.SourceList;
import com.karanjhinga.newsapp.Others.ApiClient;
import com.karanjhinga.newsapp.Others.Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CategoryListener, SourceSelectedListener {

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
        ApiClient.getInstance().getApi().getAllSources(ApiClient.api_key).enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(@NonNull Call<SourceList> call, @NonNull Response<SourceList> response) {

                if (!response.isSuccessful()){  //CHECK IF RESPONSE IS SUCCESSFUL
                    Toast.makeText(MainActivity.this, "Error: "+response.code() , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.body().status.equals("ok")) {      //LOAD DATA INTO LIST IF SUCCESSFUL
                    sourceList = response.body().sources;
                    sourcesAdapter.updateData(sourceList);
                    sourceRecycler.smoothScrollToPosition(0);
                    loadingLayout.setVisibility(View.GONE);
                    contentMain.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SourceList> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //FUNCTIONALITY OF LOADING SOURCES BY CATEGORY
    private void loadData(String category) {
        internetAndLoading();
        ApiClient.getInstance().getApi().getSourcesByCategory(category,ApiClient.api_key).enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(@NonNull Call<SourceList> call, @NonNull Response<SourceList> response) {

                if (!response.isSuccessful()){      //CHECK IF RESPONSE IS SUCCESSFUL
                    Toast.makeText(MainActivity.this, "Error: "+response.code() , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.body().status.equals("ok")) { //LOAD DATA INTO LIST IF SUCCESSFUL
                    sourceList = response.body().sources;
                    sourcesAdapter.updateData(sourceList);
                    sourceRecycler.smoothScrollToPosition(0);
                    loadingLayout.setVisibility(View.GONE);
                    contentMain.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SourceList> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
}
