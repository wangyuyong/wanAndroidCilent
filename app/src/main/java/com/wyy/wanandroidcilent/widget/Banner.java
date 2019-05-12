package com.wyy.wanandroidcilent.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.BannerAdapter;
import java.util.ArrayList;
import java.util.List;

public class Banner extends FrameLayout {
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

    public void initViews(Context context,List<Bitmap> bitmaps){
        for(int i = 0; i < bitmaps.size(); i++){
            Bitmap bitmap = bitmaps.get(i);
            View view = View.inflate(context,R.layout.adapter_item_banner,null);
            ImageView picture = (ImageView)view.findViewById(R.id.iv_banner);
            picture.setImageBitmap(bitmap);
            views.add(view);
        }
        adapter.notifyDataSetChanged();
    }


}
