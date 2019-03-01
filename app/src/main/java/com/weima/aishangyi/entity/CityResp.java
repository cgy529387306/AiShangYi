package com.weima.aishangyi.entity;

import java.util.List;

/**
 * 作者：cgy on 17/3/5 17:01
 * 邮箱：593960111@qq.com
 */
public class CityResp extends CommonEntity{
    private List<CityBean> data;

    public List<CityBean> getData() {
        return data;
    }

    public void setData(List<CityBean> data) {
        this.data = data;
    }
}
