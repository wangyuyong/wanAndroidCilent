package com.wyy.wanandroidcilent.ui.searchresult;

import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;
import com.wyy.wanandroidcilent.ui.homepage.HomePagePresent;

import java.util.List;

public class SearchResultPresent implements ResultContract.ResultPresent{
    private ResultContract.ResultView mView;
    private String mSearchContent;
    private int mPage;
    private HttpService mService;

    public SearchResultPresent(){
        RetrofitManager manager = RetrofitManager.getInstance();
        mService = manager.getService();
    }

    //搜索
    @Override
    public void search(String searchContentt) {
        mSearchContent = searchContentt;
        getSearchResult(mService,mPage);
    }

    //加载底部数据
    @Override
    public void load() {
        mPage++;
        getSearchResult(mService,mPage);
    }

    //获得搜索结果
    private void getSearchResult(HttpService service,int page){
        //获取列表数据
        HomePagePresent.operateArticle(service.getReacher(page, mSearchContent), new HomePagePresent.NotifyView() {
            @Override
            public void notify(Article article) {
                List<Article.DataBean.DatasBean> articleList = article.getData().getDatas();
                //更新ui界面
                if (articleList.size() == 0){
                    mView.hideLoading();
                }else {
                    mView.showArticleList(articleList);
                }
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
