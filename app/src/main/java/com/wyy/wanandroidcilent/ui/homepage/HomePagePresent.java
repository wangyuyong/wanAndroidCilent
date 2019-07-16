package com.wyy.wanandroidcilent.ui.homepage;

import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HomePagePresent implements HomePageContract.HomePagePresent {
    private HomePageContract.HomePageView mView;
    private HomePageModel model;
    private int page;

    //通知ui界面数据更新
    public interface NotifyView {
        void notify(Article article);
    }

    //初始化present类
    public HomePagePresent(){
        model = new HomePageModel();
    }

    //初始化数据
    @Override
    public void initData() {

        //获得文章数据
        model.getArticleData(page)
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article article) throws Exception {
                        mView.showArticleList(article.getData().getDatas());
                    }
                });

        //获得banner数据
        model.getBannerData()
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
        model.getArticleData(page)
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article article) throws Exception {
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
        model.getArticleData(page)
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article article) throws Exception {
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
}
