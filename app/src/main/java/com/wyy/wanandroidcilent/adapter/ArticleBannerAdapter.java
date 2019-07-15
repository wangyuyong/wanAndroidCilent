package com.wyy.wanandroidcilent.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.ui.articledetail.ArticleDetailActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

//扩展ArticleAdapter,在其顶部添加Banner控件
public class ArticleBannerAdapter extends ArticleAdapter {
    public static final int TOP_ITEM = 2;      //顶部Item
    private Banner banner;

    //必须提供初始化好的Banner控件
    public ArticleBannerAdapter(List<Article.DataBean.DatasBean> articles){
        super(articles);
    }


    //Banner控件的ViewHolder
    public class BannerViewHolder extends RecyclerView.ViewHolder{
        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.bn_home_page);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //如果是顶部Item,则创建Banner的ViewHolder
        if(viewType == TOP_ITEM){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_item_banner,viewGroup,false);
            BannerViewHolder viewHolder = new BannerViewHolder(view);
            return viewHolder;
        }

        return super.onCreateViewHolder(viewGroup,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if( position <= mArticleList.size() && position > 0){
            Article.DataBean.DatasBean article = mArticleList.get(position - 1);
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
                    holder.titleTv.setTextColor(Color.parseColor("#8a000000"));
                    if (StateUtil.isFastClicked()){
                        return;
                    }
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


    public void setImage(final List<BannerData.DataBean> dataBeanList, final Context context){
        List<String> imageTitle = new ArrayList<>();
        List<String> imagePath = new ArrayList<>();

        //装配广告图路径以及广告标题
        for (int i = 0; i < dataBeanList.size(); i++){
            imagePath.add(dataBeanList.get(i).getImagePath());
            imageTitle.add(dataBeanList.get(i).getTitle());
        }

        if (banner != null){
            //加载广告图
            banner.setImageLoader(new GlideImageLoader())
                    .setBannerTitles(imageTitle)
                    .setImages(imagePath)
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                    .setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            if (StateUtil.isFastClicked()){
                                return;
                            }
                            String url = dataBeanList.get(position).getUrl();
                            String title = dataBeanList.get(position).getTitle();
                            Intent intent = new Intent(context, ArticleDetailActivity.class);
                            intent.putExtra("link",url);
                            intent.putExtra("title",title);
                            context.startActivity(intent);
                        }
                    })
                    .start();
        }
    }
}
