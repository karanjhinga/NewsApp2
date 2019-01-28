package com.karanjhinga.newsapp.newschannel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karanjhinga.newsapp.R;

import static android.view.View.GONE;

public class WebViewActivity extends AppCompatActivity {

    TextView urlTextView;
    ProgressBar loadingProgess;
    WebView webView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        setupToolbar(); //METHOD TO SETUP TOOLBAR

        webView = findViewById(R.id.webView);
        urlTextView = findViewById(R.id.urlToWeb);
        loadingProgess = findViewById(R.id.loadingProgess);
        url = getIntent().getStringExtra("url");
        urlTextView.setText(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingProgess.setVisibility(GONE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                loadingProgess.setProgress(newProgress);
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    private void setupToolbar() { //CREATING OUR CUSTOM TOOLBAR WITH CENTERED TEXT
        Toolbar toolbar = findViewById(R.id.webActivityToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        /* ENABLING BACK BUTTON IN TOOLBAR */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // CHANGING THE DEFAULT FUNCTIONALITY OF BACK BUTTON OF OUR TOOLBAR
            case android.R.id.home:
                super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
