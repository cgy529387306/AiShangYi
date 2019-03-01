package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */
public class ActivityOrderResp extends CommonEntity{
    private ActivityOrderData data;

    public static class ActivityOrderData extends CommonListData{
        private List<ActivityOrderBean> data;

        public List<ActivityOrderBean> getData() {
            return data;
        }

        public void setData(List<ActivityOrderBean> data) {
            this.data = data;
        }
    }

    public ActivityOrderData getData() {
        return data;
    }

    public void setData(ActivityOrderData data) {
        this.data = data;
    }
}
