package com.example.trafficsigns.models;

public class Category {
    private String title;
    private int id;

    public Category(int id,String title){
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
