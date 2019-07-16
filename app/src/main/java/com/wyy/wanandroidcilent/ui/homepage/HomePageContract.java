package com.wyy.wanandroidcilent.ui.homepage;

import com.wyy.wanandroidcilent.base.BasePresent;
import com.wyy.wanandroidcilent.base.BaseView;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;

import java.util.List;

public interface HomePageContract {
    interface HomePageView extends BaseView{

        //显示文章列表
        void showArticleList(List<Article.DataBean.DatasBean> articleList);

        //显示轮播图
        void showBanner(List<BannerData.DataBean> dataBeanList);

        //更新文章列表
        void updataArticleList(List<Article.DataBean.DatasBean> articleList);

        //隐藏加载条
        void hideLoading();

    }

    interface HomePagePresent extends BasePresent<HomePageView>{

        //初始化数据
        void initData();

        //底部加载数据
        void bottomDownLoadData();

        //下滑更新数据
        void upData();
    }
}
