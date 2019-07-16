package com.wyy.wanandroidcilent.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.utils.StateUtil;

import java.util.List;

//文章列表的适配器
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //item为底部View
    public static final int BOTTOM_ITEM = 0;
    //item为正常View
    public static final int NORMAL_ITEM = 1;
    protected OnItemClickListener listener;
    protected List<Article.DataBean.DatasBean> mArticleList;
    //记录底部item
    LinearLayout bottomItem;

    //正常item的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout articleLl;
        TextView superChapterNameTv;
        TextView niceDateTv;
        TextView titleTv;
        TextView chapterNameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            articleLl = (LinearLayout) itemView;
            superChapterNameTv = itemView.findViewById(R.id.tv_article_author);
            niceDateTv = itemView.findViewById(R.id.tv_article_time);
            titleTv = itemView.findViewById(R.id.tv_article_title);
            chapterNameTv = itemView.findViewById(R.id.tv_article_chapter_name);
        }
    }

    //底部item的ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout itemBottomLl;
        public BottomViewHolder(View itemView) {
            super(itemView);
            itemBottomLl = itemView.findViewById(R.id.ll_item_bottom);
        }
    }

    public ArticleAdapter(List<Article.DataBean.DatasBean> mArticleList) {
        this.mArticleList = mArticleList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        if(viewType == NORMAL_ITEM){
            //如果是正常的item,加载对应的布局并添加监听
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_item_article,viewGroup,false);
            final ViewHolder holder = new ViewHolder(view);
            return holder;
        }else {
            //底部item加载相应的布局
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_item_bottom_view,viewGroup,false);
            BottomViewHolder holder = new BottomViewHolder(view);
            bottomItem = holder.itemBottomLl;
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        //ViewHolder的位置小于文章的数量，说明不是底部的ViewHolder
        if(position < mArticleList.size()){
            Article.DataBean.DatasBean article = mArticleList.get(position);
            final ViewHolder holder = (ViewHolder)viewHolder;
            holder.superChapterNameTv.setText(article.getAuthor());
            holder.niceDateTv.setText(article.getNiceDate());
            //添加文章作者，时间，文章名
            holder.titleTv.setText(article.getTitle());
            //若是已读，文章名显示为灰色
            if(article.isRead()){
                holder.titleTv.setTextColor(Color.parseColor("#8a000000"));
            }else{
                holder.titleTv.setTextColor(Color.parseColor("#000000"));
            }
            //添加文章的类型
            holder.chapterNameTv.setText(article.getChapterName() + "/" + article.getSuperChapterName());

            holder.articleLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StateUtil.isFastClicked()){
                        return;
                    }
                    holder.titleTv.setTextColor(Color.parseColor("#8a000000"));
                    if (listener != null){
                        listener.onClicked(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mArticleList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position < mArticleList.size()) {
            return NORMAL_ITEM;
        }else {
            return BOTTOM_ITEM;
        }
    }
    public void setBottomItemInVisible(){   //隐藏底部item
        bottomItem.setVisibility(View.INVISIBLE);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
