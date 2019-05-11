package com.wyy.wanandroidcilent.net;

public interface HttpCallBack {         //发送http请求获得数据后的数据回调接口
    /**
     * 对请求后得到的数据进行处理
     * @param respone(发送Http请求后得到的数据)
     */
    void onFinish(String respone);

    /**
     * 对请求抛出异常进行处理
     * @param e(抛出的异常)
     */
    void onError(Exception e);
}
