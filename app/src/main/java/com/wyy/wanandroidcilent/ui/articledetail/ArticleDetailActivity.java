package com.wyy.wanandroidcilent.ui.articledetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends BaseActivity {
    @BindView(R.id.tb_article_detail)
    Toolbar toolbar;
    @BindView(R.id.wb_article_detail)
    WebView articleWb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //对WebView进行配置
        initWebView();
        Intent intent = getIntent();
        //得到上一活动传送的数据
        String link = intent.getStringExtra("link");
        //得到文章的标题
        String title = intent.getStringExtra("title");
        actionBar.setTitle(title);
        articleWb.setWebViewClient(new WebViewClient());
        articleWb.loadUrl(link);
    }

    //初始化webView
    private void initWebView() {
        WebSettings settings = articleWb.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置缓存模式
        if (StateUtil.isNetworkConnected(this)) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        //设置缓存
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //点击按钮过快
        if (StateUtil.isFastClicked()) {
            return false;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
