package com.cli.login;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class webView extends AppCompatActivity {

    String url;

    private LinearProgressIndicator linearProgressIndicator;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        linearProgressIndicator = findViewById(R.id.newpageloader);

        url = getIntent().getStringExtra("url");
        WebView webView = findViewById(R.id.webview);

        webView.loadUrl(url);
        linearProgressIndicator.setProgress(100,true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);



    }
}