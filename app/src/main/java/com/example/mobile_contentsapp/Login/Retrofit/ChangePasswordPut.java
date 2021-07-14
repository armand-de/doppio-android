package com.example.mobile_contentsapp.Login.Retrofit;

public class ChangePasswordPut {
    private String phone;
    private String verifyNumber;
    private String password;

    public ChangePasswordPut(String phone, String verifyNumber, String password) {
        this.phone = phone;
        this.verifyNumber = verifyNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
