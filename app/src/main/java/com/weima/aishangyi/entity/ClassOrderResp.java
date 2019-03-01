package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */
public class ClassOrderResp extends CommonEntity{
    private ClassOrderData data;

    public static class ClassOrderData extends CommonListData{
        private List<ClassOrderBean> data;

        public List<ClassOrderBean> getData() {
            return data;
        }

        public void setData(List<ClassOrderBean> data) {
            this.data = data;
        }
    }

    public ClassOrderData getData() {
        return data;
    }

    public void setData(ClassOrderData data) {
        this.data = data;
    }
}
