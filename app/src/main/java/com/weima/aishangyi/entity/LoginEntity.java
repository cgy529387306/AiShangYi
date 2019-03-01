package com.weima.aishangyi.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/20 0020.
 */

public class LoginEntity extends CommonEntity{
    @SerializedName("data")
    private CurrentUser data;

    public CurrentUser getData() {
        return data;
    }

    public void setData(CurrentUser data) {
        this.data = data;
    }


}
