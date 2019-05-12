package com.wyy.wanandroidcilent.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.ui.ArticleDetailActivity;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;
import com.wyy.wanandroidcilent.widget.Banner;

import java.util.List;

public class ArticleBannerAdapter extends ArticleAdapter {
    public static final int TOP_ITEM = 2;      //顶部Holder
    private Banner banner;

    public ArticleBannerAdapter(List<Article> articles,Banner banner){
        super(articles);
        this.banner = banner;
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder{
        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == TOP_ITEM){
            BannerViewHolder viewHolder = new BannerViewHolder(banner);
            return viewHolder;
        }

        return super.onCreateViewHolder(viewGroup,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if( position <= mArticleList.size() && position > 0){
            Article article = mArticleList.get(position - 1);
            ViewHolder holder = (ViewHolder)viewHolder;
            holder.superChapterNameTextView.setText(article.getAuthor());
            holder.niceDateTextView.setText(article.getNiceDate());
            holder.titleTextView.setText(article.getTitle());                   //添加文章作者，时间，文章名
            if(article.isRead()){                                               //若是已读，文章名显示为灰色
                holder.titleTextView.setTextColor(Color.parseColor("#CCCCCC"));
            }else{
                holder.titleTextView.setTextColor(Color.parseColor("#000000"));
            }
            //添加文章的类型
            holder.chapterNameTextView.setText(article.getChapterName() + "/" + article.getSuperChapterName());
        }
    }

    @Override
    public int getItemCount() {
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
