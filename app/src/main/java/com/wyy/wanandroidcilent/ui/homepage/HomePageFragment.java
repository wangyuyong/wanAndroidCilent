package com.wyy.wanandroidcilent.ui.homepage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.ArticleBannerAdapter;
import com.wyy.wanandroidcilent.adapter.OnItemClickListener;
import com.wyy.wanandroidcilent.app.MyApplication;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.ui.articledetail.ArticleDetailActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomePageFragment extends Fragment implements HomePageContract.HomePageView{

    private HomePageContract.HomePagePresent present;
    private List<Article.DataBean.DatasBean> articleList = new ArrayList<>();
    private ArticleBannerAdapter adapter;
    private LinearLayoutManager manager;

    @BindView(R.id.rv_article_list)
    RecyclerView articleListRv;
    @BindView(R.id.srf_refresh)
    SwipeRefreshLayout articleListSrf;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        unbinder = ButterKnife.bind(this, view);

        //present绑定view
        present = new HomePagePresent();
        present.bindView(this);

        //初始化滑动组件
        initRecyclerView();

        //初始化首页数据
        present.initData();

        articleListSrf.setColorSchemeResources(R.color.colorPrimary);
        //设置下拉刷新监听器
        articleListSrf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (StateUtil.isFastRefresh()) {
                    articleListSrf.setRefreshing(false);
                    return;
                }
                //更新数据
                present.upData();
                articleListSrf.setRefreshing(false);
            }
        });
        return view;
    }

    //展示文章列表
    @Override
    public void showArticleList(List<Article.DataBean.DatasBean> articleList) {
        this.articleList.addAll(articleList);
        adapter.notifyDataSetChanged();
    }

    //更新文章列表
    @Override
    public void updataArticleList(List<Article.DataBean.DatasBean> articleList) {
        this.articleList.clear();
        this.articleList.addAll(articleList);
        adapter.notifyDataSetChanged();
    }

    //展示轮播图
    @Override
    public void showBanner(List<BannerData.DataBean> dataBeanList) {
        adapter.setImage(dataBeanList,getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //解除present的绑定
        present.unBind();
    }

    @Override
    public void hideLoading() {
        adapter.setBottomItemInVisible();
    }


    //初始化滑动组件
    public void initRecyclerView(){
        articleListRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new ArticleBannerAdapter(articleList);
        manager = new LinearLayoutManager(MyApplication.getContext());
        articleListRv.setLayoutManager(manager);
        articleListRv.setAdapter(adapter);       //获取RecyclerView实例，添加适配器和布局管理器
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onClicked(int position) {
                Intent intent = new Intent(getActivity(),ArticleDetailActivity.class);
                intent.putExtra("link",articleList.get(position - 1).getLink());
                intent.putExtra("title",articleList.get(position - 1).getTitle());
                startActivity(intent);
            }
        });          //设置监听

        articleListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            //为RecyclerView添加滑动监听器
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (manager.findLastVisibleItemPosition() == adapter.getItemCount() - 1){
                    present.bottomDownLoadData();
                }
            }
        });
    }
}
