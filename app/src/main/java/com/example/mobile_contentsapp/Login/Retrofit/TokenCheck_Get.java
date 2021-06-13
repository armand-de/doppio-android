package com.example.mobile_contentsapp.Login.Retrofit;

public class TokenCheck_Get {
    private boolean success;

    public TokenCheck_Get(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
