package com.wyy.wanandroidcilent.enity;

public class BannerData {
    private String imagePath;       //图片路径
    private String url;             //广告地址
    private String title;           //广告标题

    public BannerData(){}

    public BannerData(String imagePath, String url,String title){
        this.imagePath = imagePath;
        this.url = url;
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
