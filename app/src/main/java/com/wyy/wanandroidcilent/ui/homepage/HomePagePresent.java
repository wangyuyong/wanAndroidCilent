package com.wyy.wanandroidcilent.ui.homepage;

import com.wyy.wanandroidcilent.app.MyApplication;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePagePresent implements HomePageContract.HomePagePresent {
    private HomePageContract.HomePageView mView;
    private HttpService service;
    private int page;

    //通知ui界面数据更新
    private interface NotifyView {
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
        getArticleList(service, page, new NotifyView() {
            @Override
            public void notify(Article article) {
                mView.showArticleList(article.getData().getDatas());
            }
        });

        service.getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BannerData>() {
                    @Override
                    public void accept(BannerData bannerData) throws Exception {
                        mView.showBanner(bannerData.getData());
                    }
                });
    }

    //底部加载数据
    @Override
    public void bottomDownLoadData() {
        page++;
        getArticleList(service, page, new NotifyView() {
            @Override
            public void notify(Article article) {
                List<Article.DataBean.DatasBean> datasBeanList = article.getData().getDatas();
                if (datasBeanList.size() == 0){
                    mView.hideLoading();
                }else {
                    mView.showArticleList(datasBeanList);
                }
            }
        });
    }

    //更新数据
    @Override
    public void upData() {
        page = 0;
        getArticleList(service, page, new NotifyView() {
            @Override
            public void notify(Article article) {
                mView.updataArticleList(article.getData().getDatas());
            }
        });
    }

    //保存文章阅读记录
    @Override
    public void savieAticleHistory(String title, boolean isRead) {
        SharedPreferencesUtil.outputWithSharePreference(MyApplication.getContext(),SharedPreferencesUtil.CONST_HAVE_READ_FILE,title,isRead);
    }

    @Override
    public void bindView(HomePageContract.HomePageView view) {
        this.mView = view;
    }

    @Override
    public void unBind() {
        mView = null;
    }

    //用于发送http请求获得数据并通知ui
    private void getArticleList(HttpService mService, int mPage, final NotifyView changeView){
        mService.getArticleData(mPage)
                .subscribeOn(Schedulers.io())
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
