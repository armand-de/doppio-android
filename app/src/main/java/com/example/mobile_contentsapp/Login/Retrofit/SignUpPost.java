package com.example.mobile_contentsapp.Login.Retrofit;

public class Sign_Up_Post {
    private String nickname;
    private String password;
    private String phone;
    private String verifyNumber;

    public Sign_Up_Post(String nickname, String password, String phone, String verifyNumber) {
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.verifyNumber = verifyNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(String verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    @Override
    public String toString() {
        return "Sign_Up_Post{" +
                "nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", verifyNumber='" + verifyNumber + '\'' +
                '}';
    }
}
