package com.mobilki.dokultury.models;

public class Category {
    private int icon;
    private String name;

    public Category(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
