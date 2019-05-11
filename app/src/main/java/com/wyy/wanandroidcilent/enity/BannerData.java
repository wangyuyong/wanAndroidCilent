package com.wyy.wanandroidcilent.enity;

public class BannerData {
    private String imagePath;
    private String url;

    public BannerData(){}

    public BannerData(String imagePath, String url){
        this.imagePath = imagePath;
        this.url = url;
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
}
