package com.wyy.wanandroidcilent.ui.projectlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.OnItemClickListener;
import com.wyy.wanandroidcilent.adapter.ProjectAdapter;
import com.wyy.wanandroidcilent.app.MyApplication;
import com.wyy.wanandroidcilent.enity.Project;
import com.wyy.wanandroidcilent.ui.articledetail.ArticleDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProjectFragment extends Fragment implements ProjectContract.ProjectView {


    @BindView(R.id.rv_project_list)
    RecyclerView projectRv;
    private ProjectAdapter projectAdapter;
    private List<Project.DataBean.DatasBean> mProjectList;
    private Unbinder unbinder;
    private ProjectContract.ProjectPresent present;
    private LinearLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        unbinder = ButterKnife.bind(this, view);

        initRecyclerView();
        present = new ProjectPresent();
        present.bindView(this);

        present.load();
        return view;
    }

    //展示项目列表
    @Override
    public void showProject(List<Project.DataBean.DatasBean> project) {
        mProjectList.addAll(project);
        projectAdapter.notifyDataSetChanged();
    }

    //初始化滑动组件
    public void initRecyclerView(){
        mProjectList = new ArrayList<>();
        projectAdapter = new ProjectAdapter(mProjectList);
        projectRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        manager = new LinearLayoutManager(MyApplication.getContext());
        projectRv.setLayoutManager(manager);
        projectRv.setAdapter(projectAdapter);
        projectAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onClicked(int position) {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                intent.putExtra("link",mProjectList.get(position).getLink());
                intent.putExtra("title",mProjectList.get(position).getTitle());
                startActivity(intent);
            }
        });

        projectRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (manager.findLastVisibleItemPosition() == projectAdapter.getItemCount() - 1){
                    present.bottomLoad();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        present.unBind();
    }
}
