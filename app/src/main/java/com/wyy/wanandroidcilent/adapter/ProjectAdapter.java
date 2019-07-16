package com.wyy.wanandroidcilent.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.enity.Project;
import com.wyy.wanandroidcilent.utils.StateUtil;

import java.util.List;

//项目列表的适配器
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    //项目列表的数据源
    private List<Project.DataBean.DatasBean> mprojectList;
    //列表每个元素的监听器
    private OnItemClickListener listener;

    public ProjectAdapter(List<Project.DataBean.DatasBean> projectList) {
        this.mprojectList = projectList;
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTv;
        TextView contentTv;
        RelativeLayout projectRl;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_project_picture);
            titleTv = itemView.findViewById(R.id.tv_project_title);
            contentTv = itemView.findViewById(R.id.tv_content);
            projectRl = itemView.findViewById(R.id.rl_project);
        }
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_item_project,viewGroup,false);

        ProjectViewHolder holder = new ProjectViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder projectViewHolder, final int i) {
        String imagPath = mprojectList.get(i).getEnvelopePic();
        String projectTitle = mprojectList.get(i).getTitle();
        String projectContent = mprojectList.get(i).getAuthor() + "/" + mprojectList.get(i).getAuthor();
        //设置项目标题
        projectViewHolder.titleTv.setText(projectTitle);
        //设置项目内容
        projectViewHolder.contentTv.setText(projectContent);
        //设置点击事件
        projectViewHolder.projectRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StateUtil.isFastClicked()){
                    return;
                }
                if (listener != null){
                    listener.onClicked(i);
                }
            }
        });
        //加载项目图片
        Glide.with(projectViewHolder.imageView).load(imagPath).into(projectViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mprojectList.size();
    }

    public void setClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
