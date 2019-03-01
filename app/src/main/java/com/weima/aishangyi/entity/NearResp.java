package com.weima.aishangyi.entity;

import java.util.List;

/**
 * 作者：cgy on 17/2/16 01:05
 * 邮箱：593960111@qq.com
 */
public class NearResp extends CommonEntity{
    private List<NearBean> data;

    public List<NearBean> getData() {
        return data;
    }

    public void setData(List<NearBean> data) {
        this.data = data;
    }
}
