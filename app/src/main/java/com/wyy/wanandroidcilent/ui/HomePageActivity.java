package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.ArticleAdapter;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import com.wyy.wanandroidcilent.utils.HttpUtil;
import com.wyy.wanandroidcilent.utils.ParaseUtil;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.*;


//app主页，完成登录后进入此界面
public class HomePageActivity extends AppCompatActivity {

    RecyclerView articleRv;
    ArticleAdapter adapter;
    LinearLayoutManager manager;
    List<Article> articles = new ArrayList<>();
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        articleRv = (RecyclerView)findViewById(R.id.rv_article);
        adapter = new ArticleAdapter(articles);
        manager = new LinearLayoutManager(HomePageActivity.this);
        articleRv.setLayoutManager(manager);
        articleRv.setAdapter(adapter);
        i = 0;

        articleRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx,dy);

                if(manager.findLastVisibleItemPosition() == adapter.getItemCount() - 1){
                    String adress = "https://www.wanandroid.com/article/list/" + i +"/json";
                    HttpUtil.sendHttpRequest(adress,new DataCallBack());
                    i++;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    private class DataCallBack implements HttpCallBack{
        @Override
        public void onFinish(String respone) {          //将回调数据解析并显示到界面上
            ParaseUtil.paraseJSONToArticle(respone,articles);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace();
        }
    }
}
