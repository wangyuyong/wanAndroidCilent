package com.wyy.wanandroidcilent.ui.searchresult;

import com.wyy.wanandroidcilent.base.BaseModel;
import com.wyy.wanandroidcilent.base.BasePresent;
import com.wyy.wanandroidcilent.base.BaseView;
import com.wyy.wanandroidcilent.enity.Article;

import java.util.List;

import io.reactivex.Observable;

public interface ResultContract {
    interface ResultView extends BaseView{

        //展示列表数据
        void showArticleList(List<Article.DataBean.DatasBean> articleList);

        //隐藏加载条
        void hideLoading();
    }

    interface ResultPresent extends BasePresent<ResultView>{

        //搜索内容
        void search(String searchContentt);

        //底部加载
        void load();
    }

    interface ResultModel extends BaseModel{

        //获得文章数据
        Observable<Article> getArticle(int page, String searchContent);
    }
}
