package com.example.fakenewsdetection.ui.home;

import android.graphics.drawable.Drawable;

public class ModelNews {
    private Drawable iconPath;
    private String name;
    private String link;
    public ModelNews(Drawable iconPath, String name, String link){
        this.iconPath = iconPath;
        this.name = name;
        this.link = link;
    }



    public Drawable getIconPath() {
        return iconPath;
    }
    public void setIconPath(Drawable iconPath) {
        this.iconPath = iconPath;
    }
    public String  getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String  getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
