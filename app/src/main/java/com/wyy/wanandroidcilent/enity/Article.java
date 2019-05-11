package com.wyy.wanandroidcilent.enity;

import com.wyy.wanandroidcilent.app.MyApplication;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;

public class Article {

    private String author;
    private String chapterName;
    private String link;
    private String niceDate;
    private String superChapterName;
    private String title;
    private boolean isRead;                     //判断文章是否已阅读过

    public Article(String author, String chapterName, String link, String niceDate, String superChapterName, String title) {
        setAuthor(author);
        setChapterName(chapterName);
        setLink(link);
        setNiceDate(niceDate);
        setSuperChapterName(superChapterName);
        setTitle(title);                        //在set方法中添加逻辑判断转化一些奇怪的字符
        initRead();
    }

    public Article(){}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.indexOf("<") != -1){
            title = title.replaceAll("</?[^>]+>","");           //利用正则表达式取出html标签
        }
        if(title.indexOf("&") == -1){                                               //不存在转义字符
            this.title = title;
        }else {                                                                     //可能存在转义字符
            if (title.indexOf("&mdash") != -1){
                this.title = title.replaceAll("&mdash;","—");       //将所有&mdash;替换为-
            }
            if (title.indexOf("&quot;") != -1){
                this.title = title.replaceAll("&quot;","\"");       //将所有&quot;替换为"
            }
            if (title.indexOf("&amp;") != -1){
                this.title = title.replaceAll("&amp;","&");         //将所有&amp;替换为&
            }
            if (title.indexOf("&nbsp;") != -1){
                this.title = title.replaceAll("&nbsp;"," ");        //将所有&nbsp;替换为空格
            }
        }
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    private void initRead(){
        //从本地中查找该文章是否已读
        isRead = SharedPreferencesUtil.getBooleanWithSharePreference(MyApplication.getContext(),SharedPreferencesUtil.HAVE_READ_FILE,title);
    }
}
