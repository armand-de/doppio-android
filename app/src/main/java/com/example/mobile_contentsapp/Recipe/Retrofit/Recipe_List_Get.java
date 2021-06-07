package com.example.mobile_contentsapp.Recipe.Retrofit;

public class Recipe_List_Get {
    String id;
    String name;
    int time;
    boolean useoven;
    int category;
    String image;
    int preference;

    public Recipe_List_Get(String id, String name, int time, boolean useoven, int category, String image, int preference) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.useoven = useoven;
        this.category = category;
        this.image = image;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean getUseoven() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }
}
