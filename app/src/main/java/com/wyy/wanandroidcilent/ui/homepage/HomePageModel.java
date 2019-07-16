package com.wyy.wanandroidcilent.ui.homepage;

import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomePageModel implements HomePageContract.HomePageModel {

    HttpService service;

    public HomePageModel(){
        RetrofitManager manager = RetrofitManager.getInstance();
        service = manager.getService();
    }

    @Override
    public Observable<Article> getArticleData(int page) {
        return service.getArticleData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) throws Exception {
                        Article.DataBean.DatasBean.setYouHaveRead(article);
                        return article;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BannerData> getBannerData() {
        return service.getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
