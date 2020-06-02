package com.suusoft.elistening.retrofit.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.suusoft.elistening.model.User;

public class ResponeUser extends BaseRespone {
    public User data;

    @SerializedName("login_token")
    @Expose
    private String token;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
