package com.example.mobile_contentsapp.Recipe.Retrofit;

public class Page_Get {
    private int count;

    public Page_Get(int amount) {
        this.count = amount;
    }

    public int getAmount() {
        return count;
    }

    public void setAmount(int count) {
        this.count = count;
    }
}
