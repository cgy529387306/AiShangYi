package com.weima.aishangyi.entity;

import java.util.List;

/**
 * 作者：cgy on 17/3/5 17:01
 * 邮箱：593960111@qq.com
 */
public class CouponResp extends CommonEntity{
    private List<CouponBean> data;

    public List<CouponBean> getData() {
        return data;
    }

    public void setData(List<CouponBean> data) {
        this.data = data;
    }
}
