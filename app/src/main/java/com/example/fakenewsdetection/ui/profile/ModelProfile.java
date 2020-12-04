package com.example.fakenewsdetection.ui.profile;

import android.graphics.drawable.Drawable;

public class ModelProfile {
    private Drawable iconPath;
    private String name;
    public ModelProfile(Drawable iconPath, String name){
        this.iconPath = iconPath;
        this.name = name;
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
}
