package com.wyy.wanandroidcilent.net;

import android.graphics.Bitmap;

public interface HttpCallBack {         //发送http请求获得数据后的数据回调接口
    void onFinish(String respone);
    void onError(Exception e);
}
