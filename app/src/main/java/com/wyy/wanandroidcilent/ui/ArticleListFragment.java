package com.wyy.wanandroidcilent.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.ArticleAdapter;
import com.wyy.wanandroidcilent.adapter.ArticleBannerAdapter;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import com.wyy.wanandroidcilent.utils.HttpUtil;
import com.wyy.wanandroidcilent.utils.ParaseUtil;
import com.wyy.wanandroidcilent.widget.Banner;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ArticleListFragment extends Fragment {
    RecyclerView articleRv;
    ArticleBannerAdapter adapter;
    LinearLayoutManager manager;
    List<Article> articles = new ArrayList<>();         //数据源
    SwipeRefreshLayout refresh;
    Banner banner;
    List<BannerData> bannerData;
    List<Bitmap> bitmaps;

    int i;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list,container,false);

        banner = new Banner(getActivity());
        bannerData = new ArrayList<>();
        bitmaps = new ArrayList<>();
        initBanner();

        refresh = (SwipeRefreshLayout)view.findViewById(R.id.srf_refresh);
        articleRv = (RecyclerView)view.findViewById(R.id.rv_article_list);
        adapter = new ArticleBannerAdapter(articles,banner);
        manager = new LinearLayoutManager(container.getContext());
        articleRv.setLayoutManager(manager);
        articleRv.setAdapter(adapter);       //获取RecyclerView实例，添加适配器和布局管理器

        i = 0;
        articleRv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            //为RecyclerView添加滑动监听器
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx,dy);

                //滑到底部，换页
                if(manager.findLastVisibleItemPosition() == adapter.getItemCount() - 1){
                        String adress = "https://www.wanandroid.com/article/list/" + i +"/json";
                        HttpUtil.sendHttpRequest(adress,new DataCallBack());
                }
            }
        });

        refresh.setColorSchemeResources(R.color.colorPrimary);
        //设置下拉刷新监听器
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                i = 0;                                   //i重置为0
                articles.removeAll(articles);           //清空articles
                //重新请求网络刷新数据
                String adress = "https://www.wanandroid.com/article/list/" + i +"/json";
                HttpUtil.sendHttpRequest(adress,new RefreshCallBack());
                i++;

            }
        });
        return view;
    }

    private class DataCallBack implements HttpCallBack {
        @Override
        public void onFinish(String respone) {                            //将回调数据解析并显示到界面上
            ParaseUtil.paraseJSONToArticle(respone,articles);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();                     //通知适配器数据更改
                    i++;
                }
            });
        }
        @Override
        public void onError(Exception e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //通知用户网络连接超时
                    Toast.makeText(getActivity(),HttpUtil.NO_INTERNET,Toast.LENGTH_LONG).show();
                }
            });
            e.printStackTrace();
        }
    }

    private class RefreshCallBack implements HttpCallBack {
        @Override
        public void onFinish(String respone) {                          //将回调数据解析并显示到界面上
            ParaseUtil.paraseJSONToArticle(respone,articles);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();                     //通知适配器数据更改
                    refresh.setRefreshing(false);                      //刷新结束
                    i++;
                }
            });
        }

        @Override
        public void onError(Exception e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //通知用户网络连接超时
                    Toast.makeText(getActivity(),HttpUtil.NO_INTERNET,Toast.LENGTH_LONG).show();
                    refresh.setRefreshing(false);                           //刷新结束
                }
            });
            e.printStackTrace();
        }
    }

   private void initBanner(){
        HttpUtil.sendHttpRequest(HttpUtil.WAN_ANDROID_BANNER_ADRESS, new HttpCallBack() {
            @Override
            public void onFinish(String respone) {
                ParaseUtil.paraseJSONToBanner(respone,bannerData);
                loadPictureFromNet(bannerData,bitmaps);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),HttpUtil.NO_INTERNET,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void loadPictureFromNet(final List<BannerData> bannerData, final List<Bitmap> bitmaps){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection connection = null;
                try {
                    for(int i = 0; i < bannerData.size(); i++) {                                        //将banners转为为对应的图片
                        url = new URL(bannerData.get(i).getImagePath());
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        Log.d("bitmap",bitmap.toString());
                        bitmaps.add(bitmap);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            banner.initViews(getActivity(),bitmaps);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
