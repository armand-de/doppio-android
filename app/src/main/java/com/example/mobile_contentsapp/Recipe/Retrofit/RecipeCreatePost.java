package com.example.mobile_contentsapp.Recipe.Retrofit;

public class RecipeCreatePost {
    String name;
    String thumbnail;
    String image;
    String description;
    String ingredients;
    String contents;
    int category;
    int time;
    boolean useOven = true;

    public RecipeCreatePost(String name, String thumbnail, String image, String description, String ingredients, String contents, int category, int time, boolean useOven) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.image = image;
        this.description = description;
        this.ingredients = ingredients;
        this.contents = contents;
        this.category = category;
        this.time = time;
        this.useOven = useOven;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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
}
