package com.example.mobile_contentsapp.Recipe.Retrofit;

public class RecipeListGet {
    private int id;
    private String name;
    private String thumbnail;
    private int time;
    private boolean useOven;
    private int category;
    private int preference;

    public RecipeListGet(int id, String name, String thumbnail, int time, boolean useOven, int category, int preference) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.time = time;
        this.useOven = useOven;
        this.category = category;
        this.preference = preference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isUseOven() {
        return useOven;
    }

    public void setUseOven(boolean useOven) {
        this.useOven = useOven;
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
