package com.wyy.wanandroidcilent.ui.projectlist;

import com.wyy.wanandroidcilent.enity.Project;

import io.reactivex.functions.Consumer;


public class ProjectPresent implements ProjectContract.ProjectPresent {

    ProjectContract.ProjectView view;
    ProjectModel model;
    int page;

    public ProjectPresent(){
        model = new ProjectModel();
    }

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
        model.getProject(mPage)
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
