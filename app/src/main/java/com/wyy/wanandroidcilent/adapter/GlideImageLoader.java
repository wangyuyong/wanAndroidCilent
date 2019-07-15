package com.wyy.wanandroidcilent.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

//加载第三方库banner需要提供的图片加载器
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path != null){
            Glide.with(context)
                    .load((String) path)
                    .into(imageView);

        }
    }
}
