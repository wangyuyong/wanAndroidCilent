package com.wyy.wanandroidcilent.utils;

import com.wyy.wanandroidcilent.enity.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParaseUtil {

    /**
     * @description 解析JSON数据并返回Article数组
     * @param json
     * @return List<Article>
     */
    public static boolean paraseJSONToArticle(String json,List<Article> articles){
        if(articles != null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray datas = data.getJSONArray("datas");       //从JSON数据从取出key为datas的JSON数组

                if(datas.length() == 0){                                    //解析到的数据为0时，返回false
                    return false;
                }

                String author;
                String chapterName;
                String link;
                String niceDate;
                String superChapterName;
                String title;

                for(int i = 0; i < datas.length(); i++){                //将datas数组中对应键的键值取出并构造article对象，将该对象加入articles容器中
                    JSONObject articleJSON = datas.getJSONObject(i);
                    author = articleJSON.getString("author");
                    chapterName = articleJSON.getString("chapterName");
                    link = articleJSON.getString("link");
                    niceDate = articleJSON.getString("niceDate");
                    superChapterName = articleJSON.getString("superChapterName");
                    title = articleJSON.getString("title");
                    Article article = new Article(author,chapterName,link,niceDate,superChapterName,title);
                    articles.add(article);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
