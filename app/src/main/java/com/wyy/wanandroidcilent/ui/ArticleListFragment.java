package com.wyy.wanandroidcilent.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.ArticleAdapter;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import com.wyy.wanandroidcilent.utils.HttpUtil;
import com.wyy.wanandroidcilent.utils.ParaseUtil;
import java.util.ArrayList;
import java.util.List;

public class ArticleListFragment extends Fragment {
    Activity activity = getActivity();
    RecyclerView articleRv;
    ArticleAdapter adapter;
    LinearLayoutManager manager;
    List<Article> articles = new ArrayList<>();
    SwipeRefreshLayout refresh;

    int i;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list,container,false);

        refresh = (SwipeRefreshLayout)view.findViewById(R.id.srf_refresh);
        articleRv = (RecyclerView)view.findViewById(R.id.rv_article_list);                          //获取RecyclerView实例，添加适配器和布局管理器
        adapter = new ArticleAdapter(articles);
        manager = new LinearLayoutManager(container.getContext());
        articleRv.setLayoutManager(manager);
        articleRv.setAdapter(adapter);


        i = 0;
        articleRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {                     //为RecyclerView添加滑动监听器
                super.onScrolled(recyclerView,dx,dy);

                if(manager.findLastVisibleItemPosition() == adapter.getItemCount() - 1){            //滑到底部，换页
                        String adress = "https://www.wanandroid.com/article/list/" + i +"/json";
                        HttpUtil.sendHttpRequest(adress,new DataCallBack());
                }
            }
        });

        refresh.setColorSchemeResources(R.color.colorPrimary);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {                   //设置下拉刷新监听器
            @Override
            public void onRefresh() {
                i = 0;                                                                              //i重置为0
                articles.removeAll(articles);                                                       //清空articles
                String adress = "https://www.wanandroid.com/article/list/" + i +"/json";            //重新请求网络刷新数据
                HttpUtil.sendHttpRequest(adress,new RefreshCallBack());
                i++;

            }
        });
        return view;
    }

    private class DataCallBack implements HttpCallBack {
        @Override
        public void onFinish(String respone) {                                                      //将回调数据解析并显示到界面上
            ParaseUtil.paraseJSONToArticle(respone,articles);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();                                                 //通知适配器数据更改
                    i++;
                }
            });
        }
        @Override
        public void onError(Exception e) {
            e.printStackTrace();
        }
    }

    private class RefreshCallBack implements HttpCallBack {
        @Override
        public void onFinish(String respone) {                                                      //将回调数据解析并显示到界面上
            ParaseUtil.paraseJSONToArticle(respone,articles);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();                                                 //通知适配器数据更改
                    refresh.setRefreshing(false);                                                   //刷新结束
                    i++;
                }
            });
        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace();
        }
    }
}
