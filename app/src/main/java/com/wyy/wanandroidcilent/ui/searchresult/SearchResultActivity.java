package com.wyy.wanandroidcilent.ui.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wyy.wanandroidcilent.Const;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.ArticleAdapter;
import com.wyy.wanandroidcilent.adapter.OnItemClickListener;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.ui.articledetail.ArticleDetailActivity;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;
import com.wyy.wanandroidcilent.utils.StateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity implements ResultContract.ResultView{
    private SearchResultPresent present;
    private List<Article.DataBean.DatasBean> articles;
    private LinearLayoutManager manager;
    private ArticleAdapter adapter;
    private String searchText;

    @BindView(R.id.tb_search_result)
    Toolbar searchResultTb;
    @BindView(R.id.rv_search_result)
    RecyclerView searchResultRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        //present绑定view
        present = new SearchResultPresent();
        present.bindView(this);

        //初始化滑动组件
        initRecycleView();
        setSupportActionBar(searchResultTb);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //获得搜索内容
        Intent intent = getIntent();
        searchText = intent.getStringExtra("searchText");

        actionBar.setTitle(searchText);
        //请求网络搜索改内容
        present.search(searchText);
    }

    //显示文章列表
    @Override
    public void showArticleList(List<Article.DataBean.DatasBean> articlesList) {
        articles.addAll(articlesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    //隐藏底部加载条
    @Override
    public void hideLoading() {
        adapter.setBottomItemInVisible();
    }

    //初始化滑动组件
    public void initRecycleView(){
        articles = new ArrayList<>();
        searchResultRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new ArticleAdapter(articles);
        manager = new LinearLayoutManager(this);
        searchResultRv.setLayoutManager(manager);
        //为RecyclerView设置适配器和布局管理器
        searchResultRv.setAdapter(adapter);
        //注册点击事件
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onClicked(int position) {
                Article.DataBean.DatasBean article = articles.get(position);
                SharedPreferencesUtil.outputWithSharePreference(SearchResultActivity.this, Const.CONST_HAVE_READ_FILE,article.getTitle(),true);
                Intent intent = new Intent(SearchResultActivity.this, ArticleDetailActivity.class);
                intent.putExtra("link",article.getLink());
                intent.putExtra("title",article.getTitle());
                startActivity(intent);
            }
        });

        //添加滑动监听器
        searchResultRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //拉到底部加载数据
                if (manager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                    present.load();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.unBind();
    }
}
