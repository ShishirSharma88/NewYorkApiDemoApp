package com.my.shishir.demoapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.my.shishir.demoapp.utility.Utility;

import java.util.Objects;

public class WebActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webview_news);
        initWebView();
    }

    @SuppressLint("NewApi")
    private String getUrl() {
        return (String) Objects.requireNonNull(getIntent().getExtras()).get(Utility.KEY_URL);
    }

    private void initWebView() {
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getUrl());
        webView.setHorizontalScrollBarEnabled(false);
    }
}
