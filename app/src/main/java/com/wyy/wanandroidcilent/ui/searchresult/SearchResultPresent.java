package com.wyy.wanandroidcilent.ui.searchresult;

import com.wyy.wanandroidcilent.enity.Article;

import java.util.List;

import io.reactivex.functions.Consumer;

public class SearchResultPresent implements ResultContract.ResultPresent{
    private ResultContract.ResultView mView;
    private String mSearchContent;
    private int mPage;
    private ResultModel model;

    public SearchResultPresent(){
        model = new ResultModel();
    }

    //搜索
    @Override
    public void search(String searchContentt) {
        mSearchContent = searchContentt;
        getSearchResult(mPage);
    }

    //加载底部数据
    @Override
    public void load() {
        mPage++;
        getSearchResult(mPage);
    }

    //获得搜索结果
    private void getSearchResult(int page){
        //获取列表数据
        model.getArticle(page,mSearchContent)
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article article) throws Exception {
                        List<Article.DataBean.DatasBean> articleList = article.getData().getDatas();
                        //更新ui界面
                        if (articleList.size() == 0){
                            mView.hideLoading();
                        }else {
                            mView.showArticleList(articleList);
                        };
                    }
                });
    }

    @Override
    public void bindView(ResultContract.ResultView view) {
        this.mView = view;
    }

    @Override
    public void unBind() {
        mView = null;
    }
}
