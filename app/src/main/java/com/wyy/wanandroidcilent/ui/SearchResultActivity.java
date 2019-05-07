package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.ArticleAdapter;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import com.wyy.wanandroidcilent.utils.HttpUtil;
import com.wyy.wanandroidcilent.utils.ParaseUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends BaseActivity {
    RecyclerView searchResultRv;
    List<Article> articles;
    LinearLayoutManager manager;
    ArticleAdapter adapter;
    String searchText;
    int i;
    int w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        searchText = intent.getStringExtra("searchText");
        articles = new ArrayList<>();
        searchResultRv = (RecyclerView)findViewById(R.id.rv_search_result);
        adapter = new ArticleAdapter(articles);
        manager = new LinearLayoutManager(this);
        searchResultRv.setLayoutManager(manager);
        searchResultRv.setAdapter(adapter);
        i = 0;

        searchResultRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(manager.findLastVisibleItemPosition() == adapter.getItemCount() - 1){
                    String adress = "https://www.wanandroid.com/article/query/" + i + "/json";
                    HttpUtil.sendHttpRequestByPost(adress,"k=" + searchText,new DataCallBack());
                    i++;
                }
            }
        });
    }

    private class DataCallBack implements HttpCallBack {
        @Override
        public void onFinish(String respone) {          //将回调数据解析并显示到界面上
            if(ParaseUtil.paraseJSONToArticle(respone,articles)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SearchResultActivity.this,"没有更多数据了!",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace();
        }
    }
}
