package com.example.mobile_contentsapp.Recipe.Retrofit;

public class Recipe_List_Get {
    String id;
    String name;
    String thumbnail;
    int time;
    boolean useoven;
    int category;
    int preference;

    public Recipe_List_Get(String id, String name, String thumbnail, int time, boolean useoven, int category, int preference) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.time = time;
        this.useoven = useoven;
        this.category = category;
        this.preference = preference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isUseoven() {
        return useoven;
    }

    public void setUseoven(boolean useoven) {
        this.useoven = useoven;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }
}
