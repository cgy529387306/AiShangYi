package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
public class StuCategoryResp extends CommonEntity{
    private List<StuCategoryEntity> data;
    public List<StuCategoryEntity> getData() {
        return data;
    }

    public void setData(List<StuCategoryEntity> data) {
        this.data = data;
    }
}
