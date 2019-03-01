package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/18.
 */
public class AdvertResp {
    private int code;
    private List<AdvertEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AdvertEntity> getData() {
        return data;
    }

    public void setData(List<AdvertEntity> data) {
        this.data = data;
    }
}
