package com.wyy.wanandroidcilent.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.widget.Banner;

import java.util.List;

//扩展ArticleAdapter,在其顶部添加Banner控件
public class ArticleBannerAdapter extends ArticleAdapter {
    public static final int TOP_ITEM = 2;      //顶部Item
    private Banner banner;

    //必须提供初始化好的Banner控件
    public ArticleBannerAdapter(List<Article> articles,Banner banner){
        super(articles);
        this.banner = banner;
    }

    //Banner控件的ViewHolder
    static class BannerViewHolder extends RecyclerView.ViewHolder{
        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //如果是顶部Item,则创建Banner的ViewHolder
        if(viewType == TOP_ITEM){
            BannerViewHolder viewHolder = new BannerViewHolder(banner);
            return viewHolder;
        }

        return super.onCreateViewHolder(viewGroup,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if( position <= mArticleList.size() && position > 0){
            Article article = mArticleList.get(position - 1);
            ViewHolder holder = (ViewHolder)viewHolder;
            holder.superChapterNameTextView.setText(article.getAuthor());
            holder.niceDateTextView.setText(article.getNiceDate());
            holder.titleTextView.setText(article.getTitle());                   //添加文章作者，时间，文章名
            if(article.isRead()){                                               //若是已读，文章名显示为灰色
                holder.titleTextView.setTextColor(Color.parseColor("#8a000000"));
            }else{
                holder.titleTextView.setTextColor(Color.parseColor("#000000"));
            }
            //添加文章的类型
            holder.chapterNameTextView.setText(article.getChapterName() + "/" + article.getSuperChapterName());
            holder.articleCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onClicked(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //增加了Banner,返回的Item数量要多加1
        return super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TOP_ITEM;
        }else if (position < super.getItemCount()){
            return NORMAL_ITEM;
        }else {
            return BOTTOM_ITEM;
        }
    }
}
