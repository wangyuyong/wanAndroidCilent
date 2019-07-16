package com.wyy.wanandroidcilent.ui.searchresult;

import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class ResultModel implements ResultContract.ResultModel {

    HttpService service;

    public ResultModel(){
        RetrofitManager manager = RetrofitManager.getInstance();
        service = manager.getService();
    }

    @Override
    public Observable<Article> getArticle(int page, String searchContent) {
        return service.getReacher(page,searchContent)
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
}
