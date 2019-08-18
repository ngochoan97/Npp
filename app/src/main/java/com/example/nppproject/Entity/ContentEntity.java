package com.example.nppproject.Entity;

import java.util.ArrayList;

public class ContentEntity {
    String  tittle;
    String shortTittle;
    String pubDate;
    String urlImg;
    ArrayList<String> content;

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public ContentEntity() {
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getShortTittle() {
        return shortTittle;
    }

    public void setShortTittle(String shortTittle) {
        this.shortTittle = shortTittle;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
