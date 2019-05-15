package com.wyy.wanandroidcilent.enity;

public class BannerData {
    private String imagePath;
    private String url;
    private String title;

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
