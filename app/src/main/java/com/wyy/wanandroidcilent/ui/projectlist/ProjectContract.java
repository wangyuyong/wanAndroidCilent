package com.wyy.wanandroidcilent.ui.projectlist;

import com.wyy.wanandroidcilent.base.BasePresent;
import com.wyy.wanandroidcilent.base.BaseView;
import com.wyy.wanandroidcilent.enity.Project;

import java.util.List;

public interface ProjectContract {

    interface ProjectView extends BaseView{
        //展示Project列表
        void showProject(List<Project.DataBean.DatasBean> project);
    }

    interface ProjectPresent extends BasePresent<ProjectView>{

        //加载数据
        void load();

        //底部加载数据
        void bottomLoad();
    }
}
