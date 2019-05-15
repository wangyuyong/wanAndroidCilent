package com.wyy.wanandroidcilent.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.ui.ArticleDetailActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;

import java.util.List;
import java.util.Objects;

public class BannerAdapter extends PagerAdapter {
    private List<Bitmap> bitmaps;
    private List<BannerData> datas;
    private Activity activity;

    public BannerAdapter(List<Bitmap> bitmaps,List<BannerData> datas,Activity activity) {
        this.bitmaps = bitmaps;
        this.datas = datas;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        View view =  LayoutInflater.from(context).inflate(R.layout.adapter_item_banner, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_banner);
        TextView title = (TextView)view.findViewById(R.id.tv_banner_title);
        TextView page = (TextView)view.findViewById(R.id.tv_banner_page);
        final int pos = bitmaps.size() == 0 ? 0 : position % bitmaps.size();
        imageView.setImageBitmap(bitmaps.get(pos));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StateUtil.isFastClicked()){     //防止过快点击
                    return;
                }
                Intent intent = new Intent(activity, ArticleDetailActivity.class);
                intent.putExtra("link",datas.get(pos).getUrl());
                activity.startActivity(intent);
            }
        });
        title.setText(datas.get(pos).getTitle());
        page.setText((pos + 1) + "/" + datas.size());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (bitmaps == null || bitmaps.size() == 0) {     //如果views数组中没有数据，返回0
            return 0;
        }
        return Integer.MAX_VALUE;   //返回无穷大
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
