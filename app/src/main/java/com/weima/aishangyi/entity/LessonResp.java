package com.weima.aishangyi.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class LessonResp extends CommonEntity{
    private DataBean data;
    public static class DataBean extends CommonListData{
        private List<ClassBean> data;

        public List<ClassBean> getData() {
            return data;
        }

        public void setData(List<ClassBean> data) {
            this.data = data;
        }
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
