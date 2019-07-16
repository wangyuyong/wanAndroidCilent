package com.wyy.wanandroidcilent.ui.projectlist;

import com.wyy.wanandroidcilent.enity.Project;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProjectPresent implements ProjectContract.ProjectPresent {

    ProjectContract.ProjectView view;
    int page;

    //加载项目列表
    @Override
    public void load() {
        getProjectList(page);
    }

    //底部加载
    @Override
    public void bottomLoad() {
        page++;
        getProjectList(page);
    }

    //获得项目列表数据
    private void getProjectList(int mPage){
        RetrofitManager manager = RetrofitManager.getInstance();
        HttpService service = manager.getService();
        service.getProject(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Project>() {
                    @Override
                    public void accept(Project project) throws Exception {
                        view.showProject(project.getData().getDatas());
                    }
                });
    }

    @Override
    public void bindView(ProjectContract.ProjectView view) {
        this.view = view;
    }

    @Override
    public void unBind() {
        view = null;
    }
}
