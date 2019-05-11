package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;

public class ArticleDetailActivity extends BaseActivity {
    WebView articleWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        articleWebView = (WebView)findViewById(R.id.wb_article_detail);
        initWebView();                      //对WebView进行配置
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");        //得到上一活动传送的数据
        articleWebView.setWebViewClient(new WebViewClient());
        articleWebView.loadUrl(link);
    }

    private void initWebView(){
        WebSettings settings = articleWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        //设置缓存模式
        if(StateUtil.isNetworkConnected(this)){
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);                  //设置缓存
    }
}
