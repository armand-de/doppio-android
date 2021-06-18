package com.example.mobile_contentsapp.Login.Retrofit;

public class NumberPost {

    private String phone;

    public NumberPost(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Sign_Up_Post{" +
                "phone='" + phone + '\'' +
                '}';
    }
}
