package com.wyy.wanandroidcilent.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.BannerAdapter;
import com.wyy.wanandroidcilent.enity.BannerData;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//自定义Banner控件
public class Banner extends FrameLayout{

    private static final int UP_DATA = 1;
    private ViewPager bannerVp;
    private PagerAdapter adapter;
    private List<Bitmap> bitmaps;
    private List<BannerData> datas;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UP_DATA:
                    notyfiDataChangeViews();
                    break;
                default:
                    break;
            }
        }
    };

    public Banner(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_banner,this);
        bitmaps = new ArrayList<>();
        datas = new ArrayList<>();

        bannerVp = (ViewPager) findViewById(R.id.vp_banner);
        adapter = new BannerAdapter(bitmaps,datas,(Activity)context);
        bannerVp.setAdapter(adapter);
    }

    public void init(List<BannerData> bannerDataList){
        datas.clear();
        bitmaps.clear();            //防止容器中有数据
        datas.addAll(bannerDataList);
        loadPictureFromNet();
    }

    public void notyfiDataChangeViews(){
        adapter.notifyDataSetChanged();
    }

    private void loadPictureFromNet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection connection = null;
                try {
                    for (int i = 0; i < datas.size(); i++) {         //将图片的网络地址转化为对应的Bitmap对象
                        url = new URL(datas.get(i).getImagePath());
                        final BannerData bannerData = datas.get(i);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(input);
                        bitmaps.add(bitmap);
                    }
                    Message msg = new Message();
                    msg.what = UP_DATA;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
