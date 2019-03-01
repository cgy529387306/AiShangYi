package com.weima.aishangyi.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/20 0020.
 */

public class LoginResp extends CommonEntity{
    @SerializedName("token")
    private String token;

    @SerializedName("data")
    private CurrentUser data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CurrentUser getData() {
        return data;
    }

    public void setData(CurrentUser data) {
        this.data = data;
    }
}
