package com.wyy.wanandroidcilent.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.BannerAdapter;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.ui.ArticleDetailActivity;

import java.util.ArrayList;
import java.util.List;

//自定义Banner控件
public class Banner extends FrameLayout{
    ViewPager bannerVp;
    PagerAdapter adapter;
    List<View> views;
    public Banner(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_banner,this);
        views = new ArrayList<>();

        bannerVp = (ViewPager) findViewById(R.id.vp_banner);
        adapter = new BannerAdapter(views);
        bannerVp.setAdapter(adapter);

    }

    /**
     * 提供Bitmap,BannerDatas数组初始化Banner
     * @param context
     * @param bitmaps
     * @param datas(获取BannerData中的url)
     */
    public void initViews(final Context context, List<Bitmap> bitmaps, List<BannerData> datas){
        for(int i = 0; i < bitmaps.size(); i++){
            Bitmap bitmap = bitmaps.get(i);
            View view = View.inflate(context,R.layout.adapter_item_banner,null);
            ImageView picture = (ImageView)view.findViewById(R.id.iv_banner);
            picture.setImageBitmap(bitmap);

            final String url = datas.get(i).getUrl();
            //为图片设置点击事件
           picture.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra("link",url);
                    ((Activity)context).startActivity(intent);
                }
            });
            views.add(view);
        }

        adapter.notifyDataSetChanged();
    }

}
