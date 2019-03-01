package com.weima.aishangyi.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：cgy on 16/12/26 23:11
 * 邮箱：593960111@qq.com
 */
public class UserInfoResp extends CommonEntity{
    @SerializedName("data")
    private CurrentUser data;

    public CurrentUser getData() {
        return data;
    }

    public void setData(CurrentUser data) {
        this.data = data;
    }
}
