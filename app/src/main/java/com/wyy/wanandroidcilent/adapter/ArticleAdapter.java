package com.wyy.wanandroidcilent.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.ui.ArticleDetailActivity;

import java.util.ArrayList;
import java.util.List;

//首页文章的适配器
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BOTTOM_ITEM = 0;               //item为底部View
    private static final int NORMAL_ITEM = 1;               //item为正常View
    private List<Article> mArticleList;

    public static class ViewHolder extends RecyclerView.ViewHolder{            //正常item的ViewHolder
        CardView articleCardView;
        TextView superChapterNameTextView;
        TextView niceDateTextView;
        TextView titleTextView;
        TextView chapterNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            articleCardView = (CardView)itemView;
            superChapterNameTextView = (TextView)itemView.findViewById(R.id.tv_article_author);
            niceDateTextView = (TextView)itemView.findViewById(R.id.tv_article_time);
            titleTextView = (TextView)itemView.findViewById(R.id.tv_article_title);
            chapterNameTextView = (TextView)itemView.findViewById(R.id.tv_article_chapter_name);
        }
    }

    public static class BottomViewHolder extends RecyclerView.ViewHolder{              //底部item的ViewHolder
        LinearLayout linearLayoutItemBottom;
        public BottomViewHolder(View itemView) {
            super(itemView);
            linearLayoutItemBottom = (LinearLayout)itemView.findViewById(R.id.linear_item_bottom);
        }
    }

    public ArticleAdapter(List<Article> mArticleList) {
        this.mArticleList = mArticleList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        if(viewType == NORMAL_ITEM){                //如果是正常的item,加载对应的布局并添加监听
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_article,viewGroup,false);
            final ViewHolder holder = new ViewHolder(view);
            holder.articleCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(viewGroup.getContext(), ArticleDetailActivity.class);
                    Article article = mArticleList.get(holder.getAdapterPosition());
                    intent.putExtra("link",article.getLink());              //向下一活动传输link
                    viewGroup.getContext().startActivity(intent);
                }
            });
            return holder;
        }else {                 //底部item加载相应的布局
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_item_bottom_view,viewGroup,false);
            BottomViewHolder holder = new BottomViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(position < mArticleList.size()){
            Article article = mArticleList.get(position);
            ViewHolder holder = (ViewHolder)viewHolder;
            holder.superChapterNameTextView.setText(article.getAuthor());
            holder.niceDateTextView.setText(article.getNiceDate());
            holder.titleTextView.setText(article.getTitle());
            holder.chapterNameTextView.setText(article.getChapterName() + "/" + article.getSuperChapterName());
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

}
