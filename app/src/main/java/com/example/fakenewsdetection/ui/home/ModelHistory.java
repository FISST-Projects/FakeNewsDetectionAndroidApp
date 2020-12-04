package com.example.fakenewsdetection.ui.home;

public class ModelHistory {
    private String link;
    private String date;
    private Integer id;
    public ModelHistory(Integer id, String link, String date){
        this.id = id;
        this.date = date;
        this.link = link;
    }
    public String  getDate() {
        return date;
    }
    public String  getLink() {
        return link;
    }
    public Integer getId() {
        return id;
    }

}
