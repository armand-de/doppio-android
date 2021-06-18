package com.example.mobile_contentsapp.Recipe.Retrofit;

public class RecipeFindGet {
      private String id;
      private String name;
      private int time;
      private String thumbnail;
      private User user;
      private String image;
      private String description;
      private String ingredients;
      private String contents;
      private int category;
      private boolean useOven;
      private String createDate;
      private int preference;

      public RecipeFindGet(String id, String name, int time, String thumbnail,
                           User user, String image, String description,
                           String ingredients, String contents, int category,
                           boolean useOven, String createDate, int preference) {
            this.id = id;
            this.name = name;
            this.time = time;
            this.thumbnail = thumbnail;
            this.user = user;
            this.image = image;
            this.description = description;
            this.ingredients = ingredients;
            this.contents = contents;
            this.category = category;
            this.useOven = useOven;
            this.createDate = createDate;
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

      public String getThumbnail() {
            return thumbnail;
      }

      public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
      }

      public User getUser() {
            return user;
      }

      public void setUser(User user) {
            this.user = user;
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

      public boolean isUseOven() {
            return useOven;
      }

      public void setUseOven(boolean useOven) {
            this.useOven = useOven;
      }

      public String getCreateDate() {
            return createDate;
      }

      public void setCreateDate(String createDate) {
            this.createDate = createDate;
      }

      public int getPreference() {
            return preference;
      }

      public void setPreference(int preference) {
            this.preference = preference;
      }
}
