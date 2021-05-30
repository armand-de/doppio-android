package com.example.mobile_contentsapp.Login.Retrofit;

public class Number_Post {

    private String phone;

    public Number_Post(String phone) {
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
