package com.wyy.wanandroidcilent.ui.homepage;

import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomePagePresent implements HomePageContract.HomePagePresent {
    private HomePageContract.HomePageView mView;
    private HttpService service;
    private int page;

    //通知ui界面数据更新
    public interface NotifyView {
        void notify(Article article);
    }

    //初始化present类
    public HomePagePresent(){
        RetrofitManager manager = RetrofitManager.getInstance();
        service = manager.getService();
    }

    //初始化数据
    @Override
    public void initData() {
        //获得文章数据
        getArticleList(service, page, new NotifyView() {
            @Override
            public void notify(Article article) {
                //ui界面展示数据
                mView.showArticleList(article.getData().getDatas());
            }
        });

        //获得banner数据
        service.getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BannerData>() {
                    @Override
                    public void accept(BannerData bannerData) throws Exception {
                        //ui界面展示banner
                        mView.showBanner(bannerData.getData());
                    }
                });
    }

    //底部加载数据
    @Override
    public void bottomDownLoadData() {
        page++;
        //获得文章数据
        getArticleList(service, page, new NotifyView() {
            @Override
            public void notify(Article article) {
                List<Article.DataBean.DatasBean> datasBeanList = article.getData().getDatas();
                //获取到的数据量为0
                if (datasBeanList.size() == 0){
                    //隐藏底部加载条
                    mView.hideLoading();
                }else {
                    //展示文章数据
                    mView.showArticleList(datasBeanList);
                }
            }
        });
    }

    //更新数据
    @Override
    public void upData() {
        //页数置为0
        page = 0;
        //获取文章数据
        getArticleList(service, page, new NotifyView() {
            @Override
            public void notify(Article article) {
                //ui界面更新数据
                mView.updataArticleList(article.getData().getDatas());
            }
        });
    }

    @Override
    public void bindView(HomePageContract.HomePageView view) {
        this.mView = view;
    }

    @Override
    public void unBind() {
        mView = null;
    }

    //获得文章列表数据
    private void getArticleList(HttpService mService,int mPage,NotifyView changeView){
        if (changeView != null){
            operateArticle(mService.getArticleData(mPage),changeView);
        }
    }

    /**
     * 获取被观察者，对被观察者进行订阅,上游向下游发送数据，下游会更新ui界面
     * @param observable 被观察者
     * @param changeView ui操作
     */
    public static void operateArticle(Observable<Article> observable,final NotifyView changeView){
            observable.subscribeOn(Schedulers.io())
                //获得的数据进行重新装配，添加isRead字段
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) throws Exception {
                        Article.DataBean.DatasBean.setYouHaveRead(article);
                        return article;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article article) throws Exception {
                        changeView.notify(article);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
