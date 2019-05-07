package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;

public class ArticleDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        WebView articleWebView = (WebView)findViewById(R.id.wb_article_detail);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        WebSettings settings = articleWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        articleWebView.setWebViewClient(new WebViewClient());
        articleWebView.loadUrl(link);
    }
}
