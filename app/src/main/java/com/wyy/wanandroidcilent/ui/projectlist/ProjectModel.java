package com.wyy.wanandroidcilent.ui.projectlist;

import com.wyy.wanandroidcilent.enity.Project;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectModel implements ProjectContract.ProjectModel {

    HttpService service;

    public ProjectModel(){
        RetrofitManager manager = RetrofitManager.getInstance();
        service = manager.getService();
    }

    @Override
    public Observable<Project> getProject(int page) {
        return service.getProject(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
