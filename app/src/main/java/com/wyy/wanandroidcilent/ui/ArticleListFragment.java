package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.ArticleBannerAdapter;
import com.wyy.wanandroidcilent.adapter.OnItemClickListener;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import com.wyy.wanandroidcilent.utils.HttpUtil;
import com.wyy.wanandroidcilent.utils.ParaseUtil;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;
import com.wyy.wanandroidcilent.widget.Banner;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ArticleListFragment extends Fragment implements OnItemClickListener {
    RecyclerView articleRv;
    ArticleBannerAdapter adapter;
    LinearLayoutManager manager;
    List<Article> articles = new ArrayList<>();         //数据源
    SwipeRefreshLayout refresh;
    Banner banner;
    List<BannerData> bannerData;
    List<Bitmap> bitmaps;

    int i;              //页数
    int w;              //下拉刷新时，i重置为0，记录i重置前的数据

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list,container,false);

        initBanner();   //初始化Banner

        refresh = (SwipeRefreshLayout)view.findViewById(R.id.srf_refresh);
        articleRv = (RecyclerView)view.findViewById(R.id.rv_article_list);
        articleRv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        adapter = new ArticleBannerAdapter(articles,banner);
        manager = new LinearLayoutManager(container.getContext());
        articleRv.setLayoutManager(manager);
        articleRv.setAdapter(adapter);       //获取RecyclerView实例，添加适配器和布局管理器
        adapter.setListener(this);          //设置监听

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
                w = i;                                    //记录i的数据，网络访问失败时，返回i的数据
                i = 0;                                   //i重置为0
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
        public void onFinish(String respone) {
            articles.removeAll(articles);                               //清空articles
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
                    i = w;                  //返回i的数据
                }
            });
            e.printStackTrace();
        }
    }

    //初始化Banner
   private void initBanner(){
       banner = new Banner(getActivity());  //创建Banner
       bannerData = new ArrayList<>();
       bitmaps = new ArrayList<>();
        //发送网络请求
        HttpUtil.sendHttpRequest(HttpUtil.WAN_ANDROID_BANNER_ADRESS, new HttpCallBack() {
            @Override
            public void onFinish(String respone) {
                //解析数据
                ParaseUtil.paraseJSONToBanner(respone,bannerData);
                //将得到数据中的图片地址加载为Bitmap对象
                loadPictureFromNet(bannerData,bitmaps);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //提示用户网络连接超时
                        Toast.makeText(getActivity(),HttpUtil.NO_INTERNET,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    //将bannerData数组中的图片网络地址加载为Bitmap数组
    private void loadPictureFromNet(final List<BannerData> bannerData, final List<Bitmap> bitmaps){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection connection = null;
                try {
                    for(int i = 0; i < bannerData.size(); i++) {         //将图片的网络地址转化为对应的Bitmap对象
                        url = new URL(bannerData.get(i).getImagePath());
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        bitmaps.add(bitmap);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            banner.initViews(getActivity(),bitmaps,bannerData);    //初始化Banner中view数组
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

    @Override
    public void onClicked(int position) {
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        Article article = articles.get(position - 1);
        if (!article.isRead()){
            article.setRead(true);                                          //将文章设置为已读
            adapter.notifyDataSetChanged();                                 //告知适配器数据发生变化
            SharedPreferencesUtil.outputWithSharePreference                 //将已读文章的title以键值对(title-true)形式存入"have_read"
                    (getActivity(), SharedPreferencesUtil.HAVE_READ_FILE,article.getTitle(),true);
        }
        intent.putExtra("link",article.getLink());              //向下一活动传输link
        getActivity().startActivity(intent);
    }
}
