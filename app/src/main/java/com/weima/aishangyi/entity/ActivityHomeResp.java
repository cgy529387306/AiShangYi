package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class ActivityHomeResp extends CommonEntity{
    private List<ActivityBean> data;

    public List<ActivityBean> getData() {
        return data;
    }

    public void setData(List<ActivityBean> data) {
        this.data = data;
    }
}
