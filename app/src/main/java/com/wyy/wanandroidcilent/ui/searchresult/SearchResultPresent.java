package com.wyy.wanandroidcilent.ui.searchresult;

import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        service.getReacher(page, mSearchContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article article) throws Exception {
                        List<Article.DataBean.DatasBean> datasBeanList = article.getData().getDatas();
                        if (datasBeanList.size() == 0){
                            mView.hideLoading();
                        }else {
                            mView.showArticleList(datasBeanList);
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
