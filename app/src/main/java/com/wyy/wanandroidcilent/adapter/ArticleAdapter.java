package com.wyy.wanandroidcilent.adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

//文章列表的适配器
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int BOTTOM_ITEM = 0;               //item为底部View
    public static final int NORMAL_ITEM = 1;               //item为正常View
    protected List<Article> mArticleList;
    LinearLayout bottomItem;                                //记录底部item

    //正常item的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{
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

    //底部item的ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder{
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
        if(viewType == NORMAL_ITEM){
            //如果是正常的item,加载对应的布局并添加监听
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_article,viewGroup,false);
            final ViewHolder holder = new ViewHolder(view);
            holder.articleCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(viewGroup.getContext(), ArticleDetailActivity.class);
                    Article article = mArticleList.get(holder.getAdapterPosition());
                    article.setRead(true);                                          //将文章设置为已读
                    notifyDataSetChanged();                                         //告知适配器数据发生变化
                    SharedPreferencesUtil.outputWithSharePreference                 //将已读文章的title以键值对(title-true)形式存入"have_read"
                            (viewGroup.getContext(),SharedPreferencesUtil.HAVE_READ_FILE,article.getTitle(),true);
                    intent.putExtra("link",article.getLink());              //向下一活动传输link
                    viewGroup.getContext().startActivity(intent);
                }
            });
            return holder;
        }else {
            //底部item加载相应的布局
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_item_bottom_view,viewGroup,false);
            BottomViewHolder holder = new BottomViewHolder(view);
            bottomItem = holder.linearLayoutItemBottom;
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(position < mArticleList.size()){                                      //ViewHolder的位置小于文章的数量，说明不是底部的ViewHolder
            Article article = mArticleList.get(position);
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
}
